package com.example.storeproject.Cache;

import com.example.storeproject.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.internal.CacheHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


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
    @Cacheable(value = "users",key = "#name")
    public CacheUser create(String name,String email){
        log.info("creating new user with parameters: {},{}",name,email);//таким образом мы будем отделять кеширование по ключу имени и объекты с таким именем не будут кешироваться дважды
        return cacheUserRepository.save(new CacheUser(name,email));
    }
    @Cacheable(value = "users",key = "#email")
    public CacheUser createAndCacheByEmail(String name,String email){
        log.info("создан пользователь со следующими данными: {},{},(логирование фильтруется по email)",name,email);
        return cacheUserRepository.save(new CacheUser(name,email)); //логика предыдущего метода сохраняется но ключ email
    }

    public List<CacheUser> getAll(){
        return cacheUserRepository.findAll();//простой выборник всей репы
    }

    @Cacheable(value="users",key="cacheUser.name")
    public CacheUser createOrReturnedCache(CacheUser cacheUser){
        log.info("creating user{}",cacheUser);
        return cacheUserRepository.save(cacheUser);
    }

    @CachePut(value = "users",key = "#cacheUser.name")
    public CacheUser createAndRefreshCache(CacheUser cacheUser){
        log.info("creating user{}",cacheUser);
        return cacheUserRepository.save(cacheUser);
    }



}
