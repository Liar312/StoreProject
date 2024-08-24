package com.example.storeproject.CacheTests;

import com.example.storeproject.Cache.CacheUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CacheEmailKeyTest extends AbstractTest{
    @Autowired
    private CacheUserService cacheUserService;
    @Test
    public void CreateAndCacheByEmailTest(){

    }

    public void CreateAndPrintEmailCacheTest(String name,String email){
        log.info("create user{}",cacheUserService.createAndCacheByEmail(name,email));
    }

}
