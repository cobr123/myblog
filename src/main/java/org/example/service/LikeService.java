package org.example.service;

import org.example.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class LikeService {

    private final LikeRepository repository;

    public LikeService(LikeRepository postRepository) {
        this.repository = postRepository;
    }

    public int findByPostId(Long postId) {
        return repository.findByPostId(postId);
    }

    public void insert(Long postId, boolean reaction, Instant now) {
        repository.insert(postId, reaction, now);
    }

    public void deleteByPostId(Long postId) {
        repository.deleteByPostId(postId);
    }
}