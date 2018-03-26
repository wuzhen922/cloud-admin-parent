package com.eyelake.cloud.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@SuppressWarnings("restriction")
public class AESEncryptUtils {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AESEncryptUtils.class);

    /**
     * 字符集设置
     **/
    private static final String CHARACTER_UTF8 = "utf-8";

    /**
     * Aes128加密
     *
     * @param content    需要加密的内容
     * @param encryptKey 加密密码
     * @return
     */
    public static String encrypt(String content, String encryptKey) {
        if (!StringUtils.isNotEmpty(content)) {
            LOGGER.warn("The encrypt content is empty.");
            return null;
        }

        if (!StringUtils.isNotEmpty(encryptKey)) {
            LOGGER.warn("The encrypt key is empty.");
            return null;
        }

        byte[] encryptByte = aes128Encrypt(content, encryptKey);

        return parseByte2HexStr(encryptByte);
    }

    /**
     * Aes128解密
     *
     * @param content    待解密内容
     * @param decryptKey 解密密钥
     * @return
     */
    public static String decrypt(String content, String decryptKey) {
        if (!StringUtils.isNotEmpty(content)) {
            LOGGER.warn("The decrypt content is empty.");
            return null;
        }

        if (!StringUtils.isNotEmpty(decryptKey)) {
            LOGGER.warn("The decrypt key is empty.");
            return null;
        }

        byte[] decryptByte = aes128Decrypt(parseHexStr2Byte(content), decryptKey);

        return new String(decryptByte);
    }

    /**
     * AES128加密
     *
     * @param content    需要加密的内容
     * @param encryptKey 加密密码
     * @return
     */
    public static byte[] aes128Encrypt(String content, String encryptKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(encryptKey.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes(CHARACTER_UTF8);
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("NoSuchAlgorithmException:", e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error("NoSuchPaddingException:", e);
        } catch (InvalidKeyException e) {
            LOGGER.error("InvalidKeyException:", e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("UnsupportedEncodingException:", e);
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("IllegalBlockSizeException:", e);
        } catch (BadPaddingException e) {
            LOGGER.error("BadPaddingException:", e);
        } catch (Exception e) {
            LOGGER.error("Exception:", e);
        }
        return null;
    }

    /**
     * Aes128解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] aes128Decrypt(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 解密
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("NoSuchAlgorithmException:", e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error("NoSuchPaddingException:", e);
        } catch (InvalidKeyException e) {
            LOGGER.error("InvalidKeyException:", e);
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("IllegalBlockSizeException:", e);
        } catch (BadPaddingException e) {
            LOGGER.error("BadPaddingException:", e);
        } catch (Exception e) {
            LOGGER.error("Exception:", e);
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        if (null == buf || 0 == buf.length) {
            LOGGER.warn("The parse byte content is empty.");
            return null;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (!StringUtils.isNotEmpty(hexStr)) {
            LOGGER.warn("The parse hex string content is empty.");
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 获取16位随机秘钥
     *
     * @param length 获取秘钥长度
     * @return
     */
    public static String getRandomKey(int length) {
        if (1 > length) {
            LOGGER.warn("Get random key length must be greater than 0.");
            return null;
        }

        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    /**
     * 提供AES/CBC/NoPadding方式加密，并返回Base64加密串,对应前台js的加解密操作
     *
     * @param sSrc 要加密的字符串
     * @param sKey 加密秘钥
     * @return
     * @throws Exception
     */
    public static String encryptCBC(String sSrc, String sKey) throws Exception {

        if (!StringUtils.isNotEmpty(sSrc)) {
            LOGGER.warn("The encrypt content is empty.");
            return null;
        }

        if (0 != (sSrc.length() % 16)) {
            LOGGER.warn("The encrypt content length must be multiple of 16.");
            return null;
        }

        if (!StringUtils.isNotEmpty(sKey)) {
            LOGGER.warn("The encrypt key is empty.");
            return null;
        }

        // 判断Key是否为16位
        if (16 != sKey.length()) {
            LOGGER.warn("The encrypt key length must be 16.");
            return null;
        }

        try {
            byte[] raw = sKey.getBytes();

            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

            // 算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");

            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(sKey.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(sSrc.getBytes(CHARACTER_UTF8));

            // 此处使用BAES64做转码功能，同时能起到2次加密的作用。
            return (new BASE64Encoder()).encodeBuffer(encrypted);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("NoSuchAlgorithmException:", e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error("NoSuchPaddingException:", e);
        } catch (InvalidKeyException e) {
            LOGGER.error("InvalidKeyException:", e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("UnsupportedEncodingException:", e);
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("IllegalBlockSizeException:", e);
        } catch (BadPaddingException e) {
            LOGGER.error("BadPaddingException:", e);
        } catch (Exception e) {
            LOGGER.error("Exception:", e);
        }
        return null;
    }

    /**
     * 提供AES/CBC/NoPadding方式解密,对应前台js的加解密操作
     *
     * @param sSrc 待解密内容
     * @param sKey 解密密钥
     * @return
     */
    public static String decryptCBC(String sSrc, String sKey) {

        if (!StringUtils.isNotEmpty(sSrc)) {
            LOGGER.warn("The decrypt content is empty.");
            return null;
        }

        if (!StringUtils.isNotEmpty(sKey)) {
            LOGGER.warn("The decrypt key is empty.");
            return null;
        }

        // 判断Key是否为16位
        if (16 != sKey.length()) {
            LOGGER.warn("The decrypt key length must be 16.");
            return null;
        }

        try {
            byte[] raw = sKey.getBytes();

            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

            // 算法/模式/补码方式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");

            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(sKey.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] srcarray = (new BASE64Decoder()).decodeBuffer(sSrc);

            byte[] decrypted = cipher.doFinal(srcarray);

            return new String(decrypted, CHARACTER_UTF8);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("NoSuchAlgorithmException:", e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error("NoSuchPaddingException:", e);
        } catch (InvalidKeyException e) {
            LOGGER.error("InvalidKeyException:", e);
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("IllegalBlockSizeException:", e);
        } catch (BadPaddingException e) {
            LOGGER.error("BadPaddingException:", e);
        } catch (Exception e) {
            LOGGER.error("Exception:", e);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(decryptCBC("y54Zp3FpYuIxrhJ+tYjFd0WzKgsTmyibXIajuBWQdgg=", "1234123412341234"));
    }

}
