package org.example.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class JdbcNativeLikeRepository implements LikeRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcNativeLikeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findByPostId(Long postId) {
        return jdbcTemplate.query(
                "select sum(case when reaction then 1 else -1 end) as likesCount from likes where postId = ?",
                (rs, rowNum) -> rs.getInt("likesCount"),
                postId
        ).getFirst();
    }

    @Override
    public void insert(Long postId, boolean reaction, Instant now) {
        jdbcTemplate.update("insert into likes(postId, reaction, dateTime) values(?, ?, ?)",
                postId, reaction, now);
    }

    @Override
    public void deleteByPostId(Long postId) {
        jdbcTemplate.update("delete from likes where postId = ?", postId);
    }

}
