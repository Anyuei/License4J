package com.license4j.license.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

public class CompareTimeUtil {

    /**
     * AES偏移量16位，安装规则固定即可
     */
    private final static String AES_IV = "A64BF30925883C04";

    /**
     * 设置为CBC加密
     */
    private final static String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    /**
     * 构建加密比较时间
     *
     * @param startTime   授权开始时间
     * @param licenseCode 授权机器码
     */
    public static String generateCompareTime(Long startTime, String licenseCode) {

        Date date = new Date(startTime);
        DateFormat bf = new SimpleDateFormat("yyyyMMddHHmmss");
        String compareTime = bf.format(date);
        // 将字符转为ASCII码
        // 遍历字符串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < compareTime.length(); i++) {
            sb.append((compareTime.charAt(i)));
            if (i < compareTime.length() - 1) {
                sb.append("-");
            }
        }
        // 获取AES加密key,当前应用方的机器码的前16个字母，如果字母不够则使用`0`补充
        String key = licenseCode.replaceAll("\\s*", "").replaceAll("[^(A-Za-z)]", "");
        key = key.length() > 16 ? key.substring(0, 16) : generateKey(key);
        // 进行AES加密
        return encryptBySymmetry(sb.toString(), key);
    }

    private static String generateKey(String key) {
        for (int i = key.length(); i < 16; i++) {
            key = key.concat("0");
        }
        return key;
    }

    /**
     * 对称加密数据
     *
     * @param input : 原文
     * @param key   : 密钥
     * @return : 密文
     */
    public static String encryptBySymmetry(String input, String key) {
        try {
            // 获取加密对象
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            // 创建加密规则
            // 第一个参数key的字节
            // 第二个参数表示加密算法
            SecretKeySpec sks = new SecretKeySpec(key.getBytes(), "AES");

            // ENCRYPT_MODE：加密模式
            // DECRYPT_MODE: 解密模式
            // 初始化加密模式和算法
            // 默认采用ECB加密：同样的原文生成同样的密文,并行进行
            // CBC加密：同样的原文生成的密文不一样,串行进行
            // 使用CBC模式
            IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, sks, iv);


            // 加密
            byte[] bytes = cipher.doFinal(input.getBytes());

            // 输出加密后的数据
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密失败！");
        }
    }

    /**
     * 对称解密
     *
     * @param input : 密文
     * @param key   : 密钥
     * @throws Exception
     * @return: 原文
     */
    public static String decryptBySymmetry(String input, String key) {
        try {
            // 1,获取Cipher对象
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            // 指定密钥规则
            SecretKeySpec sks = new SecretKeySpec(key.getBytes(), "AES");
            // 默认采用ECB加密：同样的原文生成同样的密文
            // CBC加密：同样的原文生成的密文不一样

            // 使用CBC模式
            IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, sks, iv);

            // 3. 解密，上面使用的base64编码，下面直接用密文
            byte[] bytes = cipher.doFinal(Base64.decodeBase64(input));
            //  因为是明文，所以直接返回
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解密失败！");
        }
    }
}
