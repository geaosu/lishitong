package com.jn.lst.utils.encrypt;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * @Description: RSA加密工具类
 * @Author: yangxiaohu
 * @CreateDate: 2022/7/19 10:12 上午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2022/7/19 10:12 上午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class RSAUtils {
    private static final String TAG = "RSAUtil";
    private static final String Algorithm = "RSA/ECB/PKCS1Padding";

    /**
     * @params str 要加密的字符串
     */
    public static String rsaEncode(String publicKey, String str) {
        String outStr = "";
        try {
            // base64编码的公钥
            byte[] decoded = Base64.decode(publicKey, Base64.DEFAULT);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            // RSA加密
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            outStr = Base64.encodeToString(cipher.doFinal(str.getBytes()), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outStr;
    }
}
