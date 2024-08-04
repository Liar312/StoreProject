package com.example.storeproject.services;

import com.example.storeproject.models.User;
import com.example.storeproject.models.enums.Role;
import com.example.storeproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j//logger
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;


    public boolean createUser(User user){
        String email = user.getEmail();
        if(userRepository.findByEmail(email)!=null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);//добавляем базовую роль юзера
        log.info("Saving new User with email: {}",email);
        userRepository.save(user);
        return true;
    }

    public User getUserByUsername(String username){
        return userRepository.findByEmail(username);

    }

}
