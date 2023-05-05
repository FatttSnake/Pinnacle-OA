package com.cfive.pinnacle.service;

import com.cfive.pinnacle.entity.User;
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

    @Test
    void addUser(@Autowired IUserService userService, @Autowired PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername("ggb");
        user.setPasswd(passwordEncoder.encode("123"));
        user.setDepartmentId(1652713919467151362L);
        user.setEnable(1);
        userService.save(user);
    }

    @Test
    void removeUser(@Autowired IUserService userService) {
        userService.removeById(1);
    }
}