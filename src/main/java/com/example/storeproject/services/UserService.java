package com.example.storeproject.services;

import com.example.storeproject.models.User;
import com.example.storeproject.models.enums.Role;
import com.example.storeproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j//logger
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean createUser(User user){
        if(userRepository.findByEmail(user.getEmail())!=null) return false;
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);//добавляем базовую роль юзера
    }

}
