package com.example.storeproject.Configuration.BatchConfig;

import com.example.storeproject.Cache.CacheUser;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserProcessor implements ItemProcessor<CacheUser,CacheUser> {//с другой конфигурацией можно получить на выходе другой тип данных
    @Override
    public CacheUser process(final CacheUser cacheUser) throws Exception {//для примера процессор будет переводить все в верхний регистр
         final String NewfirstName = cacheUser.getName().toUpperCase();
         final String NewEmail = cacheUser.getEmail().toUpperCase();

         final CacheUser transofromedCacheUser = new CacheUser(NewfirstName,NewEmail);

        return null;
    }
}
