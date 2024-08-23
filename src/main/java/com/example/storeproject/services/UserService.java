package com.example.storeproject.services;

import com.example.storeproject.models.User;
import com.example.storeproject.models.enums.Role;
import com.example.storeproject.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DialectOverride;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j//logger
@RequiredArgsConstructor
@Component
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

    public Optional<User> getUserByUsername(String username){
        return Optional.ofNullable(userRepository.findByEmail(username));

    }
   public List<User> getAllUsers(){
        return userRepository.findAll();
   }
   public boolean deleteUser(String username){
        userRepository.deleteUserByUsername(username);
       return true;
   }

//   @Cacheable("users")
//    public User get(Long id){
//        log.info("getting user by id: {}",id);
//        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"+id));  перенес в CacheUserService
//   }
//
//   public User create(User user){
//       return  userRepository.save(user);  метод пренесен в CacheUserService
//    }
}
