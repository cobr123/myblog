package org.example.test.service;

import org.example.TestConfig;
import org.example.model.Comment;
import org.example.repository.CommentRepository;
import org.example.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Test
    void testInsertComment() {
        final String text = UUID.randomUUID().toString();
        Long postId = 1L;
        Comment validComment = new Comment(1L,postId,text);
        commentService.insert(validComment);

        List<Comment> savedComments = commentRepository.findByPostId(postId);
        Comment foundComment = savedComments.stream().filter(p -> p.getText().equals(text)).findAny().get();
        Comment savedComment = commentRepository.findById(foundComment.getId());
        assertNotNull(savedComment, "Saved comment should not be null");
        assertEquals(text, savedComment.getText(), "Comment text should match");
    }

    @Test
    void testEditComment() {
        final String text = UUID.randomUUID().toString();
        Long postId = 1L;
        Comment validComment = new Comment(1L,postId,text);
        commentService.insert(validComment);

        List<Comment> savedComments = commentRepository.findByPostId(postId);
        Comment foundComment = savedComments.stream().filter(p -> p.getText().equals(text)).findAny().get();
        Comment savedComment = commentRepository.findById(foundComment.getId());
        assertNotNull(savedComment, "Saved comment should not be null");
        assertEquals(text, savedComment.getText(), "Comment text should match");

        final String newText = UUID.randomUUID().toString();
        savedComment.setText(newText);
        commentRepository.update(savedComment);

        Comment savedCommentAfterEdit = commentRepository.findById(savedComment.getId());
        assertEquals(newText, savedCommentAfterEdit.getText(), "Comment text should match");
    }

    @Test
    void testDeleteCommentById() {
        final String text = UUID.randomUUID().toString();
        Long postId = 1L;
        Comment validComment = new Comment(1L,postId,text);
        commentService.insert(validComment);

        List<Comment> savedComments = commentRepository.findByPostId(postId);
        Comment foundComment = savedComments.stream().filter(p -> p.getText().equals(text)).findAny().get();
        Comment savedComment = commentRepository.findById(foundComment.getId());
        assertNotNull(savedComment, "Saved comment should not be null");
        assertEquals(text, savedComment.getText(), "Comment text should match");

        commentRepository.deleteById(foundComment.getId());
        assertThrows(NoSuchElementException.class, () -> commentRepository.findById(foundComment.getId()));

    }

    @Test
    void testDeleteCommentByPostId() {
        final String text = UUID.randomUUID().toString();
        Long postId = 1L;
        Comment validComment = new Comment(1L,postId,text);
        commentService.insert(validComment);

        List<Comment> savedComments = commentRepository.findByPostId(postId);
        Comment foundComment = savedComments.stream().filter(p -> p.getText().equals(text)).findAny().get();
        Comment savedComment = commentRepository.findById(foundComment.getId());
        assertNotNull(savedComment, "Saved comment should not be null");
        assertEquals(text, savedComment.getText(), "Comment text should match");

        commentRepository.deleteByPostId(foundComment.getPostId());
        assertThrows(NoSuchElementException.class, () -> commentRepository.findById(foundComment.getId()));

    }

}