package com.cfive.pinnacle.filter;

import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.permission.LoginUser;
import com.cfive.pinnacle.utils.JwtUtil;
import com.cfive.pinnacle.utils.RedisCache;
import com.cfive.pinnacle.utils.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            JwtUtil.parseJWT(token);
        } catch (Exception e) {
            String objectResponse = WebUtil.objectResponse(ResponseCode.TOKEN_IS_ILLEGAL, "Token is illegal", null);
            WebUtil.renderString(response, objectResponse);
            return;
        }

        String redisKey = "login:" + token;
        LoginUser loginUser = new ObjectMapper().convertValue(redisCache.getCacheObject(redisKey), LoginUser.class);
        if (Objects.isNull(loginUser)) {
            String objectResponse = WebUtil.objectResponse(ResponseCode.TOKEN_HAS_EXPIRED, "Token has expired", null);
            WebUtil.renderString(response, objectResponse);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
