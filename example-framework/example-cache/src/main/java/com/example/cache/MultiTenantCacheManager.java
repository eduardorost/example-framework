package com.example.cache;

import com.example.tenant.storage.TenantStorage;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

public class MultiTenantCacheManager extends RedisCacheManager {

    public MultiTenantCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
        RedisCacheManager.builder()
                .cacheWriter(cacheWriter)
                .cacheDefaults(defaultCacheConfiguration)
                .build();
    }

    @Override
    public Cache getCache(String name) {
        return super.getCache(String.format("%s_%s", TenantStorage.getTenantId(), name));
    }
}
