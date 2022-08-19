package com.example.client;

import com.example.ws.configs.WebServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebServiceConfig.class)
public class Main {

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }

}
