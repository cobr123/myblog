package org.example.service;

import org.example.model.Post;
import org.example.model.Posts;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Service;


@Service
public class PostService {

    private final PostRepository userRepository;

    public PostService(PostRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Posts findAll(String search, int pageSize, int pageNumber) {
        return userRepository.findAll(search, pageSize, pageNumber);
    }

    public Post findById(Long id) {
        return userRepository.findById(id);
    }

    public void save(Post user) {
        userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}