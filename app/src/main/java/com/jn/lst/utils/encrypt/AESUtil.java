package com.jn.lst.utils.encrypt;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description: AES加密工具类
 * @Author: yangxiaohu
 * @CreateDate: 2022/7/18 6:53 下午
 * @UpdateUser: 更新者：
 * @UpdateDate: 2022/7/18 6:53 下午
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class AESUtil {
    private static final String TAG = "AESUtil";
    public static final Charset DEFAULT_CHARSET;
    private static final char[] DIGITS_LOWER;
    private static final char[] DIGITS_UPPER;

    static {
        DEFAULT_CHARSET = StandardCharsets.UTF_8;
        DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    // AES/CBC/NOPaddin
    // AES 默认模式
    // 使用CBC模式, 在初始化Cipher对象时, 需要增加参数, 初始化向量IV : IvParameterSpec iv = new
    // IvParameterSpec(key.getBytes());
    // NOPadding: 使用NOPadding模式时, 原文长度必须是8byte的整数倍   ECB模式是可重复解密的
    private static final String transformation = "AES/ECB/NOPadding";

    /*
     * AES加密
     *
     * @param str 将要加密的内容
     * @param key 密钥
     * @return 已经加密的字节数组内容 再 base64 之后的字符串
     */

    public static String encryptAes(String str, String key) throws Exception {
        byte[] hexData = decodeHex(str);
        byte[] hexKeyByte = decodeHex(key);
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(hexKeyByte, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] resultByte = cipher.doFinal(hexData);
            return encodeHexString(resultByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeHexString(byte[] data) {
        return new String(encodeHex(data));
    }

    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        encodeHex(data, 0, data.length, toDigits, out, 0);
        return out;
    }

    private static void encodeHex(byte[] data, int dataOffset, int dataLen, char[] toDigits, char[] out, int outOffset) {
        int i = dataOffset;

        for(int var7 = outOffset; i < dataOffset + dataLen; ++i) {
            out[var7++] = toDigits[(240 & data[i]) >>> 4];
            out[var7++] = toDigits[15 & data[i]];
        }

    }

    public static byte[] decodeHex(char[] data) throws Exception {
        byte[] out = new byte[data.length >> 1];
        decodeHex(data, out, 0);
        return out;
    }

    public static int decodeHex(char[] data, byte[] out, int outOffset) throws Exception {
        int len = data.length;
        if ((len & 1) != 0) {
//            throw new DecoderException("Odd number of characters.");
            return 0;
        } else {
            int outLen = len >> 1;
            if (out.length - outOffset < outLen) {
//                throw new DecoderException("Output array is not large enough to accommodate decoded data.");
                return 0;
            } else {
                int i = outOffset;

                for(int j = 0; j < len; ++i) {
                    int f = toDigit(data[j], j) << 4;
                    ++j;
                    f |= toDigit(data[j], j);
                    ++j;
                    out[i] = (byte)(f & 255);
                }

                return outLen;
            }
        }
    }

    public static byte[] decodeHex(String data) throws Exception {
        return decodeHex(data.toCharArray());
    }

    protected static int toDigit(char ch, int index) throws Exception {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
//            throw new DecoderException("Illegal hexadecimal character " + ch + " at index " + index);
            return 0;
        } else {
            return digit;
        }
    }
}
