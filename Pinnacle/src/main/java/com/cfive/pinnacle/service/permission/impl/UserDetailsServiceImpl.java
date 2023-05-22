package com.cfive.pinnacle.service.permission.impl;

import com.cfive.pinnacle.entity.permission.User;
import com.cfive.pinnacle.entity.permission.LoginUser;
import com.cfive.pinnacle.service.permission.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private IUserService userService;

    @Lazy
    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserWithPower(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Username not found in database");
        }

        return new LoginUser(user);
    }
}
