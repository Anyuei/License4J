package com.license4j.license.utils;

import com.license4j.license.entity.CipherLicense;
import com.license4j.license.entity.License;
import com.license4j.license.entity.PublicAndPrivateKey;
import com.license4j.license.service.LicenseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: AnYunPei
 * @Description: 生成公钥私钥及授权码
 **/
@Slf4j
public class LicenseUtil {

    @Resource
    private LicenseService licenseService;
    /**
     * 凯撒加密key
     */
    public final static Integer[] KAISER_KEY = {5, 11, 2, 19, 9, 12, 20, 8, 10};

    /**
     * 生成密钥对以及授权密文文件
     * 私钥加密，公钥解密
     * 加密时使用公钥加密，需要将生成的license.key和license.pub拷贝给用户，并且持久化数据
     * 私钥由平台管理员保存，私钥用于加密！
     *
     * @param startTime   授权开始时间
     * @param endTime     授权开始时间
     * @param licenseCode 应用授权码，由用户通过接口根据一定规则生成
     * @return
     */
    public static License getLicense(LocalDateTime startTime,
                                     LocalDateTime endTime,
                                     String licenseCode,
                                     String systemName,
                                     String companyName) {
        return getLicense(
                startTime.toInstant(ZoneOffset.of("+8")).toEpochMilli(),
                endTime.toInstant(ZoneOffset.of("+8")).toEpochMilli(),
                licenseCode,
                systemName,
                companyName);
    }

    /**
     * 生成密钥对以及授权密文文件
     * 私钥加密，公钥解密
     * 加密时使用公钥加密，需要将生成的license.key和license.pub拷贝给用户，并且持久化数据
     * 私钥由平台管理员保存，私钥用于加密！
     *
     * @param startTime   授权开始时间
     * @param endTime     授权开始时间
     * @param licenseCode 应用授权码，由用户通过接口根据一定规则生成
     * @return
     */
    public static License getLicense(Long startTime,
                                     Long endTime,
                                     String licenseCode,
                                     String systemName,
                                     String companyName) {
        License license = new License();
        try {
            license.setSystemName(systemName);

            LocalDateTime startLocalTime = LocalDateTime.ofEpochSecond(startTime / 1000, 0, ZoneOffset.ofHours(8));
            LocalDateTime endLocalTime = LocalDateTime.ofEpochSecond(endTime / 1000, 0, ZoneOffset.ofHours(8));
            //时间合法性校验
            CipherLicense.checkInputTime(startLocalTime, endLocalTime);
            license.setStartTime(startLocalTime);
            license.setEndTime(endLocalTime);
            license.setCompanyName(companyName);

            PublicAndPrivateKey publicAndPrivateKey = EncryptUtil.generateKeyPair(startTime, licenseCode);
            BeanUtils.copyProperties(publicAndPrivateKey, license);

            // 生成随机key，只包含大写及数字
            String key = UUIDUtils.getUuId().toUpperCase();
            // 设置签名，key各字符的asc码，并且都为2位
            StringBuilder signBuilder = new StringBuilder();
            char[] chars = key.toCharArray();
            for (char c : chars) {
                String s = String.valueOf((int) c);
                if (s.length() != 2) {
                    throw new RuntimeException("生成的key格式错误");
                }
                signBuilder.append(s);
            }
            String sign = signBuilder.toString();



            // 生成参数
            StringBuilder paramBuilder = new StringBuilder();
            paramBuilder.append("key=").append(key).append("&startTime=").append(startTime)
                    .append("&endTime=").append(endTime)
                    .append("&sign=").append(sign)
                    .append("&licenseCode=").append(licenseCode)
                    .append("&systemName=").append(systemName);
            // 查看参数长度
            int length = paramBuilder.length();
            paramBuilder.append("&").append(length);
            String param = paramBuilder.toString();

            // 私钥加密参数
            PrivateKey privateKey = EncryptUtil.loadPrivateKeyFromString(publicAndPrivateKey.getPriKey());
            String encrypted = EncryptUtil.encryptByAsymmetric(param, privateKey);
            // 凯撒加密
            String licenseStr = KaiserBuilderUtil.encrypt(encrypted);
            //FileUtils.writeStringToFile(new File(licensePath), encryptKaiser, String.valueOf(StandardCharsets.UTF_8));
            license.setLicense(Base64Utils.encodeToString(licenseStr.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("生成授权文件失效！" + e);
            throw new RuntimeException("生成授权文件失效!"+e.getMessage());
        }
        license.setCreateTime(LocalDateTime.now());
        license.setUpdateTime(LocalDateTime.now());
        license.setCreateBy("Admin");
        license.setUpdateBy("Admin");
        return license;
    }

    /**
     * 校验许可证
     *
     * @param licenseText
     * @return
     * @throws ParseException
     */
    public static Map<String, String> checkLicense(String licenseText, String pubStr) {
        // 凯撒解密
        String decryptLicense = KaiserBuilderUtil.decrypt(new String(Base64Utils.decodeFromString(licenseText)));
        // 从公钥字符串中获取公钥
        PublicKey publicKey = EncryptUtil.loadPublicKeyFromString(pubStr);
        // 使用公钥解密许可证，获取许可证信息
        String params = EncryptUtil.decryptByAsymmetric(decryptLicense, publicKey);
        String param = params.substring(0, params.lastIndexOf("&"));
        // 验证参数的长度
        checkLicenseLength(params);
        //验证许可证签名
        checkLicenseSign(param);
        //验证许可证授权时间是否有效
        checkLicenseAvailableTime(param);

        return getParamMap(param);
    }
    /**
     * 验证许可证有效期
     *
     * @param param
     * @return
     */
    public static Boolean checkLicenseAvailableTime(String param) {
        Map<String, String> paramMap = getParamMap(param);
        long startTime = Long.parseLong(paramMap.get("startTime"));
        long endTime = Long.parseLong(paramMap.get("endTime"));
        // 获取授权比较时间
        long compareTime = 0;
        //获取系统时间
        compareTime = System.currentTimeMillis();
        // 判断授权时间
        if (compareTime > endTime || compareTime < startTime) {
            LocalDateTime start = LocalDateTime.ofEpochSecond(startTime / 1000, 0, ZoneOffset.ofHours(8));
            LocalDateTime end = LocalDateTime.ofEpochSecond(endTime / 1000, 0, ZoneOffset.ofHours(8));
            throw new RuntimeException("系统许可证已过期!当前许可证有效期为："+start+"~"+end+",请联系系统管理员申请新的许可");
        }
        return true;
    }
    /**
     * 验证许可签名
     *
     * @param param
     * @return
     */
    public static Boolean checkLicenseSign(String param) {
        Map<String, String> paramMap = getParamMap(param);
        String key = paramMap.get("key");
        String sign = paramMap.get("sign");
        // 将key再次转为sign，验证key的签名是否正确
        StringBuilder signBuilder = new StringBuilder();
        char[] chars = key.toCharArray();
        for (char c : chars) {
            String s = String.valueOf((int) c);
            if (s.length() != 2) {
                throw new RuntimeException("许可证签名生成的key格式错误");
            }
            signBuilder.append(s);
        }
        String signKey = signBuilder.toString();
        if (!signKey.equals(sign)) {
            throw new RuntimeException("解析key的签名错误");
        }
        return true;
    }

    /**
     * 检验许可证长度是否正确，放置篡改。
     *
     * @param params
     * @return
     */
    public static Boolean checkLicenseLength(String params) {
        int length = Integer.parseInt(params.substring(params.lastIndexOf("&") + 1));
        String param = params.substring(0, params.lastIndexOf("&"));
        if (param.length() != length) {
            throw new RuntimeException("许可证长度校验失败!");
        }
        return true;
    }
    /**
     * 自测授权文件与密钥是否正确
     * 加载授权密文文件进行校验
     * 私钥加密，公钥解密
     *
     * @param code        加密时用户的机器码
     * @param licensePath 授权文件位置
     * @param pubPath     公钥位置
     * @return
     */
    public static Map<String, String> testLicense(String code, String licensePath, String pubPath) {
        try {
            // 读取密文内容
            String licenseText = FileUtils.readFileToString(new File(licensePath), String.valueOf(StandardCharsets.UTF_8));
            // 凯撒解密
            StringBuilder kaiserBuilder = new StringBuilder();
            char[] decryptChars = licenseText.toCharArray();
            for (int index = 0; index < decryptChars.length; index++) {
                int keyIndex = index % KAISER_KEY.length;
                char c = decryptChars[index];
                String s = String.valueOf(c);
                String encryptKaiser = EncryptUtil.decryptKaiser(s, KAISER_KEY[keyIndex]);
                kaiserBuilder.append(encryptKaiser);
            }
            String decryptLicense = kaiserBuilder.toString();
            // 使用私密进行解密获取加密参数
            // 从文件中加载公钥
            PublicKey key1 = EncryptUtil.loadPublicKeyFromFile(pubPath);
            // 从文件中加载公钥
            //PrivateKey key2 = EncryptUtil.loadPrivateKeyFromFile(pubPath);
            // 获取原文参数
            String params = EncryptUtil.decryptByAsymmetric(decryptLicense, key1);

//            String keyString = FileUtils.readFileToString(new File(pubPath), String.valueOf(StandardCharsets.UTF_8));
//            String params = RsaUtils.decryptByPublicKey(keyString, decryptLicense);
            // 验证参数的长度
            int length = Integer.parseInt(params.substring(params.lastIndexOf("&") + 1));
            String param = params.substring(0, params.lastIndexOf("&"));
            if (param.length() != length) {
                throw new RuntimeException("验证参数长度校验失败!");
            }
            Map<String, String> paramMap = getParamMap(param);
            String key = paramMap.get("key");
            String sign = paramMap.get("sign");
            long startTime = Long.parseLong(paramMap.get("startTime"));
            long endTime = Long.parseLong(paramMap.get("endTime"));
            String licenseCode = paramMap.get("licenseCode");

            // 将key再次转为sign，验证key的签名是否正确
            StringBuilder signBuilder = new StringBuilder();
            char[] chars = key.toCharArray();
            for (char c : chars) {
                String s = String.valueOf((int) c);
                if (s.length() != 2) {
                    throw new RuntimeException("生成的key格式错误");
                }
                signBuilder.append(s);
            }
            String signKey = signBuilder.toString();
            if (!signKey.equals(sign)) {
                throw new RuntimeException("解析key的签名错误");
            }

            // 判断授权时间
            long now = System.currentTimeMillis();
            if (now > endTime || now < startTime) {
                throw new RuntimeException("授权时间无效!");
            }

            // 验证授权码是否正确
            // 对解析授权码与传入的授权码进行对比
            if (!licenseCode.equals(code)) {
                throw new RuntimeException("授权码不匹配!");
            }

            System.out.println("授权验证成功！");
            return paramMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, String> getParamMap(String param) {
        Map<String, String> map = new HashMap<>(8);
        // param为：key=1111&startTime=123.....
        String[] split = param.split("&");
        for (String s : split) {
            // 找到第一个"="位置，然后进行分割
            int indexOf = s.indexOf("=");
            String key = s.substring(0, indexOf);
            String value = s.substring(indexOf + 1);
            map.put(key, value);
        }
        return map;
    }

}
