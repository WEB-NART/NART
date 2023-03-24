package com.nart.util.upload;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: ImgtuUtil
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/01/04 15:54
 */
@Slf4j
public class ImgtuUtil {
    static final String IMGTU_USER_NAME = "nart";
    static final String IMGTU_PASSWORD = "123456";
    static final String CHAT_ALBUMID = "4Yrex";
    static final String STATUS_ALBUMID = "4YBO1";
    static final String GROUPS_ALBUMID = "4Y0yR";
    static final String USERS_ALBUMID = "4Ywl9";
    static final private String IMGTU_INIT_URL = "https://imgse.com/init";
    static final private String IMGTU_LOGIN_URL = "https://imgse.com/login";
    static final private String IMGTU_OPERATE_URL = "https://imgse.com/json";
    static final private Pattern SESSION_ID_PATTERN = Pattern.compile("PHPSESSID=([^;]*); path=/; HttpOnly");
    static final private Pattern AUTH_TOKEN_PATTERN = Pattern.compile("PF\\.obj\\.config\\.auth_token = \"([0-9a-f]{40})\";");
    static final private Pattern KEEP_LOGIN_PATTERN = Pattern.compile("KEEP_LOGIN=([^;]*);");
    static final private long INIT_VALID_DURATION = 15L * 60 * 1000;
    static final private long LOGIN_VALID_DURATION = 30L * 24 * 60 * 60 * 1000;


    static private String sessionId;

    static private String authToken;

    static private String keepLogin;

    static private long initTimestamp = 0;

    static private long loginTimestamp = 0;

    public static Boolean initSession() {
        return initSession(false);
    }

    public static Boolean initSession(boolean forceAction) {
        if (!forceAction && !isSessionIdExpired()) {
            log.info("Imgtu [INITIALIZATION] Success：No re-initialisation required for the duration of the session");
            return null;
        }
        synchronized (ImgtuUtil.class) {
            // Initialisation
            sessionId = null;
            authToken = null;

            // Request a login page
            String httpRawString;
            CloseableHttpResponse httpResponse;
            try {
                httpResponse = HttpUtil.get(IMGTU_INIT_URL, new HashMap<>(0), new HashMap<>(0));
                HttpEntity httpEntity = httpResponse.getEntity();
                httpRawString = EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                log.error("Imgtu [INITIALIZATION] Failed: Failing request page ({})" + e.getLocalizedMessage());
                e.printStackTrace();
                return false;
            }

            // Get SessionId
            Header[] responseHeaders = httpResponse.getAllHeaders();
            for (Header header : responseHeaders) {
                if ("Set-Cookie".equalsIgnoreCase(header.getName())) {
                    String cookies = header.getValue();
                    Matcher matcher = SESSION_ID_PATTERN.matcher(cookies);
                    if (matcher.find(0)) {
                        sessionId = matcher.group(1);
                    }
                }
            }

            if (sessionId == null) {
                log.error("Imgtu [INITIALIZATION] Failed: Failing get sessionId");
                return false;
            }

            // Get AuthToken
            Matcher matcher = AUTH_TOKEN_PATTERN.matcher(httpRawString);
            if (matcher.find(0)) {
                authToken = matcher.group(1);
            } else {
                log.error("Imgtu [INITIALIZATION] Failed: Failing get AuthToken");
                return false;
            }

            log.info("Imgtu [INITIALIZATION]V SessionId:" + sessionId);
            log.info("Imgtu [INITIALIZATION]V AuthToken:" + authToken);

            initTimestamp = System.currentTimeMillis();
            return true;
        }
    }

    public static Boolean login() throws IOException {
        return login(false);
    }

    public static Boolean login(boolean forceAction) throws IOException {
        if (!forceAction && !isLoginExpired()) {
            log.info("Imgtu [LOGIN] success：No re-login required for the duration of the session");
            return null;
        }
        synchronized (ImgtuUtil.class) {
            // Initializing a session
            if (isSessionIdExpired()) {
                Boolean b = initSession();
                if (!(b == null || b)) {
                    log.error("Imgtu [LOGIN] fail：Initialization session blocked");
                }
            }

            // Setting the request header
            Map<String, String> headers = new HashMap<>(3);
            headers.put("cookie", "PHPSESSID=" + sessionId + ";");
            headers.put("content-type", "application/x-www-form-urlencoded");
            headers.put("connection", "keep-alive");

            CloseableHttpResponse httpResponse = HttpUtil.post(IMGTU_LOGIN_URL, new HashMap<>(0), headers,
                    "login-subject=" + IMGTU_USER_NAME + "&password=" + IMGTU_PASSWORD + "&auth_token=" + authToken);

            Header[] responseHeaders = httpResponse.getAllHeaders();
            for (Header header : responseHeaders) {
                if ("Set-Cookie".equalsIgnoreCase(header.getName())) {
                    String cookies = header.getValue();
                    Matcher matcher = KEEP_LOGIN_PATTERN.matcher(cookies);
                    if (matcher.find(0)) {
                        keepLogin = matcher.group(1);
                    }
                }
            }

            if (keepLogin != null) {
                loginTimestamp = System.currentTimeMillis();
                log.info("Imgtu [LOGIN]：V KeepLogin:" + keepLogin);
                return true;
            } else {
                log.error("Imgtu [LOGIN]：X StatusCode:" + httpResponse.getStatusLine().getStatusCode());
                log.error("Imgtu [LOGIN]：X response:" + httpResponse);
                return false;
            }
        }
    }

    public static boolean ensureLogin() throws IOException {
        Boolean loginRes = login();
        if (loginRes == null) {
            Boolean initRes = initSession();
            return initRes == null || initRes;
        } else {
            return loginRes;
        }
    }

    public static JsonObject upload(byte[] bytes, String fileName, ContentType fileType, String album) throws IOException {
        log.info("-------->>>> PicBeds - Upload <<<<--------");
        if (!ensureLogin()) {
            log.error("Imgtu [UPLOAD]：fail：Service unavailable");
            return null;
        }

        Map<String, String> headers = new HashMap<>(3);
        headers.put("Cookie", "PHPSESSID=" + sessionId + "; KEEP_LOGIN=" + keepLogin);
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Safari/537.36");
        headers.put("Connection", "keep-alive");

        try {
            Map<String, ContentBody> params = new HashMap<>(6);
            params.put("source", new ByteArrayBody(bytes, fileType, fileName));
            params.put("type", new StringBody("file", ContentType.MULTIPART_FORM_DATA));
            params.put("action", new StringBody("upload", ContentType.MULTIPART_FORM_DATA));
            params.put("timestamp", new StringBody(Long.toString(System.currentTimeMillis()), ContentType.MULTIPART_FORM_DATA));
            params.put("auth_token", new StringBody(authToken, ContentType.MULTIPART_FORM_DATA));
            params.put("nsfw", new StringBody("0", ContentType.MULTIPART_FORM_DATA));
            params.put("album_id", new StringBody(album, ContentType.MULTIPART_FORM_DATA));

            CloseableHttpResponse httpResponse = HttpUtil.multipart(IMGTU_OPERATE_URL, new HashMap<>(0), headers, params);
            String httpRawString = EntityUtils.toString(httpResponse.getEntity());
            log.info("Imgtu [UPLOAD] Success：Upload Successfully!");
            return new Gson().fromJson(httpRawString, JsonObject.class);
        } catch (IOException e) {
            log.error("\"Imgtu [UPLOAD] Fail：{}" + e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static JsonObject delete(String deleteId) throws IOException {
        log.info("-------->>>> PicBeds - Delete <<<<--------");
        if (!ensureLogin()) {
            log.error("Imgtu [DELETE] Fail：Service unavailable");
            return null;
        }
        Map<String, String> headers = new HashMap<>(3);
        headers.put("Cookie", "PHPSESSID=" + sessionId + "; KEEP_LOGIN=" + keepLogin);
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Safari/537.36");
        headers.put("Connection", "keep-alive");

        try {
            Map<String, ContentBody> params = new HashMap<>(5);
            params.put("auth_token", new StringBody(authToken, ContentType.MULTIPART_FORM_DATA));
            params.put("action", new StringBody("delete", ContentType.MULTIPART_FORM_DATA));
            params.put("single", new StringBody("true", ContentType.MULTIPART_FORM_DATA));
            params.put("delete", new StringBody("image", ContentType.MULTIPART_FORM_DATA));
            params.put("deleting[id]", new StringBody(deleteId, ContentType.MULTIPART_FORM_DATA));

            CloseableHttpResponse httpResponse = HttpUtil.multipart(IMGTU_OPERATE_URL, new HashMap<>(0), headers, params);
            String httpRawString = EntityUtils.toString(httpResponse.getEntity());
            log.info("Imgtu [DELETE] Success：Delete Successfully!");
            return new Gson().fromJson(httpRawString, JsonObject.class);
        } catch (IOException e) {
            log.error("Imgtu [DELETE] Fail：{}" + e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    static boolean isSessionIdExpired() {
        return initTimestamp + INIT_VALID_DURATION < System.currentTimeMillis();
    }

    static boolean isLoginExpired() {
        return loginTimestamp + LOGIN_VALID_DURATION < System.currentTimeMillis();
    }

    public static String uploadPic(byte[] bytes, String fileName, Integer album) throws IOException {
        String albumId;
        switch (album) {
            case 0: albumId = STATUS_ALBUMID; break;
            case 1: albumId = USERS_ALBUMID; break;
            case 2: albumId = GROUPS_ALBUMID; break;
            default: albumId = CHAT_ALBUMID;
        }
        JsonObject testFile = upload(bytes, fileName, FileType.checkType(fileName), albumId);
        assert testFile != null;
        Object x = testFile.get("status_code");
        if (x.toString().equals("400")) {
            return "400";
        }
        JsonObject image1 = testFile.getAsJsonObject("image");
        assert image1 != null;
        JsonObject image = image1.getAsJsonObject("image");
        assert image != null;
        JsonPrimitive jp = image.getAsJsonPrimitive("url");

        String asString = jp.getAsString();
        log.info("short url: " + asString);

        return asString;
    }

    public static String deletePic(String id) throws IOException {
        JsonObject jsonObject = delete(id);
        assert jsonObject != null;
        Object x = jsonObject.get("status_code");
        return x.toString();
    }
}
