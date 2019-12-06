package org.hongtao.constant;


/**
 * @author yoga
 * @Description: SecurityConstants
 * @date 2019/10/1019:52
 */
public class SecurityConstants {


    /**
     * 过期时间
     */
    public static final long TOKEN_EXPIRATION = 60 * 60 * 24 * 7L;
//    public static final long TOKEN_EXPIRATION = 60 * 2L;

    /**
     * JWT签名密钥
     */
    public static final String JWT_SECRET_KEY = "C*F-JaNdRgUkXn2r5u8x/A?D(G+KbPeShVmYq3s6v9y$B&E)H@McQfTjWnZr4u7w";

    // JWT token defaults

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";

    public static final String USER_ID_KEY = "userId";
    public static final String MOBILE_KEY = "mobile";
    public static final String TYPE_KEY = "loginType";


    private SecurityConstants() { }

}
