package com.cfive.pinnacle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class PinnacleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PinnacleApplication.class, args);
    }

}
