package com.license4j.license.controller;

import com.license4j.license.utils.CipherUtil;
import com.license4j.license.utils.DecodeUtil;
import com.license4j.license.utils.LicenseUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: AnYunPei
 * @Description:
 **/
@Api(tags = "加密授权接口管理")
@RestController
@RequestMapping("/cipher")
@Slf4j
public class CipherController {
    @Value("${sys.license.key}")
    private String keyPath;

    @Value("${sys.license.log}")
    private String logPath;

    @Value("${sys.license.pub}")
    private String pubPath;

    /**
     * 用户在点击，查看`授权信息`按钮时，请求check接口,进行一次授权验证(每天第一次通过其他接口访问系统时，也会验证一次 )
     * 如果通过则返回授权信息(开始+结束时间)
     * 如果失败则返回授权码
     *
     * @return
     */
    @ApiOperation(value = "获取以及验证授权信息", notes = "获取以及验证授权信息")
    @ApiOperationSupport(order = 5)
    @GetMapping("/check")
    public Object check() {
        Map<String, String> resultMap = new HashMap<>(4);
        Map<String, String> map = null;
        // 验证是否通过了授权，通过了返回授权信息(开始+结束时间)
        try {
            map = LicenseUtil.loadLicense(keyPath, pubPath);
        } catch (Exception e) {
            resultMap.put("许可证状态", e.getMessage());
            resultMap.put("许可证有效期", "-");
            return resultMap;
        }
        long startTime = Long.parseLong(map.get("startTime"));
        long endTime = Long.parseLong(map.get("endTime"));
        LocalDateTime startTimeLocal = LocalDateTime.ofEpochSecond(startTime, 0, ZoneOffset.ofHours(8));
        LocalDateTime endTimeLocal = LocalDateTime.ofEpochSecond(endTime, 0, ZoneOffset.ofHours(8));
        // 只返回授权开始和结束时间给页面
        resultMap.put("许可证状态", "有效");
        resultMap.put("许可证有效期", startTimeLocal + "~" + endTimeLocal);
        return resultMap;
    }




    @ApiOperation(value = "测试请求拦截验证", notes = "测试请求拦截验证")
    @ApiOperationSupport(order = 5)
    @GetMapping("/test")
    public String test() {
        return "success";
    }

}
