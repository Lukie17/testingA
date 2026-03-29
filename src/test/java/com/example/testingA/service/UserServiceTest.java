package com.example.testingA.service;

import com.example.testingA.model.User;
import com.example.testingA.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnUserById() {
        User user = new User(1L, "Luke", "luke@test.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals("Luke", result.getName());
        verify(userRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1L);
        });
    }
    @Test
    void shouldCreateUser() {
        User user = new User(null, "Luke", "luke@test.com");

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals("Luke", result.getName());
        verify(userRepository).save(user);
    }
    @Test
    void shouldReturnSavedUser() {
        User user = new User(null, "Luke", "luke@test.com");

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals("luke@test.com", result.getEmail());
    }
    @Test
    void shouldCallRepositoryOnceWhenGettingUser() {
        User user = new User(1L, "Luke", "luke@test.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.getUserById(1L);

        verify(userRepository, times(1)).findById(1L);
    }
    @Test
    void shouldReturnCorrectExceptionMessage() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1L);
        });

        assertEquals("User not found", exception.getMessage());
    }
    @Test
    void shouldReturnCorrectUserObject() {
        User user = new User(1L, "Luke", "luke@test.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertAll(
            () -> assertEquals(1L, result.getId()),
            () -> assertEquals("Luke", result.getName()),
            () -> assertEquals("luke@test.com", result.getEmail())
        );
    }
}