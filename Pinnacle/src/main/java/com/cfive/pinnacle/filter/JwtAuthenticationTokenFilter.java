package com.cfive.pinnacle.filter;

import com.cfive.pinnacle.entity.permission.LoginUser;
import com.cfive.pinnacle.exception.TokenHasExpiredException;
import com.cfive.pinnacle.utils.JwtUtil;
import com.cfive.pinnacle.utils.RedisCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Nonnull;
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
import java.util.concurrent.TimeUnit;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private RedisCache redisCache;

    @Autowired
    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");

        if (!StringUtils.hasText(token) || "/error/thrown".equals(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        JwtUtil.parseJWT(token);

        String redisKey = "login:" + token;
        LoginUser loginUser = new ObjectMapper().registerModule(new JavaTimeModule()).convertValue(redisCache.getCacheObject(redisKey), LoginUser.class);
        if (Objects.isNull(loginUser)) {
            throw new TokenHasExpiredException();
        }
        redisCache.expire(redisKey, 20, TimeUnit.MINUTES);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
