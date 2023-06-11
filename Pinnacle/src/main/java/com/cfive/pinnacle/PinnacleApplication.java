package com.cfive.pinnacle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PinnacleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PinnacleApplication.class, args);
    }

}
