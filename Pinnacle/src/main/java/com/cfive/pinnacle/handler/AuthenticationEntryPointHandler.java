package com.cfive.pinnacle.handler;

import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.utils.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String objectResponse = WebUtil.objectResponse(ResponseCode.UNAUTHORIZED, "Unauthorized access", null);
        WebUtil.renderString(response, objectResponse);
    }
}
