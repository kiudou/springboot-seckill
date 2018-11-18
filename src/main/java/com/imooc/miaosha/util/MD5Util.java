package com.imooc.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static final String salt = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass){
        String str = salt.charAt(0) + salt.charAt(2) + inputPass
                + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String DBsalt) {
        String str = DBsalt.charAt(0) + DBsalt.charAt(2) + formPass
                + DBsalt.charAt(5) + DBsalt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String DBSalt) {
        return formPassToDBPass(inputPassToFormPass(inputPass), DBSalt);
    }

//    public static void main(String[] args) {
//        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
//    }
}
