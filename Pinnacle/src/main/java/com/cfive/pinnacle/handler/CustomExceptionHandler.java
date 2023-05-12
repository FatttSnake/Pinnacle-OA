package com.cfive.pinnacle.handler;

import com.cfive.pinnacle.entity.common.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        return ResponseResult.fail(e.getClass().toString() + ": " + e.getMessage());
    }
}
