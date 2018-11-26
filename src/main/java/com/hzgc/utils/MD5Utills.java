package com.hzgc.utils;
import org.apache.commons.codec.digest.DigestUtils;
/**
 * MD5加密工具类
 * created by liang on 18-11-20
 */
public class MD5Utills {

        public static String md5(String str) {
            return DigestUtils.md5Hex(str);
        }

        private static final String salt = "1a2b3c4d";

        public static String inputPassToFormPass(String inputPass, String salt) {
            String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
            return md5(str);
        }

        public static String formPassToDBPass(String formPass, String saltDB) {
            String str = "" + saltDB.charAt(0) + saltDB.charAt(2) + formPass + saltDB.charAt(5) + saltDB.charAt(4);
            return md5(str);
        }

        public static void main(String[] args) {
            System.out.println(inputPassToFormPass("123456", salt));//d3b1294a61a07da9b49b6e22b2cbd7f9
            System.out.println(formPassToDBPass(inputPassToFormPass("123456", salt), salt));//b7797cce01b4b131b433b6acf4add449

        }

    }