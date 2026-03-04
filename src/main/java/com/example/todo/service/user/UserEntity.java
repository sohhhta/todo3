package com.example.todo.service.user;

public record UserEntity(
    String id,
    String username,
    String password,
    String authority
) {
}