package com.dedu.cryptosignaturemodule;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * RSA证书工具类
 */
public class RSACertFileUtil {

    public static void main(String[] args) {
//        //使用jdk的keytool生成秘钥库
        //keytool -genkey -alias rsakeyalia -keyalg RSA -keystore f:/rsakeystore.keystore -keysize 1024 -validity 3650
        //相比于 Java6，在 Java7 中 keytool 工具改动-genkey 选项改名为 -genkeypair
        //生成一对非对称密钥以及一个自签发证书，其中私钥和证书以别名 TEST_ROOT 存储在 test_root.jks 文件中
        //keytool -genkeypair -alias TEST_ROOT -keystore test_root.jks

        //生成.pfx证书文件
        String keyStoreFilePath = "f:/rsakeystore.keystore";
        String keyStorePasswd = "12345678";
        String keyAlias = "rsakeyalia";
        String pfxFilePath = "f:/rsakey.pfx";
        String cerFilePath = "f:/rsakey.cer";
        System.out.println("1.转换keystore文件为PFX文件：");
        coverKeystoreToPfx(keyStoreFilePath, keyStorePasswd, keyAlias, pfxFilePath);

        //keytool -export -alias rsakeyalia -keystore f:/rsakeystore.keystore -file  f:/rsakey.cer
        System.out.println("2.提取.cer中的公钥字符串：");
        String publickeyOfCer = getPubkeyFromCerFile(cerFilePath);
        System.out.println("3.提取.cer文件中的公钥字符串：");
        System.out.println(publickeyOfCer);

//        从pfx证书文件中提取私钥
        String keyStorefile = "F:\\rsakey.pfx";
        String keyPassword = "12345678";
        getPrivateKeyFromPfxFile(keyStorefile,keyPassword);
//
//        从pfx证书文件中提取私钥
//        String keyStorefile = "F:\\acp_test_sign.pfx";
//        String keyPassword = "000000";
//        getPrivateKeyFromPfxFile(keyStorefile,keyPassword);

//        //使用openssl工具从pfx证书中提取公私钥
        //openssl pkcs12 -in rsakey.pfx -nodes -out rsakey.pem
        //获取私钥
        //openssl rsa -in rsakey.pem -out rsakey.key
        //获取公钥
        //openssl x509 -in rsakey.pem -out rsakey.crt

        //将私钥转换成Java识别的pkcs8格式，输出显示
        //openssl pkcs8 -topk8 -inform PEM -in rsakey.pem  -outform PEM -nocrypt
        //生成对应的公钥
        //openssl rsa -in rsakey.pem -pubout -out rsa_public_key.pem
    }

    /**
     * 转换keystore文件为.pfx文件
     *
     * @param keyStoreFile keystore证书密码文件路径
     * @param passwd       keystore证书密码
     * @param keyAlias     keystore证书别名
     * @param pfxFile      pfx文件证书路径
     */
    public static void coverKeystoreToPfx(String keyStoreFile, String passwd, String keyAlias, String pfxFile) {
        try {
            KeyStore inputKeyStore = KeyStore.getInstance("JKS");
            FileInputStream fis = new FileInputStream(keyStoreFile);
            char[] nPassword = null;
            if ((passwd == null)
                    || passwd.trim().equals("")) {
                nPassword = null;
            } else {
                nPassword = passwd.toCharArray();
            }
            inputKeyStore.load(fis, nPassword);
            fis.close();
            KeyStore outputKeyStore = KeyStore.getInstance("PKCS12");
            outputKeyStore.load(null, passwd.toCharArray());
            if (inputKeyStore.isKeyEntry(keyAlias)) {
                Key key = inputKeyStore.getKey(keyAlias, passwd.toCharArray());
                Certificate[] certChain = inputKeyStore
                        .getCertificateChain(keyAlias);
                outputKeyStore.setKeyEntry(keyAlias, key, passwd
                        .toCharArray(), certChain);
            }
            FileOutputStream out = new FileOutputStream(pfxFile);
            outputKeyStore.store(out, nPassword);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从.cet证书中获取公钥字符串
     * @param cerFile
     * @return
     */
    public static String getPubkeyFromCerFile(String cerFile) {
        String key = "";
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            FileInputStream in = new FileInputStream(cerFile);
            Certificate c = cf.generateCertificate(in);
            PublicKey publicKey = c.getPublicKey();
            key = Base64.encode(publicKey.getEncoded());
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return key;
    }

    /**
     * 从PFX文件中获取私钥
     *
     * @param strPfx      文件存储目录
     * @param strPassword PFX密码
     */
    private static PrivateKey getPrivateKeyFromPfxFile(String strPfx, String strPassword) {
        try {
            FileInputStream fis = new FileInputStream(strPfx);
            //密码处理
            char[] nPassword = null;
            if ((strPassword == null) || strPassword.trim().equals("")) {
                nPassword = null;
            } else {
                nPassword = strPassword.toCharArray();
            }
            //加载读取PFX文件
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(fis, nPassword);
            fis.close();
            Enumeration enumas = ks.aliases();
            //从文件中获取秘钥
            String keyPFXFile = null;
            if (enumas.hasMoreElements()) {
                keyPFXFile = (String) enumas.nextElement();
            }
            PrivateKey prikey = (PrivateKey) ks.getKey(keyPFXFile, nPassword);
            Certificate cert = ks.getCertificate(keyPFXFile);
            PublicKey pubkey = cert.getPublicKey();
            BASE64Encoder bse = new BASE64Encoder();
            System.out.println("public key = ");
            System.out.println(pubkey);
            System.out.println("private key = ");
            System.out.println(prikey);
            System.out.println("public encode = ");
            System.out.println(bse.encode(pubkey.getEncoded()));
            System.out.println("private encode = ");
            System.out.println(bse.encode(prikey.getEncoded()));
            return prikey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}