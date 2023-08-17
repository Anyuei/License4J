package com.license4j.license.controller;

import com.license4j.license.entity.CipherLicense;
import com.license4j.license.entity.License;
import com.license4j.license.entity.PublicKeyAndLicenseKey;
import com.license4j.license.entity.Result;
import com.license4j.license.entity.request.IdNotNullRequest;
import com.license4j.license.service.LicenseService;
import com.license4j.license.utils.LicenseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "加密授权接口管理")
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
    @Operation(summary = "生成新的公钥和授权文件", description = "生成新的公钥和授权文件")
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
    @Operation(summary = "根据id删除许可", description = "根据id删除许可")
    @PostMapping("/deleteLicenseById")
    public Result deleteLicenseById(
            @Valid @RequestBody IdNotNullRequest idNotNullRequest) {
        licenseService.removeById(idNotNullRequest.getId());
        return Result.ok();
    }

    @Operation(summary = "手动校验公钥与许可证的合法性", description = "手动校验公钥与许可证的合法性")
    @GetMapping("/checkPublicKeyAndLicenseKey")
    public Result checkPublicKeyAndLicenseKey(@RequestBody PublicKeyAndLicenseKey publicKeyAndLicenseKey) {
        LicenseUtil.checkLicense(
                publicKeyAndLicenseKey.getLicenseKey(),
                publicKeyAndLicenseKey.getPubKey());
        return Result.ok();
    }
}
