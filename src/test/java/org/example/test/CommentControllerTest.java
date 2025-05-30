package org.example.test;

import org.example.controller.CommentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CommentControllerTest.TestMockConfig.class)
public class CommentControllerTest {

    @Autowired
    private CommentController controller;

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
    public void testAddCommentStatus() throws Exception {
        mockMvc.perform(post("/posts/1/comments")
                        .param("text", "some text"))
                .andExpect(status().isOk());
    }

    @Test
    public void testEditCommentStatus() throws Exception {
        mockMvc.perform(post("/posts/1/comments/2")
                        .param("text", "some text"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCommentStatus() throws Exception {
        mockMvc.perform(post("/posts/1/comments/2/delete"))
                .andExpect(status().isOk());
    }

    @Configuration
    static class TestMockConfig {
        @Bean
        public CommentController commentController() {
            return mock(CommentController.class);
        }
    }
}