package com.cmcc.v2x.dso.authcs.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static void main(String[] args) {
      String 终端id ="0012345678901234";
      String 终端MAC ="0012345678901234";
      String timestamp ="20190924133049794";
      String miwen = 终端id+终端MAC+timestamp;
        String md5Str = getMD5Str(miwen);
      System.out.println(md5Str);
    }

    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        //16位加密，从第9位到25位  大写
        return md5StrBuff.substring(8, 24).toString().toUpperCase();
        //16位加密，从第9位到25位  小写
//        return md5StrBuff.substring(8, 24).toString();
    }
}
