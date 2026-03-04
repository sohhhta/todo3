package com.example.todo.service.user;

import com.example.todo.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void create(String username, String password) {
        var encodedPassword = passwordEncoder.encode(password);
        var user = new UserEntity(username, username, encodedPassword, "USER"); // IDとユーザー名は同じにする
        taskRepository.insertUser(user);
    }
}