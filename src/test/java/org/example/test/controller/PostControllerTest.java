package org.example.test.controller;

import org.example.controller.PostController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class PostControllerTest {

    @MockitoBean
    private PostController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testGetPostsStatus() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSearchPostsStatus() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("search", "123"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSearchPaginatedPostsStatus() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("pageSize", "10")
                        .param("pageNumber", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPostStatus() throws Exception {
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddPostFormStatus() throws Exception {
        mockMvc.perform(get("/posts/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void testEditPostFormStatus() throws Exception {
        mockMvc.perform(get("/posts/1/edit"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddPostStatus() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "image", // Parameter name in the controller
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "This is a test file content.".getBytes()
        );

        mockMvc.perform(multipart("/posts")
                        .file(file)
                        .param("title", "some title")
                        .param("text", "some text")
                        .param("tags", "some tags")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testEditPostStatus() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "image", // Parameter name in the controller
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "This is a test file content.".getBytes()
        );

        mockMvc.perform(multipart("/posts/1")
                        .file(file)
                        .param("title", "some title")
                        .param("text", "some text")
                        .param("tags", "some tags")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddPostLikeStatus() throws Exception {
        mockMvc.perform(post("/posts/1/like")
                        .param("like", "true"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDelPostLikeStatus() throws Exception {
        mockMvc.perform(post("/posts/1/like")
                        .param("like", "false"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePostStatus() throws Exception {
        mockMvc.perform(post("/posts/1/delete"))
                .andExpect(status().isOk());
    }

}