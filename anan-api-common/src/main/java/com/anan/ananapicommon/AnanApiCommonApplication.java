package com.anan.ananapicommon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.anan.ananapicommon.mapper")
public class AnanApiCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnanApiCommonApplication.class, args);
    }

}
