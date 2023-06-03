package com.cfive.pinnacle.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cfive.pinnacle.entity.common.ResponseCode;
import com.cfive.pinnacle.entity.common.ResponseResult;
import com.cfive.pinnacle.exception.DataValidationFailedException;
import com.cfive.pinnacle.exception.TokenHasExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseResult<?> exceptionHandler(Exception e) {
        if (e instanceof InsufficientAuthenticationException) {
            return ResponseResult.build(ResponseCode.UNAUTHORIZED, e.getMessage(), null);
        }
        if (e instanceof JWTDecodeException) {
            return ResponseResult.build(ResponseCode.TOKEN_IS_ILLEGAL, "Token is illegal", null);
        }
        if (e instanceof TokenHasExpiredException || e instanceof TokenExpiredException) {
            return ResponseResult.build(ResponseCode.TOKEN_HAS_EXPIRED, "Token has expired", null);
        }
        if (e instanceof DuplicateKeyException) {
            return ResponseResult.build(ResponseCode.DATABASE_SAVE_ERROR, "无法添加重复数据", null);
        }
        if (e instanceof BadCredentialsException) {
            return ResponseResult.build(ResponseCode.LOGIN_USERNAME_PASSWORD_ERROR, e.getMessage(), null);
        }
        if (e instanceof AccessDeniedException) {
            return ResponseResult.build(ResponseCode.ACCESS_DENIED, e.getMessage(), null);
        }
        if (e instanceof DisabledException) {
            return ResponseResult.build(ResponseCode.USER_DISABLE, e.getMessage(), null);
        }
        if (e instanceof DataIntegrityViolationException) {
            return ResponseResult.build(ResponseCode.DATABASE_DATA_TO_LONG, e.getMessage(), null);
        }
        if (e instanceof DataValidationFailedException) {
            return ResponseResult.build(ResponseCode.DATABASE_DATA_VALIDATION_FAILED, e.getMessage(), null);
        }
        if (e instanceof UncategorizedSQLException) {
            return ResponseResult.build(ResponseCode.DATABASE_EXECUTE_ERROR, "error", null);
        }

        log.debug(e.getMessage(), e);

        return ResponseResult.fail(e.getClass().toString() + ": " + e.getMessage());
    }
}
