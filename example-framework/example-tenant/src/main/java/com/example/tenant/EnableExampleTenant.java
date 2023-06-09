package com.example.tenant;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
public @interface EnableExampleTenant {

}
