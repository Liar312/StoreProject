package com.example.storeproject.Configuration.BatchConfig;

import com.example.storeproject.Cache.CacheUser;
import com.example.storeproject.models.Product;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CacheUserProcessor implements ItemProcessor<Product, CacheUser> {
    @Override
    public CacheUser process(final Product product) throws Exception {
        String newCity = product.getCity();

        CacheUser cacheUser = new CacheUser();
        cacheUser.setCity(newCity);
        return cacheUser;
    }
}
