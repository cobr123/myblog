package org.example.repository;

import org.example.model.Paging;
import org.example.model.Post;
import org.example.model.Posts;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
        List<Post> posts;
        int totalCount;
        if (search == null || search.isEmpty()) {
            totalCount = jdbcTemplate.query(
                    "select count(*) as totalCount from posts",
                    (rs, rowNum) -> rs.getInt("totalCount")
            ).getFirst();
            posts = jdbcTemplate.query(
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
        } else {
            totalCount = jdbcTemplate.query(
                    "select count(*) as totalCount from posts where tags like concat('%', ?, '%')",
                    (rs, rowNum) -> rs.getInt("totalCount"),
                    search
            ).getFirst();
            posts = jdbcTemplate.query(
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
        }
        return new Posts(posts, new Paging(pageNumber, pageSize, totalCount > pageSize * pageNumber, pageNumber > 1));
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
    public Long insert(Post post) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement("insert into posts(title, tags, text, imagePath, likesCount) values(?, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, post.getTitle());
                    ps.setString(2, post.getTagsAsText());
                    ps.setString(3, post.getText());
                    ps.setString(4, post.getImagePath());
                    ps.setInt(5, post.getLikesCount());
                    return ps;
                },
                keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Post post) {
        jdbcTemplate.update("update posts set title = ?, tags = ?, text = ?, imagePath = ?, likesCount = ? where id = ?",
                post.getTitle(), post.getTagsAsText(), post.getText(), post.getImagePath(), post.getLikesCount(), post.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from posts where id = ?", id);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from posts");
    }
}