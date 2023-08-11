package com.license4j.license.utils;


public class KaiserBuilderUtil {
    /**
     * 凯撒加密key
     */
    public final static Integer[] KAISER_KEY = {5, 11, 2, 19, 9, 12, 20, 8, 10};
    public static String encrypt(String data){
        // 凯撒加密
        StringBuilder kaiserBuilder = new StringBuilder();
        char[] encryptedChars = data.toCharArray();
        // 将私钥加密后密文的每个字符进行凯撒位移加密
        for (int index = 0; index < encryptedChars.length; index++) {
            int keyIndex = index % KAISER_KEY.length;
            char c = encryptedChars[index];
            String s = String.valueOf(c);
            String encryptKaiser = EncryptUtil.encryptKaiser(s, KAISER_KEY[keyIndex]);
            kaiserBuilder.append(encryptKaiser);
        }
        return kaiserBuilder.toString();
    }
    public static String decrypt(String data){
        StringBuilder kaiserBuilder = new StringBuilder();
        char[] decryptChars = data.toCharArray();
        for (int index = 0; index < decryptChars.length; index++) {
            int keyIndex = index % KAISER_KEY.length;
            char c = decryptChars[index];
            String s = String.valueOf(c);
            String encryptKaiser = EncryptUtil.decryptKaiser(s, KAISER_KEY[keyIndex]);
            kaiserBuilder.append(encryptKaiser);
        }
        return kaiserBuilder.toString();
    }
}
