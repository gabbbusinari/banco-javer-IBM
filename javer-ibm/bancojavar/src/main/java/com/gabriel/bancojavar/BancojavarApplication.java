package com.gabriel.bancojavar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BancojavarApplication {

    public static void main(String[] args) {
        SpringApplication.run(BancojavarApplication.class, args);
    }

}
