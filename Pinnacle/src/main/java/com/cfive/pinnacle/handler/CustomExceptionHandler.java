package com.cfive.pinnacle.handler;

import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        if (e instanceof DuplicateKeyException) {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "无法添加重复数据", null);
        }
        if (e instanceof BadCredentialsException) {
            return ResponseResult.build(ResponseCode.LOGOUT_FAILED, e.getMessage(), null);
        }

        log.debug(e.getMessage(), e);

        return ResponseResult.fail(e.getClass().toString() + ": " + e.getMessage());
    }
}
