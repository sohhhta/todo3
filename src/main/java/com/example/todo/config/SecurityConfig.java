package com.example.todo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // cssなどは誰でもアクセスOK
                .requestMatchers(mvcMatcherBuilder.pattern("/login")).permitAll() // ログイン画面は誰でもアクセスOK
                .requestMatchers(mvcMatcherBuilder.pattern("/users/**")).permitAll() // ユーザー登録画面は誰でもアクセスOK
                .anyRequest().authenticated() // それ以外はログイン必須
            )
            .formLogin(login -> login
                .loginPage("/login") // ログイン画面のURL
                .defaultSuccessUrl("/tasks", true) // ログイン成功後の遷移先
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login") // ログアウト後の遷移先
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}