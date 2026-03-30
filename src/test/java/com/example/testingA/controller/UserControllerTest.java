package com.example.testingA.controller;

import com.example.testingA.model.User;
import com.example.testingA.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import com.example.testingA.exception.GlobalExceptionHandler;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(GlobalExceptionHandler.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldReturnUser() throws Exception {
        User user = new User(1L, "Luke", "luke@test.com");

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Luke"));
    }
    @Test
    void shouldCreateUser() throws Exception {
        User user = new User(1L, "Luke", "luke@test.com");

        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content("""
                    {
                        "name": "Luke",
                        "email": "luke@test.com"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Luke"));
    }
    @Test
    void shouldReturnFullUserJson() throws Exception {
        User user = new User(1L, "Luke", "luke@test.com");

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Luke"))
                .andExpect(jsonPath("$.email").value("luke@test.com"));
    }
    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {
        when(userService.getUserById(1L))
                .thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }
}