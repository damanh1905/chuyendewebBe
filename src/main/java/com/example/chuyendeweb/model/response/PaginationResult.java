package com.example.chuyendeweb.model.response;

import java.util.ArrayList;
import java.util.List;

public class PaginationResult<T> {
    private List<T> items = new ArrayList<>();
    private Long totalLength;

    public PaginationResult(List<T> items, Long totalLength) {
        this.items = items;
        this.totalLength = totalLength;
    }

    public PaginationResult() {
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Long totalLength) {
        this.totalLength = totalLength;
    }

    @Override
    public String toString() {
        return "PaginationResult{" +
                "items=" + items +
                ", totalLength=" + totalLength +
                '}';
    }
}
