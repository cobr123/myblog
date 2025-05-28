package org.example.test;

import org.example.TestConfig;
import org.example.configuration.DataSourceConfiguration;
import org.example.model.Post;
import org.example.model.Posts;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataSourceConfiguration.class, TestConfig.class})
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Test
    void testFindPostByTag() {
        final String title = UUID.randomUUID().toString();
        final String tag1 = UUID.randomUUID().toString();
        final String tag2 = UUID.randomUUID().toString();
        Post validPost = new Post(1L, title, tag1 + " " + tag2, "text", "imagePath", 0, new ArrayList<>());
        Long id = postService.insert(validPost);

        Posts savedPosts = postRepository.findAll(tag2, 10, 1);
        Post foundPost = savedPosts.posts().stream().filter(p -> p.getTitle().equals(title)).findAny().get();
        assertEquals(id, foundPost.getId(), "Post id should match");

        Post savedPost = postRepository.findById(id);
        assertNotNull(savedPost, "Saved post should not be null");
        assertEquals(title, savedPost.getTitle(), "Post title should match");
    }

    @Test
    void testInsertPost() {
        final String title = UUID.randomUUID().toString();
        Post validPost = new Post(1L, title, "tags", "text", "imagePath", 0, new ArrayList<>());
        postService.insert(validPost);

        Posts savedPosts = postRepository.findAll("", 10, 1);
        Post foundPost = savedPosts.posts().stream().filter(p -> p.getTitle().equals(title)).findAny().get();
        Post savedPost = postRepository.findById(foundPost.getId());
        assertNotNull(savedPost, "Saved post should not be null");
        assertEquals(title, savedPost.getTitle(), "Post title should match");
    }

    @Test
    void testEditPost() {
        final String title = UUID.randomUUID().toString();
        Post validPost = new Post(1L, title, "tags", "text", "imagePath", 0, new ArrayList<>());
        postService.insert(validPost);

        Posts savedPosts = postRepository.findAll("", 10, 1);
        Post foundPost = savedPosts.posts().stream().filter(p -> p.getTitle().equals(title)).findAny().get();
        Post savedPost = postRepository.findById(foundPost.getId());
        assertNotNull(savedPost, "Saved post should not be null");
        assertEquals(title, savedPost.getTitle(), "Post title should match");

        final String text = UUID.randomUUID().toString();
        savedPost.setText(text);
        postRepository.update(savedPost);

        Post savedPostAfterEdit = postRepository.findById(foundPost.getId());
        assertEquals(text, savedPostAfterEdit.getText(), "Post text should match");
    }

    @Test
    void testDeletePost() {
        final String title = UUID.randomUUID().toString();
        Post validPost = new Post(1L, title, "tags", "text", "imagePath", 0, new ArrayList<>());
        postService.insert(validPost);

        Posts savedPosts = postRepository.findAll("", 10, 1);
        Post foundPost = savedPosts.posts().stream().filter(p -> p.getTitle().equals(title)).findAny().get();
        Post savedPost = postRepository.findById(foundPost.getId());
        assertNotNull(savedPost, "Saved post should not be null");
        assertEquals(title, savedPost.getTitle(), "Post title should match");

        postRepository.deleteById(foundPost.getId());
        assertThrows(NoSuchElementException.class, () -> postRepository.findById(foundPost.getId()));

    }

}