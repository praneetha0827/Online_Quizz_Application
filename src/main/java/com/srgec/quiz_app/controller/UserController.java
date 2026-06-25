package com.srgec.quiz_app.controller;

import com.srgec.quiz_app.dto.LoginRequest;
import com.srgec.quiz_app.models.User;
import com.srgec.quiz_app.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.createUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return service.login(request.getEmail(), request.getPassword());
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {
        return service.deleteUser(id);
    }
}