package com.warehouse_manager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching //1.开启Redis注解的缓存
public class WarehouserManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouserManagerApplication.class, args);
    }

}
