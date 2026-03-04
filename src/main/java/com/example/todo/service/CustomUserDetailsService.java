package com.example.todo.service;

import com.example.todo.repository.task.TaskRepository;
import com.example.todo.service.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final TaskRepository taskRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return taskRepository.selectUserById(username)
                .map(user -> new User(
                        user.id(),
                        user.password(),
                        Collections.emptyList() // 権限リスト（今回は空でOK）
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Given username is not found. (username = '" + username + "')"));
    }
}