package org.example.model;

import java.util.List;

public record Posts(List<Post> posts, Paging paging) {
}
