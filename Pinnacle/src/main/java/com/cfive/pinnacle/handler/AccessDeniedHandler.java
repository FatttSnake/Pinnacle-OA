package com.cfive.pinnacle.handler;

import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.utils.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        String objectResponse = WebUtil.objectResponse(ResponseCode.ACCESS_DENIED, "Access denied", null);
        WebUtil.renderString(response, objectResponse);
    }
}
