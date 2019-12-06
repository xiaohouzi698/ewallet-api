package org.hongtao.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * @author yoga
 * @Description: EncryptUtil - 目前只用rsa加密
 * @date 2019/10/1114:07
 */

public class EncryptUtil {
    private static String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlPxhZLHQN9IchGtRS6cH30O9nwi8bD8Z0/xK2oexQxh1KuufbwxrmS3w/H1BfGHAC58CzOA4Bq4G67sRUgKqCEeNGsgB/0qgdNi6GBHPHTPoYEQieXiuJZoDekOOExz22U06eDhut/pBhaa/NQOQwV76NQeUQNqM+EFr5RSQd7siG94oyM+7KcW6Ru2rb7Z9is3gQvejw+MoCilIXoifd96dB/KjWm+nqfw2boxvHbWcqrq+vSMw9PcTe2XbCv5SrOYvAQy6DZIYfi2dR1bhPh9YTUihjXaqquzwjtcBHrhMn9cpCpOOgXIpK74Gg7F0UpjNEMwZkXxbhJ9dc1ZmjQIDAQAB";

    private static String privateKeyStr =  "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCU/GFksdA30hyEa1FLpwffQ72fCLxsPxnT/Erah7FDGHUq659vDGuZLfD8fUF8YcALnwLM4DgGrgbruxFSAqoIR40ayAH/SqB02LoYEc8dM+hgRCJ5eK4lmgN6Q44THPbZTTp4OG63+kGFpr81A5DBXvo1B5RA2oz4QWvlFJB3uyIb3ijIz7spxbpG7atvtn2KzeBC96PD4ygKKUheiJ933p0H8qNab6ep/DZujG8dtZyqur69IzD09xN7ZdsK/lKs5i8BDLoNkhh+LZ1HVuE+H1hNSKGNdqqq7PCO1wEeuEyf1ykKk46BcikrvgaDsXRSmM0QzBmRfFuEn11zVmaNAgMBAAECggEBAIQXyFo49T2owrP9WpTSwT4Il4T7f+QRhlrXY56hlF7Cvo+RXUheIMAtZW7Xt0oKePVUxGzVl5cEkQpMT60Aw7SXi9nk3zB0ABPZXBWfJJzluvAvOMmuX+jixpaOctcrZQmBWYfhsPjZn2JaV0kvohZwmTKPR7jAjSBHENyJARXFqmeJkTMTrdVi5kB3HFM84i46vXvdL3Oj4sQTzVNA6kWPCUIhVuSrx9bG0f5WLSurGCr1JGVxzq9Cd+9zf9gzosPUPjVT+D20ts4AsYWCAShdwCWTubKa0Ks28AQ3sQmuDt0PZHrmw5Ikc0AERvA+wsoyjxRyDPgS/99HeO7htBkCgYEA7IfbmopSD4DfM65+wT4kBCpcYaTMoZtTd8WTr6HnG8lNuhm+CJD7WaFrT3BFvQFV29e57ojdQ8aKGrMZ11xUQEee763CbIoHoBbGy1HOqFa0+dt2rIlRuAF9G827RiCWqAgj3GIlCAPn/SRpyOKEc/7kZGNxYQ2y5u6gGA8dUhsCgYEAoT/KOrndi1WleOD2oDO8Bdgv9MAvqfXu9N2O2YOjHsMMAXUHAC48sXx4KAISulCkgEnoil7gMoXkZbpZ0Pb3ACrBvpLoipHtBATJJkr86yrdB83kUsubi3qxk7GuQoZeprhUA5wFCoABwVrR5+jAtmB1MtEkQnL+DSfP/NqTdHcCgYEAtfUNiyU9W4/YJPnnqOEU899ikfslMG09GLZ1lncGQunpUXDcL9rYgWscElBcVbnhQu4Bqu6+yJTDSSbeij4M0iZGrjsNhlHo686Zv29/eyC4uD2MXc6FfvgmNm1d0+TeAQXCEJevxSq8HJEWj+Mx4coECPY3EmJnbh/EKL334GECgYBfXcLVCKgmh/jdjO9PwEkri7iRIDkQZAt3drHNozUONH0dP4FscewjbGIEB3XcC2W/GfcWHxapytZR8+y60dUznBYKCAjQK43qGop8w0ziKO+gdfzbmt/bli0AFM3jOmUwZUQK/nNcymg/Wt9aMSYItvOZome3hRsL3Mf926fp8QKBgQCPRwD4YFc4Zf35SHR3nXBr9iyv56Q6aSG80WzG//XWWO8Hl5UY3TLZKdlem62+2U6yumbwob5zKkVl/XCxA3RvMjJqi73HfAPkkjZsCTPqmknAv9SH4VZo+5dhAusu3O6cNkW0VHTElstQrx4WbHVcBpH2FD2NCA6YCzC9mBcCOA==";

    public static String rasEncrypt2Base64(String content) throws InvalidKeySpecException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {

        PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);

        // 加密
        byte[] encrypteds = RSAUtil.publicEncrytype(content.getBytes(), publicKey);
        String encryptedBase64Str = Base64.getEncoder().encodeToString(encrypteds);
        return encryptedBase64Str;
    }

    public static String decrypt(String encryptedBase64Content) throws UnsupportedEncodingException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        PrivateKey privateKey = RSAUtil.string2Privatekey(privateKeyStr);

        byte[] encrypteds = Base64.getDecoder().decode(encryptedBase64Content);
        byte[] dencrypteds = RSAUtil.privateDecrypt(encrypteds, privateKey);
        String dencrypted = new String(dencrypteds, "utf8");
        return dencrypted;
    }
}

