package com.example.storeproject;

import com.example.storeproject.models.User;
import com.example.storeproject.repositories.UserRepository;
import com.example.storeproject.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


public class ServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getUserByIdTest(){
        User user = new User();
        user.setName("testuser");

        when(userRepository.findByEmail("testuser")).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByUsername("testuser");
        assertNotNull(foundUser);
        assertEquals("testuser",foundUser.getName());
    }
}
