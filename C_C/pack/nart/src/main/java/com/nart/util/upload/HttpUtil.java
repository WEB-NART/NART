package com.nart.util.upload;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.util.Map;


/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: HttpUtil
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/01/04 15:55
 */
public class HttpUtil {
    private static Header[] parseHeaders(Map<String, String> headers) {
        Header[] headersArray = new Header[headers.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            headersArray[i] = new BasicHeader(entry.getKey(), entry.getValue());
            i++;
        }
        return headersArray;
    }

    private static String parseParams(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.toString();
    }

    /**
     * Send a Get request
     *
     * @param url request address
     * @param params request parameters
     * @param headers request headers
     * @return The result of the response, or null if the request failed.
     */
    public static CloseableHttpResponse get(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        CloseableHttpClient httpClient;

        //Create an httpClient instance with the default configuration
        httpClient = HttpClients.createDefault();

        //Create an httpGet remote connection instance
        String param = parseParams(params);
        if (param != null) {
            url = url + "?" + param;
        }
        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeaders(parseHeaders(headers));

        //Configure request parameters
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000)
                .setConnectionRequestTimeout(30 * 1000)
                .setSocketTimeout(60 * 1000)
                .build();
        httpGet.setConfig(requestConfig);

        //Execute a get request to get the returned object
        return httpClient.execute(httpGet);
    }

    /**
     * Sending a Post request
     *
     * @param url request address
     * @param params request parameters
     * @param headers request headers
     * @param entity request entity
     * @return response result
     */
    public static CloseableHttpResponse post(String url, Map<String, String> params, Map<String, String> headers, HttpEntity entity) throws IOException {
        //Creating the httpClient object
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //Creating an http request
        String param = parseParams(params);
        if (param != null) {
            url = url + "?" + param;
        }
        HttpPost httpPost = new HttpPost(url);

        // Set the request header
        httpPost.setHeaders(parseHeaders(headers));

        // Configure request parameters
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000)
                .setConnectionRequestTimeout(30 * 1000)
                .setSocketTimeout(60 * 1000)
                .build();
        httpPost.setConfig(requestConfig);

        // Set the request content
        httpPost.setEntity(entity);

        // Execute the get request to get the returned object
        return httpClient.execute(httpPost);
    }

    /**
     * Sending a Post request
     *
     * @param url request address
     * @param params request parameters
     * @param headers request headers
     * @param data request data
     * @return response result
     */
    public static CloseableHttpResponse post(String url, Map<String, String> params, Map<String, String> headers, String data) throws IOException {
        return post(url, params, headers, new StringEntity(data));
    }

    /**
     * Sending a Multipart request
     *
     * @param url request address
     * @param params request parameters
     * @param headers request headers
     * @param data request data
     * @return response result
     */
    public static CloseableHttpResponse multipart(String url, Map<String, String> params, Map<String, String> headers, Map<String, ContentBody> data) throws IOException {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        for (Map.Entry<String, ContentBody> dataPair : data.entrySet()) {
            multipartEntityBuilder.addPart(dataPair.getKey(), dataPair.getValue());
        }
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        HttpEntity reqEntity = multipartEntityBuilder.build();

        return post(url, params, headers, reqEntity);
    }
}
