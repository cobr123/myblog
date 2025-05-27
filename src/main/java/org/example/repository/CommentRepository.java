package org.example.repository;

import org.example.model.Comment;

import java.util.List;


public interface CommentRepository {
    List<Comment> findByPostId(Long postId);

    Comment findById(Long id);

    void insert(Comment comment);

    void update(Comment comment);

    void deleteByPostId(Long postId);

    void deleteById(Long id);
} 