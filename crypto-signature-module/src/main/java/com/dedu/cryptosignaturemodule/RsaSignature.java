package com.dedu.cryptosignaturemodule;


import org.apache.commons.lang3.tuple.ImmutablePair;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

/**
 * RSA加签验签
 */
public class RsaSignature {

    public static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDqt9K3JOy9xbIR1WFiSufbE06BjAJ+1n2HaxTetAJjz8ojHCBV3t2QMX+0ljW2kDj1M/8WK/qAF1ASte28pEvxNvl2u+MHTdlTW3O3GSIIAIz/bRjdKoWE3JW7Qt6XIXFPE1XHK5VVDdpcyQywR4TwQwRV87EMfVcLZtqo39hnKwIDAQAB";

    public static final String PRIVATEKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOq30rck7L3FshHVYWJK59sTToGMAn7WfYdrFN60AmPPyiMcIFXe3ZAxf7SWNbaQOPUz/xYr+oAXUBK17bykS/E2+Xa74wdN2VNbc7cZIggAjP9tGN0qhYTclbtC3pchcU8TVccrlVUN2lzJDLBHhPBDBFXzsQx9Vwtm2qjf2GcrAgMBAAECgYEAsHnz4aXOpkTNRSFVbiz5tLsIbNjTS4CDs1ysvWFE5rzls45DNa0yk2bUKPhDfHdli99DbO02FDbzCo5lKE+zlEHaC/WTp6guEe7jj5dwMl3shBZmgITCTk1/MQ46gGRG4RRADbQT/Y7tENp/GF3y9oJyJ+LmHFvfdEjSuY1/QzECQQD6aKqYFO8wuhLhy1fTvjMwlzok0szT9wTp+l6E7Ct9+csvdwaYjJrGsr6kUv+6YUwieSJ41lVtGnRy1oXEQG2TAkEA7/V35kYG+FMwYq/DOrBNaomRQGJVAOLzGRoK2dkjAkpoUAfzk4TTQ0KdJJ3T6mzF/6IQY+1oFDD42kNKJklfCQJARiya0i/bsC4VKI3RuRcuRUm8E6G3oRcym1d8sYd10MH1/QFAKfQNU+23m1lfLR4jNe34iSCXpBGr3JrdtdfQXQJAXgWRkGHZ800tRU3XMlTIULlMd6zP38QNOsWwgMGK7SfYjZs//opp+Q3N4v4QfedXAZ4vy+fHAzpZF7SMBkpzeQJALlMaKKeqKvPr8abXSRjW8u6s8tHaHX6CRV/1fGDX1bkUByqdFMO5CqIHn7isK2dHXI42bJVz63/d2Aax3lTbkA==";

    public static final String CHARSET = "UTF-8";

    public static final String ALGORITHM = "SHA1WithRSA";

    public static final int KEYSIZE = 1024;

    public static void main(String[] args) throws Exception {
        //生成RSA密钥对
        ImmutablePair<String, String> keyPair = generateKeyPair(KEYSIZE);
        //使用现有RSA密钥对
//        ImmutablePair<String, String> keyPair = new ImmutablePair<>(PUBLICKEY, PRIVATEKEY);
        System.out.println("公钥：" + keyPair.getLeft());
        System.out.println("私钥：" + keyPair.getRight());
        // 签名原文
        String originalData = "key1=value1&key2=value2";
        System.out.println("加签前字符串：" + originalData);
        // 进行加签
        String sign = sign(originalData.getBytes(CHARSET), Base64.getDecoder().decode(keyPair.getRight()), ALGORITHM);
        System.out.println("加签字符串：" + sign);
        //进行验签，这里模拟数据被修改后验签结果
        String modifiedData = "key1=value1&key2=value2";
        boolean verify = verify(modifiedData.getBytes(CHARSET), Base64.getDecoder().decode(keyPair.getLeft()), sign, ALGORITHM);
        System.out.println("解签结果：" + verify);
    }

    /**
     * @param keysize 长度
     * @return <left,right> left为Base64编码后的公钥，right为编码后的私钥
     * @throws Exception
     */
    public static ImmutablePair generateKeyPair(int keysize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keysize, new SecureRandom(UUID.randomUUID().toString().getBytes()));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        String publicKey = new String(Base64.getEncoder().encode(keyPair.getPublic().getEncoded()));
        String privateKey = new String(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()));
        ImmutablePair<String, String> keys = new ImmutablePair(publicKey, privateKey);
        return keys;
    }

    /**
     * @param keysize 长度
     * @return <left,right> left为Base64编码后的公钥，right为编码后的私钥
     * @throws Exception
     */
    public static KeyPair generateKeyPairReturn(int keysize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keysize, new SecureRandom(UUID.randomUUID().toString().getBytes()));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    /**
     * 加签
     *
     * @param data       需要验签的原始数据字符数组
     * @param privateKey 进行Base64解码后的私钥
     * @param algorithm  算法 RSA为SHA1WithRSA、RSA2为SHA256WithRSA
     * @return 加签字符串
     * @throws Exception
     */
    public static String sign(byte[] data, byte[] privateKey, String algorithm) throws Exception {
        PKCS8EncodedKeySpec pksc = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyfactory = KeyFactory.getInstance("RSA");
        PrivateKey pk = keyfactory.generatePrivate(pksc);
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(pk);
        signature.update(data);
        return new String(Base64.getEncoder().encode(signature.sign()));
    }

    /**
     * 验签
     *
     * @param data      需要验签的原始数据字符数组
     * @param publicKey 进行Base64解码后的公钥
     * @param sign      需对比的签名
     * @param algorithm 算法 RSA为SHA1WithRSA、RSA2为SHA256WithRSA
     * @return 验签结果
     * @throws Exception
     */
    public static boolean verify(byte[] data, byte[] publicKey, String sign, String algorithm) throws Exception {
        X509EncodedKeySpec xksc = new X509EncodedKeySpec(publicKey);
        KeyFactory keyfactory = KeyFactory.getInstance("RSA");
        PublicKey pk = keyfactory.generatePublic(xksc);
        Signature signature = Signature.getInstance(algorithm);
        signature.initVerify(pk);
        signature.update(data);
        return signature.verify(Base64.getDecoder().decode(sign));
    }
}
