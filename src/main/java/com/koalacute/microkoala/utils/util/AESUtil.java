package com.koalacute.microkoala.utils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * AES加解密工具类
 */
public class AESUtil {

    /**
     * AES算法
     */
    private static final String AES_ALGORITHM = "AES";

    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);


    /**
     * Aes加密
     *
     * @param content 待加密内容
     * @param key     加密的密钥
     */
    public static String encrypt(String content, String key) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");

            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());

            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] byteRresult = cipher.doFinal(byteContent);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteRresult.length; i++) {
                String hex = Integer.toHexString(byteRresult[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.info("未知算法");
        } catch (NoSuchPaddingException e) {
            logger.info("未知填充机制");
        } catch (InvalidKeyException e) {
            logger.info("当密钥大于128");
        } catch (UnsupportedEncodingException e) {
            logger.info("加密内容不支持编码异常");
        } catch (IllegalBlockSizeException e) {
            logger.info("编码方式的问题可能会造成数据的丢失");
        } catch (BadPaddingException e) {
            logger.info("密钥错误");
        }
        return null;
    }

    /**
     * Aes解密
     *
     * @param content 待解密内容
     * @param key     加密的密钥
     */
    public static String decrypt(String content, String key) {
        if (content.length() < 1)
            return null;
        byte[] byteRresult = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer
                    .parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2),
                    16);
            byteRresult[i] = (byte) (high * 16 + low);
        }
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");

            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());

            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] result = cipher.doFinal(byteRresult);
            return new String(result);
        } catch (NoSuchAlgorithmException e) {
            logger.info("未知算法");
        } catch (NoSuchPaddingException e) {
            logger.info("未知填充机制");
        } catch (InvalidKeyException e) {
            logger.info("当密钥大于128");
        } catch (IllegalBlockSizeException e) {
            logger.info("编码方式的问题可能会造成数据的丢失");
        } catch (BadPaddingException e) {
            logger.info("密钥错误");
        }
        return null;
    }


    /**
     * 利用AES算法对数据进行加密
     *
     * @param data         要加密的数据
     * @param aesBase64Key base64编码的AES Key
     * @param charset      字符集
     * @return 加密后的数据(Base64编码)
     */
    public static String encrypt(byte[] data, String aesBase64Key, String algorithm) throws Exception {
        // AES加密
        Cipher cipher = initCipher(aesBase64Key, algorithm, Cipher.ENCRYPT_MODE);

        byte[] bytOut = cipher.doFinal(data);
        return Base64Utils.encode(bytOut);
    }


    /**
     * 利用AES算法对数据进行解密
     *
     * @param encryptedData AES加密的数据
     * @param aesBase64Key  base64编码的AES Key
     * @param aesKey        加密模式
     * @return 解密后的数据
     * @throws Exception 解密异常
     */
    public static byte[] decrypt(byte[] encryptedData, String aesBase64Key, String algorithm)
            throws Exception {
        // AES加密
        Cipher cipher = initCipher(aesBase64Key, algorithm, Cipher.DECRYPT_MODE);

        byte[] bytOut = cipher.doFinal(encryptedData);

        return bytOut;
    }


    /**
     * @param aesBase64Key
     * @param mode         指定是加密还是解密模式
     * @return
     * @throws Exception
     */
    public static Cipher initCipher(String aesBase64Key, String algorithm, int mode) throws Exception {
        SecretKeySpec skeySpec = getSecretKeySpec(aesBase64Key);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(mode, skeySpec);
        return cipher;
    }

    /**
     * 根据base64编码的key获取SecretKeySpec对象
     *
     * @param aesBase64Key base64编码的key
     * @return SecretKeySpec对象
     * @throws Exception 获取SecretKeySpec对象异常
     */
    public static SecretKeySpec getSecretKeySpec(String aesBase64Key) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(Base64Utils.decode(aesBase64Key),
                AES_ALGORITHM);
        return skeySpec;
    }

    /**
     * 初始化AES Key
     *
     * @return base64编码的AES Key
     * @throws Exception 异常
     */
    public static String initKey(int len, String seek) throws Exception {
        //实例化
        KeyGenerator kgen = KeyGenerator.getInstance(AES_ALGORITHM);
        //设置密钥长度
        SecureRandom random = null;
        if (seek != null) {
            random = new SecureRandom(seek.getBytes());
        } else {
            random = new SecureRandom();
        }

        kgen.init(len, random);
        //kgen.init(128);
        //生成密钥
        SecretKey skey = kgen.generateKey();

        //返回密钥的二进制编码
        return Base64Utils.encode(skey.getEncoded());
    }
}
