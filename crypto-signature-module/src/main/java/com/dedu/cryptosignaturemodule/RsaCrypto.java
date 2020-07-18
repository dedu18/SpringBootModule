package com.dedu.cryptosignaturemodule;

import org.apache.commons.lang3.tuple.ImmutablePair;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 加密解密
 */
public class RsaCrypto {

    public static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDqt9K3JOy9xbIR1WFiSufbE06BjAJ+1n2HaxTetAJjz8ojHCBV3t2QMX+0ljW2kDj1M/8WK/qAF1ASte28pEvxNvl2u+MHTdlTW3O3GSIIAIz/bRjdKoWE3JW7Qt6XIXFPE1XHK5VVDdpcyQywR4TwQwRV87EMfVcLZtqo39hnKwIDAQAB";

    public static final String PRIVATEKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOq30rck7L3FshHVYWJK59sTToGMAn7WfYdrFN60AmPPyiMcIFXe3ZAxf7SWNbaQOPUz/xYr+oAXUBK17bykS/E2+Xa74wdN2VNbc7cZIggAjP9tGN0qhYTclbtC3pchcU8TVccrlVUN2lzJDLBHhPBDBFXzsQx9Vwtm2qjf2GcrAgMBAAECgYEAsHnz4aXOpkTNRSFVbiz5tLsIbNjTS4CDs1ysvWFE5rzls45DNa0yk2bUKPhDfHdli99DbO02FDbzCo5lKE+zlEHaC/WTp6guEe7jj5dwMl3shBZmgITCTk1/MQ46gGRG4RRADbQT/Y7tENp/GF3y9oJyJ+LmHFvfdEjSuY1/QzECQQD6aKqYFO8wuhLhy1fTvjMwlzok0szT9wTp+l6E7Ct9+csvdwaYjJrGsr6kUv+6YUwieSJ41lVtGnRy1oXEQG2TAkEA7/V35kYG+FMwYq/DOrBNaomRQGJVAOLzGRoK2dkjAkpoUAfzk4TTQ0KdJJ3T6mzF/6IQY+1oFDD42kNKJklfCQJARiya0i/bsC4VKI3RuRcuRUm8E6G3oRcym1d8sYd10MH1/QFAKfQNU+23m1lfLR4jNe34iSCXpBGr3JrdtdfQXQJAXgWRkGHZ800tRU3XMlTIULlMd6zP38QNOsWwgMGK7SfYjZs//opp+Q3N4v4QfedXAZ4vy+fHAzpZF7SMBkpzeQJALlMaKKeqKvPr8abXSRjW8u6s8tHaHX6CRV/1fGDX1bkUByqdFMO5CqIHn7isK2dHXI42bJVz63/d2Aax3lTbkA==";

    public static final String CHARSET = "UTF-8";

    public static final int KEYSIZE = 1024;

    public static void main(String[] args) throws Exception {
        ImmutablePair<String, String> keyPair = RsaSignature.generateKeyPair(KEYSIZE);
        System.out.println("公钥：" + keyPair.getLeft());
        System.out.println("私钥：" + keyPair.getRight());
        String data = "test";
        String encryptData = rsaEncrypt(data.getBytes(CHARSET), getPublicKey(keyPair.getLeft()));
        System.out.println("公钥加密后：" + encryptData);
        String decryptData = rsaDecrypt(encryptData.getBytes(), getPrivateKey(keyPair.getRight()));
        System.out.println("私钥解密后：" + decryptData);
    }

    /**
     * 解码PublicKey
     * @param key
     * @return
     */
    public static Key getPublicKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(x509EncodedKeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解码PrivateKey
     * @param key
     * @return
     */
    public static Key getPrivateKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec x509EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(x509EncodedKeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Java中可以使用Cipher加密解密器来实现加解密操作，加密数据Data must not be longer than 117 bytes
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String rsaEncrypt(byte[] data, Key publicKey) throws Exception {
        //获取一个加密算法为RSA的加解密器对象cipher。
        Cipher cipher = Cipher.getInstance("RSA");
        //设置为加密模式,并将公钥给cipher。
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        //获得密文
        byte[] secret = cipher.doFinal(data);
        //进行Base64编码并返回
        return new BASE64Encoder().encode(secret);
    }

    /**
     * 解密数据Data must not be longer than 128 bytes
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String rsaDecrypt(byte[] data, Key privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        //传递私钥，设置为解密模式。
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //解密器解密由Base64解码后的密文,获得明文字节数组
        byte[] encryptBytes = cipher.doFinal(data);
        //转换成字符串
        return new String(encryptBytes);
    }
}
