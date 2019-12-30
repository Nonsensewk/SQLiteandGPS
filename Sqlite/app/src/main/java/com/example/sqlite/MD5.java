package com.example.sqlite;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public String str;

    public void md5s(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
            System.out.println("result: " + buf.toString());// 32位的加密
            System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
        } catch (NoSuchAlgorithmException e) {
// TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    public static void main(String agrs[]) {
        long s =System.currentTimeMillis();
        MD5 md51 = new MD5();
        md51.md5s("123456");
        long e = System.currentTimeMillis();
        System.out.println("time:"+(e-s));
    }

} 