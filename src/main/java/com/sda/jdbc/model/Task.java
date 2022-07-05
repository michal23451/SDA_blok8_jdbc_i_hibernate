package com.sda.jdbc.model;

public class Task {
    private Long id;
    private String description;
    private Long userId;

    public Task(long id, String description, long userId) {
        this.id = id;
        this.description = description;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                '}';
    }
}