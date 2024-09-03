package com.example.storeproject.Configuration.BatchConfig;

import com.example.storeproject.Cache.CacheUser;
import com.example.storeproject.models.Product;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ProductProcessor implements ItemProcessor<CacheUser, Product> {
    @Override
    public Product process(final CacheUser cacheUser) throws Exception {
        String newAuthor = cacheUser.getEmail();//так email мы используем как юзернейм

        Product product = new Product();
        product.setAuthor(newAuthor);
        return product;
    }
}
