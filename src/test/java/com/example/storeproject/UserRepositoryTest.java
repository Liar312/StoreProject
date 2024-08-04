package com.example.storeproject;

import com.example.storeproject.models.User;
import com.example.storeproject.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void FindUserByNameAndEmailTest(){
        User user = new User();

        user.setName("Test Name");
        user.setEmail("TestEmail");

        userRepository.save(user);

        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail("TestEmail"));
        assertTrue(foundUser.isPresent());//проверка на существование
        assertEquals("Test Name",foundUser.get().getName());
    }
}
