package com.cfive.pinnacle.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT 工具类
 */
public class JwtUtil {

    // 有效期
    public static final Long JWT_TTL = 60 * 60 * 1000L; // 60 * 60 * 1000  一个小时
    // 秘钥明文
    public static final String JWT_KEY = "pinnacle";
    //签发者
    public static final String ISSUER = "cfive";

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return 密钥
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    public static Algorithm algorithm() {
        SecretKey secretKey = generalKey();
        return Algorithm.HMAC256(secretKey.toString());
    }

    private static String getJwt(String subject, Long ttlMillis, String uuid) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return JWT.create()
                .withJWTId(uuid)
                .withSubject(subject)
                .withIssuer(ISSUER)
                .withIssuedAt(now)
                .withExpiresAt(expDate)
                .sign(algorithm());
    }

    /**
     * 生成 jwt
     *
     * @param subject token 中要存放的数据（json 格式）
     * @return jwt 字符串
     */
    public static String createJWT(String subject) {
        return getJwt(subject, null, getUUID());
    }

    /**
     * 生成 jwt
     *
     * @param subject   token 中要存放的数据（json格式）
     * @param ttlMillis token 超时时间
     * @return jwt 字符串
     */
    public static String createJWT(String subject, Long ttlMillis) {
        return getJwt(subject, ttlMillis, getUUID());
    }

    /**
     * 创建 token
     *
     * @param id        唯一的 ID
     * @param subject   token 中要存放的数据（json 格式）
     * @param ttlMillis token 超时时间
     * @return jwt 字符串
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        return getJwt(subject, ttlMillis, id);
    }

    /**
     * 解析 jwt
     *
     * @param jwt 字符串
     * @return 解析内容
     */
    public static DecodedJWT parseJWT(String jwt) {
        JWTVerifier jwtVerifier = JWT.require(algorithm())
                .build();
        return jwtVerifier.verify(jwt);
    }

    public static void main(String[] args) {
//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjYWM2ZDVhZi1mNjVlLTQ0MDAtYjcxMi0zYWEwOGIyOTIwYjQiLCJzdWIiOiJzZyIsImlzcyI6InNnIiwiaWF0IjoxNjM4MTA2NzEyLCJleHAiOjE2MzgxMTAzMTJ9.JVsSbkP94wuczb4QryQbAke3ysBDIL5ou8fWsbt_ebg";
//        Claims claims = parseJWT(token);

        System.out.println(parseJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxd2UiLCJpc3MiOiJjZml2ZSIsImV4cCI6MTY4MzE5MzkyOSwiaWF0IjoxNjgzMTkwMzI5LCJqdGkiOiIzOWY5YTcxYTllY2E0Mjg1OGVjNGExODU2ZmQwYjk4OCJ9.4YOOILGWxlnmToWTdo4YoCbfXqvzdJF_Ds4zulDWX1o")
                .getClaims());
    }
}