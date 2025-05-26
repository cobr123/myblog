package org.example.model;

public record Paging(int pageNumber, int pageSize, boolean hasNext, boolean hasPrevious) {
}