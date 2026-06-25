package com.srgec.quiz_app.service;

import com.srgec.quiz_app.config.JwtService;
import com.srgec.quiz_app.models.User;
import com.srgec.quiz_app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public UserService(UserRepository repository, PasswordEncoder encoder, JwtService jwtService) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public User createUser(User user) {
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return repository.save(user);
    }

    public String login(String email, String password) {
        Optional<User> foundUser = repository.findByEmail(email);

        if (foundUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = foundUser.get();
        boolean matches = encoder.matches(password, user.getPassword());

        if (!matches) {
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateToken(email);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public String deleteUser(int id) {
        repository.deleteById(id);
        return "User deleted successfully";
    }
}