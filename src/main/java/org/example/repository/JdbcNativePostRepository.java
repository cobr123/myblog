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
                    "select id, title, tags, text, imagePath, likesCount from posts order by id desc offset ? limit ?",
                    (rs, rowNum) -> new Post(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("tags"),
                            rs.getString("text"),
                            rs.getString("imagePath"),
                            rs.getInt("likesCount"),
                            new ArrayList<>()
                    ),
                    getOffset(pageSize, pageNumber),
                    pageSize
            );
            return new Posts(posts, search, new Paging(pageNumber, pageSize, false, false));
        } else {
            List<Post> posts = jdbcTemplate.query(
                    "select id, title, tags, text, imagePath, likesCount from posts where tags like '%' || ? || '%' order by id desc offset ? limit ?",
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
                    getOffset(pageSize, pageNumber),
                    pageSize
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
                )
        ).getFirst();
    }

    @Override
    public void save(Post post) {
        jdbcTemplate.update("insert into posts(title, tags, text, imagePath, likesCount) values(?, ?, ?, ?, ?)",
                post.getTitle(), post.getTags(), post.getText(), post.getImagePath(), post.getLikesCount());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from posts where id = ?", id);
    }
}