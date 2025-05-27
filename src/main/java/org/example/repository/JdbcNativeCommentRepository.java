package org.example.repository;

import org.example.model.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcNativeCommentRepository implements CommentRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcNativeCommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        return jdbcTemplate.query(
                "select id, postId, text from comments where postId = ?",
                (rs, rowNum) -> new Comment(
                        rs.getLong("id"),
                        rs.getLong("postId"),
                        rs.getString("text")
                ),
                postId
        );
    }

    @Override
    public Comment findById(Long id) {
        return jdbcTemplate.query(
                "select id, postId, text from comments where id = ?",
                (rs, rowNum) -> new Comment(
                        rs.getLong("id"),
                        rs.getLong("postId"),
                        rs.getString("text")
                ),
                id
        ).getFirst();
    }

    @Override
    public void insert(Comment comment) {
        jdbcTemplate.update("insert into comments(postId, text) values(?, ?)",
                comment.getPostId(), comment.getText());
    }

    @Override
    public void update(Comment comment) {
        jdbcTemplate.update("update comments set text = ? where id = ?",
                comment.getText(), comment.getId());
    }

    @Override
    public void deleteByPostId(Long postId) {
        jdbcTemplate.update("delete from comments where postId = ?", postId);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from comments where id = ?", id);
    }
}
