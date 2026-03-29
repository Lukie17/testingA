

package com.example.testingA.service;

import com.example.testingA.model.User;
import com.example.testingA.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        if (id == null) {
            throw new RuntimeException("User ID cannot be null");
        }

        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        if (user == null) {
            throw new RuntimeException("User cannot be null");
        }

        return userRepository.save(user);
    }
}
