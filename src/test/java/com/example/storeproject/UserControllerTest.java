package com.example.storeproject;

import com.example.storeproject.controllers.UserController;
import com.example.storeproject.models.User;
import com.example.storeproject.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)

public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetUserByUsername() throws Exception{
        User user = new User();
        user.setName("testuser");
        when(userService.getUserByUsername("testuser")).thenReturn(Optional.of(user));
        mockMvc.perform(get("/users/testuser"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.firstname").value("Test"))
                .andExpect(jsonPath("$.lastname").value("User"));
    }


    @Test
    public void testGetUserByUsername_NotFound() throws Exception {
        when(userService.getUserByUsername("nonexistenuser")).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/nonexistenuser"))
                .andExpect(status().isNotFound());
    }

}
