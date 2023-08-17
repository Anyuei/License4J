package com.license4j.license.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.license4j.license.config.LicenseHandler;
import com.license4j.license.entity.NewLicenseAndPubKeyRequest;
import com.license4j.license.entity.NewLicenseRequest;
import com.license4j.license.entity.NewPubKeyRequest;
import com.license4j.license.entity.Result;
import com.license4j.license.utils.CipherUtil;
import com.license4j.license.utils.DecodeUtil;
import com.license4j.license.utils.LicenseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Api(tags = "无拦截接口")
@RestController
@RequestMapping("/noAuth")
@Slf4j
public class NoAuthController {
    @Value("${sys.license.key}")
    private String keyPath;

    @Value("${sys.license.log}")
    private String logPath;

    @Value("${sys.license.pub}")
    private String pubPath;
    @ApiOperation(value = "更新系统许可证", notes = "更新系统许可证，输入许可证有效时，才会更新许可")
    @ApiOperationSupport(order = 5)
    @PostMapping("/setNewLicense")
    public Result setNewLicense(@RequestBody NewLicenseRequest newLicenseRequest) {
        //校验许可证
        try{
            LicenseUtil.checkLicense(newLicenseRequest.getNewLicense(), pubPath);
        }catch (Exception e){
            log.info(e.getMessage());
            throw new RuntimeException("许可证错误,请联系管理员");
        }

        try {
            FileUtils.writeStringToFile(new File(keyPath), newLicenseRequest.getNewLicense(), String.valueOf(StandardCharsets.UTF_8));
            //失败次数置0
            LicenseHandler.FAIL_NUM = 0;
            //许可置为有效
            LicenseHandler.LICENSE_IS_AVAILABLE = true;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("文件写入失败");
        }
        return Result.ok("更新许可成功");
    }

    @ApiOperation(value = "更新系统公钥", notes = "更新系统公钥，用于系统解密")
    @ApiOperationSupport(order = 5)
    @PostMapping("/setNewPubKey")
    public Result setNewPubKey(@RequestBody NewPubKeyRequest newPubKeyRequest) {
        try {
            FileUtils.writeStringToFile(new File(pubPath), newPubKeyRequest.getNewPubKey(), String.valueOf(StandardCharsets.UTF_8));
            //失败次数置0
            LicenseHandler.FAIL_NUM = 0;
            //许可置为有效
            LicenseHandler.LICENSE_IS_AVAILABLE = true;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("文件写入失败");
        }
        return Result.ok("更新公钥成功");
    }
    @ApiOperation(value = "更新许可和系统公钥", notes = "更新系统公钥，用于系统解密")
    @PostMapping("/setNewLicenseAndNewPubKey")
    public Result setNewLicenseAndNewPubKey(
            @RequestBody NewLicenseAndPubKeyRequest newLicenseAndPubKeyRequest) {
        try {
            FileUtils.writeStringToFile(
                    new File(pubPath),
                    newLicenseAndPubKeyRequest.getNewPubKey(), String.valueOf(StandardCharsets.UTF_8));
            //失败次数置0
            LicenseHandler.FAIL_NUM = 0;
            //许可置为有效
            LicenseHandler.LICENSE_IS_AVAILABLE = true;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("文件写入失败");
        }
        //校验许可证
        try{
            LicenseUtil.checkLicense(newLicenseAndPubKeyRequest.getNewLicense(), pubPath);
        }catch (Exception e){
            log.info(e.getMessage());
            throw new RuntimeException("许可证错误,请联系管理员");
        }
        try {
            FileUtils.writeStringToFile(new File(keyPath), newLicenseAndPubKeyRequest.getNewLicense(), String.valueOf(StandardCharsets.UTF_8));
            //失败次数置0
            LicenseHandler.FAIL_NUM = 0;
            //许可置为有效
            LicenseHandler.LICENSE_IS_AVAILABLE = true;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("文件写入失败");
        }
        return Result.ok("更新成功");
    }
    @ApiOperation(value = "获取当前系统唯一身份ID,用于授权平台办法许可证", notes = "获取当前系统唯一身份ID")
    @ApiOperationSupport(order = 5)
    @GetMapping("/getId")
    public String getId() {
        String applicationInfo = CipherUtil.getApplicationInfo();
        String encryptAes = DecodeUtil.encryptBySymmetry(applicationInfo, DecodeUtil.AES_KEY, DecodeUtil.AES, true);
        log.info("授权码获取记录:" + encryptAes);
        return encryptAes;
    }
}
