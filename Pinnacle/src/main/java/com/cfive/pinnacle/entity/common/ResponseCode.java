package com.cfive.pinnacle.entity.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseCode {
    public static final int SYSTEM_OK = 20000;
    public static final int LOGIN_SUCCESS = 20010;
    public static final int LOGIN_USERNAME_PASSWORD_ERROR = 20011;
    public static final int OLD_PASSWORD_NOT_MATCH = 20012;
    public static final int LOGOUT_SUCCESS = 20015;
    public static final int LOGOUT_FAILED = 20016;
    public static final int TOKEN_IS_ILLEGAL = 20017;
    public static final int TOKEN_HAS_EXPIRED = 20018;
    public static final int TOKEN_RENEW_SUCCESS = 20019;
    public static final int DATABASE_SELECT_OK = 20021;
    public static final int DATABASE_SAVE_OK = 20022;
    public static final int DATABASE_UPDATE_OK = 20023;
    public static final int DATABASE_DELETE_OK = 20024;
    public static final int DATABASE_SELECT_ERROR = 20031;
    public static final int DATABASE_SAVE_ERROR = 20032;
    public static final int DATABASE_UPDATE_ERROR = 20033;
    public static final int DATABASE_DELETE_ERROR = 20034;
    public static final int DATABASE_TIMEOUT_ERROR = 20035;
    public static final int DATABASE_CONNECT_ERROR = 20036;
    public static final int DATABASE_DATA_TO_LONG = 20037;
    public static final int DATABASE_DATA_VALIDATION_FAILED = 20038;
    public static final int DATABASE_EXECUTE_ERROR = 20039;

    public static final int UNAUTHORIZED = 30010;
    public static final int ACCESS_DENIED = 30030;
    public static final int USER_DISABLE = 30031;

    public static final int SYSTEM_ERROR = 50001;
    public static final int SYSTEM_TIMEOUT = 50002;
}
