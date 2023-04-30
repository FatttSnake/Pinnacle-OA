package com.cfive.pinnacle.entity.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private Object data;

    public static ResponseResult build(int code, String msg, Object data) {
        return new ResponseResult(code, msg, data);
    }

    public static ResponseResult success() {
        return success(null);
    }

    public static ResponseResult success(String msg) {
        return success(msg, null);
    }

    public static ResponseResult success(Object data) {
        return success("success", data);
    }

    public static ResponseResult success(String msg, Object data) {
        return build(ResponseCode.SYSTEM_OK, msg, data);
    }

    public static ResponseResult fail() {
        return fail(null);
    }

    public static ResponseResult fail(String msg) {
        return fail(msg, null);
    }

    public static ResponseResult fail(Object data) {
        return fail("error", data);
    }

    public static ResponseResult fail(String msg, Object data) {
        return build(ResponseCode.SYSTEM_ERROR, msg, data);
    }
}
