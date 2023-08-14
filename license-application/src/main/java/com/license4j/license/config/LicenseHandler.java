package com.license4j.license.config;

import com.license4j.license.utils.LicenseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LicenseHandler {

    public static Boolean LICENSE_IS_AVAILABLE = false;

    public static int FAIL_NUM = 0;
    private static final int FAIL_MAX_NUM = 10;

    @Value("${sys.license.log}")
    private String errorPath;

    @Value("${sys.license.pub}")
    private String pubPath;

    @Value("${sys.license.key}")
    private String licensePath;

    public boolean loadLicense() {
        // 10次请求出现授权失败，则直接返回失败不再加载授权文件。
        if (FAIL_NUM >= FAIL_MAX_NUM) {
            return false;
        }
        // 当授权验证为false时，进行重新验证，让前十次验证时即使没有授权也能成功，直到连续失败十次
        if (!LICENSE_IS_AVAILABLE) {
            try {
                LicenseUtil.loadLicense(licensePath, pubPath);
                FAIL_NUM = 0;
                LICENSE_IS_AVAILABLE = true;
            } catch (IOException e) {
                FAIL_NUM++;
                LICENSE_IS_AVAILABLE = false;
            }
        }
        return LICENSE_IS_AVAILABLE;
    }

    /**
     * 定时任务，bean不初始化时不会执行，每隔一个小时执行一次,加载一次许可证
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void scheduled() {
        try {
            LicenseUtil.loadLicense(licensePath, pubPath);
            FAIL_NUM = 0;
            LICENSE_IS_AVAILABLE = true;
        } catch (IOException e) {
            LICENSE_IS_AVAILABLE = false;
        }
    }
}