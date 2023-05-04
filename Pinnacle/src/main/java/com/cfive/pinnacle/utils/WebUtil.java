package com.cfive.pinnacle.utils;

import com.cfive.pinnacle.entity.common.ResponseResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class WebUtil {
    public static String convert2json(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static String objectResponse(int resultCode, String msg, Object object) throws JsonProcessingException {
        ResponseResult result = ResponseResult.build(resultCode, msg, object);
        return convert2json(result);
    }

    public static void renderString(HttpServletResponse response, String string) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(string);
    }
}
