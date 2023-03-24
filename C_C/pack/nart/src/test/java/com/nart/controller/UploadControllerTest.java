package com.nart.controller;

import com.nart.service.LoadDataInDataBase;
import com.nart.service.LoginService;
import com.nart.util.upload.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UploadController.class)
@AutoConfigureMybatis
class UploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoadDataInDataBase mockLd;

    @MockBean
    private LoginService loginService;

    private String link;

    @Test
    void testUpload_jpg() throws Exception {
        // Setup
        // Run the test
        MockHttpServletResponse response = mockMvc.perform(multipart("/upload/{album}", 0)
                        .file(new MockMultipartFile("file", "originalFilename.jpg", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 1)
                        .file(new MockMultipartFile("file", "originalFilename.jpg", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 2)
                        .file(new MockMultipartFile("file", "originalFilename.jpg", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        MockHttpServletResponse response1 = mockMvc.perform(multipart("/upload/{album}", 3)
                        .file(new MockMultipartFile("file", "originalFilename.jpg", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response1.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response1.getContentAsString()).isNotNull();
    }

    @Test
    void testUpload_ThrowsIOException_jpg() throws Exception {
        // Setup
        // Run the test
        MockHttpServletResponse response = mockMvc.perform(multipart("/upload/{album}", 0)
                        .file(new MockMultipartFile("file", "originalFilename.jpg", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 1)
                        .file(new MockMultipartFile("file", "originalFilename.jpg", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 2)
                        .file(new MockMultipartFile("file", "originalFilename.jpg", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 3)
                        .file(new MockMultipartFile("file", "originalFilename.jpg", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void testUpload_png() throws Exception {
        // Setup
        // Run the test
        MockHttpServletResponse response = mockMvc.perform(multipart("/upload/{album}", 0)
                        .file(new MockMultipartFile("file", "originalFilename.png", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 1)
                        .file(new MockMultipartFile("file", "originalFilename.png", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 2)
                        .file(new MockMultipartFile("file", "originalFilename.png", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 3)
                        .file(new MockMultipartFile("file", "originalFilename.png", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void testUpload_ThrowsIOException_png() throws Exception {
        // Setup
        // Run the test
        MockHttpServletResponse response = mockMvc.perform(multipart("/upload/{album}", 0)
                        .file(new MockMultipartFile("file", "originalFilename.png", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 1)
                        .file(new MockMultipartFile("file", "originalFilename.png", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 2)
                        .file(new MockMultipartFile("file", "originalFilename.png", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 3)
                        .file(new MockMultipartFile("file", "originalFilename.png", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void testUpload_gif() throws Exception {
        // Setup
        // Run the test
        MockHttpServletResponse response = mockMvc.perform(multipart("/upload/{album}", 0)
                        .file(new MockMultipartFile("file", "originalFilename.gif", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 1)
                        .file(new MockMultipartFile("file", "originalFilename.gif", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 2)
                        .file(new MockMultipartFile("file", "originalFilename.gif", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 3)
                        .file(new MockMultipartFile("file", "originalFilename.gif", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void testUpload_ThrowsIOException_gif() throws Exception {
        // Setup
        // Run the test
        MockHttpServletResponse response = mockMvc.perform(multipart("/upload/{album}", 0)
                        .file(new MockMultipartFile("file", "originalFilename.gif", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 1)
                        .file(new MockMultipartFile("file", "originalFilename.gif", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 2)
                        .file(new MockMultipartFile("file", "originalFilename.gif", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();

        response = mockMvc.perform(multipart("/upload/{album}", 3)
                        .file(new MockMultipartFile("file", "originalFilename.gif", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void testUpload_pdf() throws Exception {
        // Setup
        // Run the test
        MockHttpServletResponse response = mockMvc.perform(multipart("/upload/{album}", 0)
                        .file(new MockMultipartFile("file", "originalFilename.pdf", MediaType.APPLICATION_JSON_VALUE,
                                "content".getBytes()))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void testDelete() throws Exception {

        File file = new File("C:\\Users\\mikeq\\Pictures\\bug2.jpg");
        byte[] fileByte = Files.readAllBytes(file.toPath());

        MockHttpServletResponse response1 = mockMvc.perform(multipart("/upload/{album}", 3)
                        .file(new MockMultipartFile("file", file.getName(), MediaType.APPLICATION_JSON_VALUE,fileByte))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response1.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response1.getContentAsString()).isNotNull();

        String contentAsString = response1.getContentAsString();
        String part = contentAsString.split("data\":\"")[1];
        link = StringUtils.substring(part, 0, part.length() - 2);
        // Setup
        // Run the test
        int head = link.lastIndexOf("/") + 1;
        int tail = link.lastIndexOf(".");
        String id = link.substring(head, tail);
        final MockHttpServletResponse response = mockMvc.perform(put("/upload/delete/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void testDelete_ThrowsIOException() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/upload/delete/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotNull();
    }

    @Test
    void testInit() throws IOException {
//        FileType fileType = new FileType();
//        HttpUtil httpUtil = new HttpUtil();
//        ImgtuUtil imgtuUtil = new ImgtuUtil();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "mike");
        map.put("pwd", "pwd");
        Map<String, String> headers = new HashMap<>(3);
        headers.put("Cookie", "PHPSESSID=1234; KEEP_LOGIN=true");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Safari/537.36");
        headers.put("Connection", "keep-alive");
        HttpUtil.post("https://example.com", map, headers, "");
    }
}
