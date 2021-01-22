package com.dedu.cryptosignaturemodule;

import java.io.FileInputStream;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

/**
 * 验证X.509规范证书有效性
 */
public class VerifyX509Validity {

    public static void main(String args[]) throws Exception {
        String filePath = "E:\\QWQW.crt";
        CertificateFactory of = CertificateFactory.getInstance("X.509");
        FileInputStream in = new FileInputStream(filePath);
        java.security.cert.Certificate ceof = of.generateCertificate(in);
        in.close();
        X509Certificate t = (X509Certificate) ceof;
        Calendar calendar = Calendar.getInstance();
        Date day = calendar.getTime();
        try {
            t.checkValidity(day);
            System.out.println("证书在有效期之内！");
        } catch (CertificateExpiredException e) {
            System.out.println("证书已过期");
            System.out.println(e.getMessage());
        } catch (CertificateNotYetValidException e) {
            System.out.println("证书尚未开始生效！");
            System.out.println(e.getMessage());
        }
    }
}