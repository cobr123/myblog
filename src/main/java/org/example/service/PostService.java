package org.example.service;

import org.example.model.Post;
import org.example.model.Posts;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Service;


@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Posts findAll(String search, int pageSize, int pageNumber) {
        return postRepository.findAll(search, pageSize, pageNumber);
    }

    public Post findById(Long id) {
        return postRepository.findById(id);
    }

    public void insert(Post user) {
        postRepository.insert(user);
    }

    public void update(Post user) {
        postRepository.update(user);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}