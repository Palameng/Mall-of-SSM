package com.my.o2o.util.wechat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {
    private static String token = "myo2o";
    
    /**
     * 验证签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        //将token, timestamp, nonce这三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        
        MessageDigest md = null;
        String tmpStr = null;
        
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); 
        }
        
        content = null;
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }
    
    private static String byteToStr(byte[] byteArray){
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }
    
    private static String byteToHexStr(byte mByte){
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        
        tempArr[0] = Digit[(mByte >>> 4) & 0x0F];
        tempArr[1] = Digit[mByte & 0x0F];
        
        String s = new String(tempArr);
        return s;
    }
    
    
}
