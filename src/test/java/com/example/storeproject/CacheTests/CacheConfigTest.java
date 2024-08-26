package com.example.storeproject.CacheTests;

import com.example.storeproject.Cache.CacheUser;
import com.example.storeproject.Cache.CacheUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class CacheConfigTest {

    private CacheUserService cacheUserService;
    @Test
    public void checkSettings() throws InterruptedException{
        CacheUser cacheUser1 = cacheUserService.createOrReturnedCache(new CacheUser("Vasya", "vasya@mail.ru"));
        log.info("{}",cacheUserService.get(cacheUser1.getId()));

        CacheUser cacheUser2 = cacheUserService.createOrReturnedCache(new CacheUser("Vasya", "vasya@mail.ru"));
        log.info("{}",cacheUserService.get(cacheUser2.getId()));


        Thread.sleep(1000L);//по настройкам нашего менеджера через 1 секунду кеш протухнет
        CacheUser cacheUser3 = cacheUserService.createOrReturnedCache(new CacheUser("Vasya", "vasya@mail.ru"));
        log.info("{}",cacheUserService.get(cacheUser3.getId()));

    }
}
