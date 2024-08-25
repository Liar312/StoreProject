package com.example.storeproject.services;

import com.example.storeproject.models.User;
import com.example.storeproject.models.enums.Role;
import com.example.storeproject.repositories.UserRepository;
import com.google.common.cache.CacheBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.annotations.DialectOverride;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j//logger
@RequiredArgsConstructor
@Component

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);//добавляем базовую роль юзера
        log.info("Saving new User with email: {}", email);
        userRepository.save(user);
        return true;
    }

    @Cacheable(value = "users", key = "#username")
    public Optional<User> getUserByUsername(String username) {
        log.info("запрошенный пользователь{} закеширован", username);
        return Optional.ofNullable(userRepository.findByEmail(username));

    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public boolean deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
        return true;
    }

    @CacheEvict("users")//без этой аннотации даже при удалении сущности из бд она остается в кеше
    public void deleteUserFromCache(Long id, String username) {
        User nowUser = userRepository.findByEmail(username);
        log.info("пользоывтель с именем {} удален из кеша ", nowUser.getName());
        userRepository.deleteById(id);
    }



    }


//   @Cacheable("users")
//    public User get(Long id){
//        log.info("getting user by id: {}",id);
//        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"+id));  перенес в CacheUserService
//   }
//
//   public User create(User user){
//       return  userRepository.save(user);  метод пренесен в CacheUserService
//    }}
