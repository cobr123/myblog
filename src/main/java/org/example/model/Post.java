package org.example.model;

import java.util.Arrays;
import java.util.List;

public class Post {
    private Long id;
    private String title;
    private String tags;
    private String text;
    private String imagePath;
    private int likesCount;
    private List<Comment> comments;

    // Конструктор без аргументов
    public Post() {
    }

    // Конструктор с аргументами для удобства использования
    public Post(Long id, String title, String tags, String text, String imagePath, int likesCount, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.text = text;
        this.imagePath = imagePath;
        this.likesCount = likesCount;
        this.comments = comments;
    }

    // Геттеры и сеттеры ...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return Arrays.asList(tags.split(" "));
    }

    public String getTagsAsText() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getText() {
        return text;
    }

    public List<String> getTextParts() {
        // текст поста, разбитый на абзацы
        return Arrays.asList(text.split("\r?\n"));
    }

    // первый абзац не больше трёх строк
    public String getTextPreview() {
        int maxLen = 512;
        List<String> parts = getTextParts();
        if (parts.size() <= 1) {
            if (text.length() < maxLen) {
                return text;
            } else {
                return text.substring(0, maxLen) + "..";
            }
        } else if (parts.getFirst().length() < maxLen) {
            return parts.getFirst() + "..";
        } else {
            return parts.getFirst().substring(0, maxLen) + "..";
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
