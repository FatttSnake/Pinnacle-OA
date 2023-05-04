package com.cfive.pinnacle.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cfive.pinnacle.entity.permission.LoginUser;
import com.cfive.pinnacle.utils.JwtUtil;
import com.cfive.pinnacle.utils.RedisCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private RedisCache redisCache;

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String userId;
        try {
            DecodedJWT decodedJWT = JwtUtil.parseJWT(token);
            userId = decodedJWT.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token is illegal");
        }

        String redisKey = "login:" + userId;
        LoginUser loginUser = new ObjectMapper().convertValue(redisCache.getCacheObject(redisKey), LoginUser.class);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("Not logged in");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
