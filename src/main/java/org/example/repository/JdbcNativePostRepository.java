package org.example.repository;

import org.example.model.Paging;
import org.example.model.Post;
import org.example.model.Posts;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcNativePostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcNativePostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Posts findAll(String search, int pageSize, int pageNumber) {
        if (search == null || search.isEmpty()) {
            List<Post> posts = jdbcTemplate.query(
                    "select id, title, tags, text, imagePath, likesCount from posts order by id desc limit ? offset ?",
                    (rs, rowNum) -> new Post(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("tags"),
                            rs.getString("text"),
                            rs.getString("imagePath"),
                            rs.getInt("likesCount"),
                            new ArrayList<>()
                    ),
                    pageSize,
                    getOffset(pageSize, pageNumber)
            );
            return new Posts(posts, search, new Paging(pageNumber, pageSize, false, false));
        } else {
            List<Post> posts = jdbcTemplate.query(
                    "select id, title, tags, text, imagePath, likesCount from posts where tags like concat('%', ?, '%') order by id desc limit ? offset ?",
                    (rs, rowNum) -> new Post(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("tags"),
                            rs.getString("text"),
                            rs.getString("imagePath"),
                            rs.getInt("likesCount"),
                            new ArrayList<>()
                    ),
                    search,
                    pageSize,
                    getOffset(pageSize, pageNumber)
            );
            return new Posts(posts, search, new Paging(pageNumber, pageSize, false, false));
        }
    }

    private int getOffset(int pageSize, int pageNumber) {
        return (pageNumber - 1) * pageSize;
    }

    @Override
    public Post findById(Long id) {
        return jdbcTemplate.query(
                "select id, title, tags, text, imagePath, likesCount from posts where id = ?",
                (rs, rowNum) -> new Post(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("tags"),
                        rs.getString("text"),
                        rs.getString("imagePath"),
                        rs.getInt("likesCount"),
                        new ArrayList<>()
                ),
                id
        ).getFirst();
    }

    @Override
    public void insert(Post post) {
        jdbcTemplate.update("insert into posts(title, tags, text, imagePath, likesCount) values(?, ?, ?, ?, ?)",
                post.getTitle(), post.getTags(), post.getText(), post.getImagePath(), post.getLikesCount());
    }

    @Override
    public void update(Post post) {
        jdbcTemplate.update("update posts set title = ?, tags = ?, text = ?, imagePath = ?, likesCount = ? where id = ?",
                post.getTitle(), post.getTags(), post.getText(), post.getImagePath(), post.getLikesCount(), post.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from posts where id = ?", id);
    }
}