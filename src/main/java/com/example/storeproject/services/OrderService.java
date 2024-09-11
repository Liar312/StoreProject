package com.example.storeproject.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OrderService {
    @Async("taskExecutor")
    public CompletableFuture<String> processOrder(Long orderId){
        log.info("Идет создание и оформление закза");
        try{
            Thread.sleep(5000);//дилей 5 секунд
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        return CompletableFuture.completedFuture("Order processing interrupted");
    }
}
