package com.cfive.pinnacle.handler;

import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.utils.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String objectResponse;
        if (authException instanceof BadCredentialsException) {
            objectResponse = WebUtil.objectResponse(ResponseCode.LOGIN_USERNAME_PASSWORD_ERROR, authException.getMessage(), null);
        } else if (authException instanceof InsufficientAuthenticationException) {
            objectResponse = WebUtil.objectResponse(ResponseCode.UNAUTHORIZED, authException.getMessage(), null);
        } else {
            objectResponse = WebUtil.objectResponse(ResponseCode.UNAUTHORIZED, authException.getClass().toString() + ": " + authException.getMessage(), null);
        }
        WebUtil.renderString(response, objectResponse);
    }
}
