package com.ubisys.bootwithsercurity.common.sercurity;

import com.ubisys.bootwithsercurity.common.utils.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.Date;

/**
 * Created by cw on 2018/10/31.
 */
public class JwtTokenUtil {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "jwtsecretdemo";
    private static final String ISS = "scw";

    // 过期时间是3600秒，既是1个小时
    private static final long EXPIRATION = 3600L;

    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 604800L;

    // 创建token
    public static String createToken(String username, int expirations, Map<String, Object> claims) {
        // long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        long expiration = expirations;
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    // 从token中获取用户名
    public static String getUsername(String token) {
        return getTokenBody(token).getSubject();
    }


    /**
     * @author: zzx
     * @date: 2018-10-19 09:10
     * @deprecation: 解析token, 获得subject中的信息
     */
    public static String parseToken(String token, String salt) {
        String subject = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET) // 不使用公钥私钥
                    .parseClaimsJws(token).getBody();
            subject = claims.getSubject();
        } catch (Exception e) {
        }
        return subject;
    }


    // 是否已过期
    public static boolean isExpiration(String expirationTime) {
        // return getTokenBody(token).getExpiration().before(new Date());

        //通过redis中的失效时间进行判断
        String currentTime = DateUtil.getTime();
        if (DateUtil.compareDate(currentTime, expirationTime)) {
            //当前时间比过期时间小，失效
            return true;
        } else {
            return false;
        }


    }


    //获取token自定义属性
    public static Map<String, Object> getClaims(String token) {
        Map<String, Object> claims = null;
        try {
            claims = getTokenBody(token);
        } catch (Exception e) {
        }

        return claims;
    }


    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

}
