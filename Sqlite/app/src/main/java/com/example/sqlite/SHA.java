//package com.example.sqlite;
////单向加密
//import java.math.BigInteger;
//import java.security.MessageDigest;
///*
//SHA(Secure Hash Algorithm，安全散列算法），数字签名等密码学应用中重要的工具，
//被广泛地应用于电子商务等信息安全领域。虽然，SHA与MD5通过碰撞法都被破解了，
//但是SHA仍然是公认的安全加密算法，较之MD更为安全*/
//public class SHA {
//
//    public static final String KEY_SHA = "SHA";
//
//    public static String getResult(String inputStr) {
//        BigInteger sha = null;
//        System.out.println("加密前的数据>>>:" + inputStr);
//        byte[] inputData = inputStr.getBytes();
//        try {
//            MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
//            messageDigest.update(inputData);
//            sha = new BigInteger(messageDigest.digest());
//            System.out.println("SHA加密后>>>>>:" + sha.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sha.toString();
//    }
//
//    public static void main(String args[]) {
//        try {
//            String inputStr = "123456";
//            getResult(inputStr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
package com.example.sqlite;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SHA{
    public static final String KEY_SHA = "SHA";
    public String addPwd(String password) {
        BigInteger sha = null;
        System.out.println("=======加密前的数据:" + password);
        byte[] inputData = password.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
            messageDigest.update(inputData);

            sha = new BigInteger(messageDigest.digest());
            //return sha.toString(16);
            System.out.println("SHA加密后:" + sha.toString(16));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sha.toString(16);
    }

    public static void main(String[] args) {
        long s= System.currentTimeMillis();
        SHA sha = new SHA();
        sha.addPwd("123456");
        long e = System.currentTimeMillis();
        System.out.println("time:"+(e-s));
    }

}