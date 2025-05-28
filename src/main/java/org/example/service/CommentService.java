package org.example.service;

import org.example.model.Comment;
import org.example.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentService {

    private final CommentRepository repository;

    public CommentService(CommentRepository commentRepository) {
        this.repository = commentRepository;
    }

    public List<Comment> findByPostId(Long postId) {
        return repository.findByPostId(postId);
    }

    public Comment findById(Long id) {
        return repository.findById(id);
    }

    public void insert(Comment comment) {
        repository.insert(comment);
    }

    public void update(Comment comment) {
        repository.update(comment);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
