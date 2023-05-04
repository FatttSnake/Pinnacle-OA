package com.cfive.pinnacle.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
class IUserServiceTest {

    @Test
    void getAllUser(@Autowired IUserService userService) {
        userService.list().forEach(System.out::println);
    }

    @Test
    void getBCy(@Autowired PasswordEncoder passwordEncoder) {
        System.out.println(passwordEncoder.encode("123"));
    }
}