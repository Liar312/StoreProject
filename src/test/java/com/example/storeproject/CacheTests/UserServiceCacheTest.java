package com.example.storeproject.CacheTests;

import com.example.storeproject.Cache.CacheUser;
import com.example.storeproject.Cache.CacheUserService;
import com.example.storeproject.models.User;
import com.example.storeproject.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserServiceCacheTest extends AbstractTest {
    @Autowired
    private CacheUserService cacheUserService;


    @Test
    public void get() {
        CacheUser user1 = cacheUserService.create(new CacheUser("Vasya", "vasya@mail.ru"));
        CacheUser user2 = cacheUserService.create(new CacheUser("Kolya", "kolya@mail.ru"));
        getAndPrint(user1.getId());
        getAndPrint(user2.getId());
        getAndPrint(user1.getId());
        getAndPrint(user2.getId());// при этих обращениях первые 2 раза мы дергаем метод Get потом уже используем кешируемые данные


    }

    private void getAndPrint(Long id) {
        log.info("user found: {}", cacheUserService.get(id));//так как здесь метод get ceachable
    }

    @Test
    public void create() {
        createAndPrint("Ivan", "ivan@mail.ru");
        createAndPrint("Ivan", "ivan1122@mail.ru");
        createAndPrint("Sergey", "ivan@mail.ru");

        log.info("all enteries are below:");
        cacheUserService.getAll().forEach(u -> log.info("{}", u.toString()));
    }

    public void createAndPrint(String name, String email) {
        log.info("create user:{}", cacheUserService.create(name, email));
    }

}
