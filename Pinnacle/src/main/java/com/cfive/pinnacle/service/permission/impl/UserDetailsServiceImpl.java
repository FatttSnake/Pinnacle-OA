package com.cfive.pinnacle.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cfive.pinnacle.entity.User;
import com.cfive.pinnacle.entity.permission.LoginUser;
import com.cfive.pinnacle.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private IUserService userService;

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = userService.getOne(wrapper);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Username not found in database");
        }
        return new LoginUser(user);
    }
}
