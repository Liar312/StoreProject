package com.example.storeproject.Cache;

import com.example.storeproject.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.internal.CacheHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

@Service
@Slf4j
public class CacheUserService {
    @Autowired
    private CacheUserRepository cacheUserRepository;


    @Cacheable("users")
    public CacheUser get(Long id){
        log.info("getting user by id: {}", id);
        return cacheUserRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found by id " + id));
    }

    public CacheUser create(CacheUser cacheUser){
        return cacheUserRepository.save(cacheUser);
    }

}
