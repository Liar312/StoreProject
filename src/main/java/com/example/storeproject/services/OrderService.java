package com.example.storeproject.services;

import com.example.storeproject.models.Product;
import com.example.storeproject.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OrderService {
    private ProductService productService;
//    @Async("taskExecutor")
//    public CompletableFuture<String> processOrder(Long orderId){
//        log.info("Идет создание и оформление закза");
//        try{
//            Thread.sleep(5000);//дилей 5 секунд
//        }
//        catch (InterruptedException e){
//            Thread.currentThread().interrupt();
//        }
//        return CompletableFuture.completedFuture("Order processing interrupted");
//    }
    @Async("taskExecutor")
    public CompletableFuture<String> processOrder(Long orderId,Long productId ){
        String apiResponse = SimulateApiCall(orderId,productId);

        String result = "Order" + orderId + "processed with response" + apiResponse;

        return CompletableFuture.completedFuture(result);
    }

    private String SimulateApiCall(Long orderId,Long productid){
        if (productService.ProductExist(productid)){
            return "API Response for order " + orderId;
        }
        return "this order " +orderId + "cant be because product" + productid + "doesnt exist";
    }
}
