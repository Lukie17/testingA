package com.example.testingA.repository;

import com.example.testingA.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUser() {
        User user = new User(null, "Luke", "luke@test.com");

        userRepository.save(user);

        Optional<User> result = userRepository.findById(user.getId());

        assertTrue(result.isPresent());

        User savedUser = result.get();

        assertAll(
            () -> assertEquals("Luke", savedUser.getName()),
            () -> assertEquals("luke@test.com", savedUser.getEmail()),
            () -> assertNotNull(savedUser.getId())
        );
    }
    @Test
    void shouldFindAllUsers() {
        userRepository.save(new User(null, "Luke", "luke@test.com"));
        userRepository.save(new User(null, "John", "john@test.com"));
        userRepository.save(new User(null, "Niamh", "niamh@yahoo.com"));

        List<User> users = userRepository.findAll();

        assertEquals(3, users.size());
    }
    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        Optional<User> result = userRepository.findById(999L);

        assertTrue(result.isEmpty());
    }
}