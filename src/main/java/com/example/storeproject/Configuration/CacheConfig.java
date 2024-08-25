package com.example.storeproject.Configuration;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
@Configuration

public class CacheConfig {
    @Bean("cacheManager")
    public CacheManager cacheManager() { //создаем свой кеш менеджер
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(
                        name,
                        CacheBuilder.newBuilder()
                                .expireAfterWrite(1, TimeUnit.SECONDS)
                                .maximumSize(2132132)
                                .build().asMap(),
                        false
                );
            }
        };
    }
}

