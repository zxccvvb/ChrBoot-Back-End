package com.chr.common.utils.jwt;

import io.jsonwebtoken.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public final class JwtHelper {

    /**
     * 生成 JWT（HS256，固定密钥）
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                .setExpiration(exp)
                .compact();
    }

    /**
     * 解析 JWT
     */
    public static Claims parseJWT(String secretKey, String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 判断是否过期（过期返回 true）
     */
    public static boolean isExpired(String secretKey, String token) {
        try {
            Date exp = parseJWT(secretKey, token).getExpiration();
            return exp.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    private JwtHelper() {}
}