package org.hongtao.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.hongtao.enums.LoginTypeEnum;
import org.hongtao.constant.SecurityConstants;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.UUID;

/**
 * @author yoga
 * @Description: JwtCreateUtil
 * @date 2019/10/1111:21
 */
public class JwtCreateUtil {

    private static byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);
    private static SecretKey secretKey = Keys.hmacShaKeyFor(apiKeySecretBytes);

    public static String createToken(String username, Integer userId, Long mobile , LoginTypeEnum type ) {

        String tokenPrefix = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .claim(SecurityConstants.USER_ID_KEY, userId)
                .claim(SecurityConstants.MOBILE_KEY, mobile)
                .claim(SecurityConstants.TYPE_KEY, type.getType())
                .setIssuer("ewallet-oauth")
                .setIssuedAt(new Date())
                .setSubject(username)
                .setExpiration(new Date(SecurityConstants.TOKEN_EXPIRATION * 1000 + System.currentTimeMillis()) )
                .compact();
        return SecurityConstants.TOKEN_PREFIX + tokenPrefix;
    }


}

