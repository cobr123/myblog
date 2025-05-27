package org.example.service;

import org.example.model.Post;
import org.example.model.Posts;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Service;


@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository postRepository) {
        this.repository = postRepository;
    }

    public Posts findAll(String search, int pageSize, int pageNumber) {
        return repository.findAll(search, pageSize, pageNumber);
    }

    public Post findById(Long id) {
        return repository.findById(id);
    }

    public void insert(Post post) {
        repository.insert(post);
    }

    public void update(Post post) {
        repository.update(post);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}