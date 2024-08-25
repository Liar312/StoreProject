package com.example.storeproject.CacheTests;

import com.example.storeproject.Cache.CacheUser;
import com.example.storeproject.Cache.CacheUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class PutCacheTest {

    private CacheUserService cacheUserService;
    CacheUser user1 = cacheUserService.createOrReturnedCache(new CacheUser("Vasya", "vasya@mail.ru"));


    CacheUser user2 = cacheUserService.createOrReturnedCache(new CacheUser("Vasya", "misha@mail.ru"));

    CacheUser user3 = cacheUserService.createAndRefreshCache(new CacheUser("Vasya", "kolya@mail.ru"));


    CacheUser user4 = cacheUserService.createOrReturnedCache(new CacheUser("Vasya", "petya@mail.ru"));



}
