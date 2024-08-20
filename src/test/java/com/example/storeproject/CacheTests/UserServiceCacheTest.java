package com.example.storeproject.CacheTests;

import com.example.storeproject.models.User;
import com.example.storeproject.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserServiceCacheTest extends AbstractTest {
    @Autowired
    private UserService userService;

    @Test
    public void get(){
             User user1 = userService.create(new User())
    }

}
