package com.license4j.license.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: AnYunPei
 * @Description: 生成公钥私钥及授权码
 **/
@Slf4j
public class LicenseUtil {

    //本系统用于解密的公钥
    public static String LICENSE_PUB_KEY = "";


    public final static long MAX_ERROR_FILE = 10 * 1024 * 1024;
    public final static int MAX_ERROR_FILE_NUM = 10;
    public final static String ERROR_FILE_DEFAULT = "error.log";
    public final static String ERROR_FILE_PREFIX = "error-";
    public final static String ERROR_FILE_SUFFIX = ".log";

    /**
     * 校验许可证
     *
     * @param licenseText
     * @return
     * @throws ParseException
     */
    public static Map<String, String> checkLicense(String licenseText, String pubPath) {
        // 凯撒解密
        String decryptLicense = KaiserBuilderUtil.decrypt(new String(Base64Utils.decodeFromString(licenseText)));
        // 从公钥字符串中获取公钥
        PublicKey publicKey = DecodeUtil.loadPublicKeyFromFile(pubPath);
        // 使用公钥解密许可证，获取许可证信息
        String params = DecodeUtil.decryptByAsymmetric(decryptLicense, publicKey);
        String param = params.substring(0, params.lastIndexOf("&"));
        // 验证参数的长度
        checkLicenseLength(params);
        //验证许可证签名
        checkLicenseSign(param);
        //验证许可证是否为本机许可证
        checkLicenseCode(param);
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
     * 验证许可证使用时长
     *
     * @param param
     * @return
     */
    public static Boolean checkLicenseUsedTime(String param) {
        Map<String, String> paramMap = getParamMap(param);
        long startTime = Long.parseLong(paramMap.get("startTime"));
        long endTime = Long.parseLong(paramMap.get("endTime"));
        // 获取授权比较时间
        long compareTime = 0;
        try {
            //获取公钥内部的系统运行总时间
            compareTime = CompareTimeUtil.getCompareTime();
        } catch (ParseException e) {
            throw new RuntimeException("时间转换异常，请检查许可证信息中时间格式是否正确!");
        }
        // 判断授权时间
        if (compareTime > endTime || compareTime < startTime) {
            throw new RuntimeException("授权时间无效!系统可用运行时间不足，请联系系统管理员申请新的许可");
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
     * 检验许可证code是否为本机
     *
     * @param param 许可证信息
     * @return
     */
    public static Boolean checkLicenseCode(String param) {
        Map<String, String> paramMap = getParamMap(param);
        String licenseCode = paramMap.get("licenseCode");
        // 验证授权码是否正确
        // 获取服务器的硬件信息编码
        String applicationInfo = CipherUtil.getApplicationInfo();
        // 对授权码进行解密
        String encryptData = DecodeUtil.decryptBySymmetry(licenseCode, DecodeUtil.AES_KEY, DecodeUtil.AES, true);
        // 对授权码进行与硬件信息编码进行匹配
        if (!applicationInfo.equals(encryptData)) {
            throw new RuntimeException("许可证不匹配!请检查是否本机许可证");
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
     * 加载授权码文件，判断是否授权成功
     *
     * @param licensePath 授权码文件位置
     * @param pubPath     公钥位置
     * @return
     */
    public static Map<String, String> loadLicense(String licensePath, String pubPath) throws IOException {
        String licenseText = FileUtils.readFileToString(new File(licensePath), String.valueOf(StandardCharsets.UTF_8));
        return checkLicense(licenseText, pubPath);
    }


    /**
     * 将授权的错误信息写入文件
     *
     * @param e
     * @param errorPath
     * @throws IOException
     */
    private static void writeErrorToFile(Exception e, String errorPath) throws IOException {
        File parentFile = new File(errorPath);
        // 获取当前文件夹，的所有文件列表
        File[] listFiles = parentFile.listFiles();
        int fileNum = 1;
        List<File> fileList = new ArrayList<>();
        if (listFiles != null) {
            // 按修改日期排行
            for (File sonFile : listFiles) {
                if (sonFile.getName().contains(ERROR_FILE_PREFIX)) {
                    fileNum++;
                    fileList.add(sonFile);
                }
            }
        }

        // 如果文件过大(10MB)，则先复制副本，再进行删除
        File file = new File(errorPath + File.separator + ERROR_FILE_DEFAULT);
        if (file.exists() && file.length() > MAX_ERROR_FILE) {
            // 按照日期排序
            List<File> fileCollect = fileList.stream().sorted(Comparator.comparing(File::lastModified)).collect(Collectors.toList());
            // 如果大于10个，则删除文件，只保留10个
            while (fileCollect.size() >= MAX_ERROR_FILE_NUM) {
                boolean delete = fileCollect.get(0).delete();
                fileCollect.remove(0);
            }
            for (int index = 0; index < fileCollect.size(); index++) {
                String name = errorPath + File.separator + ERROR_FILE_PREFIX + (index + 1) + ERROR_FILE_SUFFIX;
                boolean b = fileCollect.get(index).renameTo(new File(name));
            }
            fileNum = Math.min(fileNum, MAX_ERROR_FILE_NUM);
            String newErrorFile = errorPath + File.separator + ERROR_FILE_PREFIX + fileNum + ERROR_FILE_SUFFIX;
            // 重新命名
            boolean b = file.renameTo(new File(newErrorFile));
            // 删除原文件
            boolean delete = file.delete();
        }
        // 获取当前日期
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = dateTimeFormatter.format(now);
        String message = format + " " + e.getMessage() + "\n";
        FileUtils.writeStringToFile(file, message, String.valueOf(StandardCharsets.UTF_8), true);
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
