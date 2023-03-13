package com.anan.ananapisdk;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("yuapi.client")
@Data
@ComponentScan
public class AnanApiClientConfig {

    public static void main(String[] args) {
        SpringApplication.run(AnanApiClientConfig.class, args);
    }

}
