package org.example.repository;

import org.example.model.Post;
import org.example.model.Posts;


public interface PostRepository {
    Posts findAll(String search, int pageSize, int pageNumber);

    Post findById(Long id);

    Long insert(Post post);

    void update(Post post);

    void deleteById(Long id);

    void deleteAll();
} 