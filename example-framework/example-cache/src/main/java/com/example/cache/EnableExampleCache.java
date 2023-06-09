package com.example.cache;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({MultiTenantCacheManager.class, CachingConfig.class})
@Configuration
public @interface EnableExampleCache {

}
