package com.license4j.license.utils;


public class KaiserBuilderUtil {
    /**
     * 凯撒加密key
     */
    public final static Integer[] KAISER_KEY = {5, 11, 2, 19, 9, 12, 20, 8, 10};
    public static String decrypt(String data){
        StringBuilder kaiserBuilder = new StringBuilder();
        char[] decryptChars = data.toCharArray();
        for (int index = 0; index < decryptChars.length; index++) {
            int keyIndex = index % KAISER_KEY.length;
            char c = decryptChars[index];
            String s = String.valueOf(c);
            String encryptKaiser = DecodeUtil.decryptKaiser(s, KAISER_KEY[keyIndex]);
            kaiserBuilder.append(encryptKaiser);
        }
        return kaiserBuilder.toString();
    }

}
