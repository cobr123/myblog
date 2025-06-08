package org.example.repository;


import java.time.Instant;

public interface LikeRepository {

    void insert(Long postId, boolean reaction, Instant now);

    int findByPostId(Long postId);

    void deleteByPostId(Long postId);

} 