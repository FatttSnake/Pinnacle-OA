package com.cfive.pinnacle.service.permission.impl;

import com.cfive.pinnacle.entity.User;
import com.cfive.pinnacle.entity.permission.LoginUser;
import com.cfive.pinnacle.service.permission.ILoginService;
import com.cfive.pinnacle.utils.JwtUtil;
import com.cfive.pinnacle.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements ILoginService {
    private AuthenticationManager authenticationManager;
    private RedisCache redisCache;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    public HashMap<String, String> login(User user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPasswd());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("Login failed");
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        loginUser.getUser().setPasswd("");
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", jwt);

        redisCache.setCacheObject("login:" + userId, loginUser);

        return hashMap;
    }

    @Override
    public boolean logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        Long userId = loginUser.getUser().getId();
        return redisCache.deleteObject("login:" + userId);
    }
}
