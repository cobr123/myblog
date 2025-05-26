package org.example.model;

import java.util.List;

public record Posts(List<Post> posts, String search, Paging paging) {
}
