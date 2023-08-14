package com.license4j.license.controller;

import com.license4j.license.entity.CipherLicense;
import com.license4j.license.entity.License;
import com.license4j.license.entity.PublicKeyAndLicenseKey;
import com.license4j.license.entity.Result;
import com.license4j.license.entity.request.IdNotNullRequest;
import com.license4j.license.service.LicenseService;
import com.license4j.license.utils.LicenseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "加密授权接口管理")
@RestController
@RequestMapping("/cipher")
@Slf4j
public class CipherController {
    @Resource
    private LicenseService licenseService;

    /**
     * 生成公钥和授权文件-内部接口
     *
     * @return
     */
    @ApiOperation(value = "生成新的公钥和授权文件", notes = "生成新的公钥和授权文件")
    @PostMapping("/createNewLicenseAndPubKey")
    public Result createNewLicenseAndPubKey(@RequestBody CipherLicense param) {
        License license = LicenseUtil.getLicense(
                param.getStartTime(),
                param.getEndTime(),
                param.getLicenseCode(),
                param.getSystemName(),
                param.getCompanyName());
        licenseService.save(license);
        return Result.ok();
    }

    /**
     * 生成公钥和授权文件-内部接口
     *
     * @return
     */
    @ApiOperation(value = "根据id删除许可", notes = "根据id删除许可")
    @PostMapping("/deleteLicenseById")
    public Result deleteLicenseById(
            @Valid @RequestBody IdNotNullRequest idNotNullRequest) {
        licenseService.removeById(idNotNullRequest.getId());
        return Result.ok();
    }

    @ApiOperation(value = "手动校验公钥与许可证的合法性", notes = "手动校验公钥与许可证的合法性")
    @GetMapping("/checkPublicKeyAndLicenseKey")
    public Result checkPublicKeyAndLicenseKey(@RequestBody PublicKeyAndLicenseKey publicKeyAndLicenseKey) {
        LicenseUtil.checkLicense(
                publicKeyAndLicenseKey.getLicenseKey(),
                publicKeyAndLicenseKey.getPubKey());
        return Result.ok();
    }
}
