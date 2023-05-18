package com.cfive.pinnacle.handler;

import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        e.printStackTrace();
        if (e instanceof DuplicateKeyException) {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "无法添加重复数据", null);
        }
        return ResponseResult.fail(e.getClass().toString() + ": " + e.getMessage());
    }
}
