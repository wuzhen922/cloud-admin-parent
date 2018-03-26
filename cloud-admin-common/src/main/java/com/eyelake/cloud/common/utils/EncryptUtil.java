package com.eyelake.cloud.common.utils;

import com.yjh.framework.lang.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 加密算法
 *
 * @author
 */
public class EncryptUtil {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    /**
     * 默认字符
     */
    private static String CHARSET = "UTF-8";

    /**
     * MD5盐
     */
    private static final String DEFAULT = "x#4";

    private static final String MD5 = "MD5";

    private static final String SHA1 = "SHA1";

    /**
     * md5加密
     *
     * @param str
     * @return
     */
    public final static String md5Encrypt(String str) {
        return encrypt(MD5, str);
    }

    /**
     * 双重md5加密
     *
     * @param userId
     * @param passord
     * @return
     */
    public final static String doubleMd5(String userId, String passord) {
        return md5Encrypt(userId + DEFAULT + md5Encrypt(passord));
    }

    /**
     * 第二次md5加密
     *
     * @param userId
     * @param md5Passord
     * @return
     */
    public final static String md5For2(String userId, String md5Passord) {
        return md5Encrypt(userId + DEFAULT + md5Passord);
    }

    /**
     * sha1加密
     *
     * @param str
     * @return
     */
    public final static String sha1Encrypt(String str) {
        return encrypt(SHA1, str);
    }

    /**
     * 加密
     *
     * @param algorithm:加密算法
     * @param str
     * @return
     */
    private static String encrypt(String algorithm, String str) {
        if (str == null) {
            return null;
        }

        try {
            MessageDigest md = getMessageDigest(algorithm);
            md.reset();
            return bytes2Hex(md.digest(str.getBytes(CHARSET)));
        } catch (UnsupportedEncodingException e) {
            logger.error("NoSuchAlgorithmException", e);
            throw new AppException("system-error-001", "加密失败");
        }
    }

    /**
     * 获取文件的sha1 Hash值
     *
     * @param filebytes
     * @return
     */
    public static String getFileSHA1Hash(byte[] filebytes) {
        return getFileHash(SHA1, filebytes);
    }

    // private static String getFileHash(String algorithm,File file){
    // return getFileHash(algorithm,new FileInputStream(file));
    // }

    /**
     * 根据文件指定算法的hash值
     *
     * @param algorithm
     * @param filebytes
     * @return
     */
    private static String getFileHash(String algorithm, byte[] filebytes) {
        return getFileHash(algorithm, new ByteArrayInputStream(filebytes));
    }

    /**
     * 根据文件指定算法的hash值
     *
     * @param algorithm
     * @param fis
     * @return
     */
    private static String getFileHash(String algorithm, InputStream fis) {
        if (null == fis) {
            return null;
        }
        try {
            MessageDigest md = getMessageDigest(algorithm);
            byte[] buffer = new byte[2048];
            int length = -1;
            long s = System.currentTimeMillis();
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            byte[] b = md.digest();
            return bytes2Hex(b);
        } catch (IOException e) {
            logger.error("IOException", e);
            throw new AppException("system-error-001", "生成文件hash失败");
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                logger.error("NoSuchAlgorithmException", ex);
            }
        }
    }

    private static MessageDigest getMessageDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException", e);
            throw new AppException("system-error-001", "不支持该加密算法");
        }
    }

    private static String bytes2Hex(byte[] byteArray) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] >= 0 && byteArray[i] < 16) {
                strBuf.append("0");
            }
            strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));
        }
        return strBuf.toString();
    }

    private static void createFile(byte[] pdfbyte) {
        File file = new File("d:/a.pdf");
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(pdfbyte);
        } catch (Exception e) {
            logger.error("FileNotFoundException", e);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    logger.error("IOException", e1);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    logger.error("IOException", e1);
                }
            }
        }
    }

}
