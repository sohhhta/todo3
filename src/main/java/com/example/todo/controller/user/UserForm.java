package com.example.todo.controller.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserForm(
    @NotBlank
    @Size(max = 256)
    String username,

    @NotBlank
    @Size(min = 8, max = 256)
    String password
) {
}