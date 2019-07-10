package com.yuchen.catalog.common.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.util.Base64;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。
 * 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。
 *
 */
public class RSAUtil {

    /**
     * 解密
     *
     * @param content 待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(content);
            return result; // 加密  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static byte[] encrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密  
        } catch (Exception e) {
            e.printStackTrace();
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
        if (hexStr.length() < 1) {
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
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final KeyPair keyPair = initKey();
    public static final PrivateKey pr = keyPair.getPrivate();
    public static final PublicKey pu = keyPair.getPublic();

    private static KeyPair initKey() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024, new SecureRandom());
            System.out.print(kpg.getAlgorithm());
            KeyPair kp = kpg.generateKeyPair();
            return kp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateBase64PrivateKey() {
        return new String(Base64.getEncoder().encode(pr.getEncoded()));
    }

    public static String generateBase64PublicKey() {
        return new String(Base64.getEncoder().encode(pu.getEncoded()));
    }

    public static String decryptBase64(String string) throws Exception {
        return new String(decrypt(Base64.getDecoder().decode(string)));
    }

    public static String encryptBase64(String string) throws Exception {
        return new String(Base64.getEncoder().encode(encrypt(string.getBytes())));
    }

    // 私钥加密
    public static byte[] encrypt(byte[] data) throws Exception {
        Cipher cdes = Cipher.getInstance(pu.getAlgorithm());
        keyPair.getPrivate();
        cdes.init(Cipher.ENCRYPT_MODE, pu);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        System.out.println(inputLen);
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cdes.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                System.out.println(offSet + "," + (inputLen - offSet));
                cache = cdes.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;

    }

    // 公钥解密
    public static byte[] decrypt(byte[] data) throws Exception {
        Cipher cdes = Cipher.getInstance(pr.getAlgorithm());
        cdes.init(Cipher.DECRYPT_MODE, pr);
        int inputLen = data.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cdes.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cdes.doFinal(data, offSet, inputLen - offSet);
            }
            bout.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = bout.toByteArray();
        bout.close();
        return decryptedData;
    }

    public static void main(String[] args) {
        try {
            String content = "test";
            String password = "12345678";
            //加密  
            System.out.println("加密前：" + content);
            byte[] encryptResult = encrypt(content, password);
            String encryptResultStr = parseByte2HexStr(encryptResult);
            System.out.println("加密后：" + encryptResultStr);
            //解密  
            byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
            byte[] decryptResult = decrypt(decryptFrom, password);
            System.out.println("解密后：" + new String(decryptResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
