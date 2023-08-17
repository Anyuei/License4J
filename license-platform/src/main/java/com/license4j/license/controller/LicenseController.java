package com.license4j.license.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.license4j.license.entity.License;
import com.license4j.license.entity.request.ListLicenseRequest;
import com.license4j.license.entity.Result;
import com.license4j.license.service.LicenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "许可证管理")
@RestController
@RequestMapping("/license")
@Slf4j
public class LicenseController {

    @Resource
    private LicenseService licenseService;

    @Operation(summary = "获取许可列表", description = "获取许可列表")
    @PostMapping("/listLicense")
    public Result listLicense(@RequestBody ListLicenseRequest listLicenseRequest) {
        String companyName = listLicenseRequest.getCompanyName();
        String systemName = listLicenseRequest.getSystemName();
        String licenseName = listLicenseRequest.getLicenseName();
        Integer currentPage = listLicenseRequest.getCurrentPage();
        Integer pageSize = listLicenseRequest.getPageSize();
        LocalDateTime startTime = listLicenseRequest.getStartTime();
        LocalDateTime endTime = listLicenseRequest.getEndTime();
        Page<License> licensePage = new Page<>(currentPage,pageSize);
        QueryWrapper<License> licenseQueryWrapper = new QueryWrapper<>();
        licenseQueryWrapper.like(null!=companyName,"company_name",companyName);
        licenseQueryWrapper.like(null!=systemName,"system_name",systemName);
        licenseQueryWrapper.like(null!=licenseName,"license_name",licenseName);
        licenseQueryWrapper.ge(null!=startTime,"start_time",startTime);
        licenseQueryWrapper.le(null!=endTime,"end_time",endTime);
        IPage<License> page = licenseService.page(licensePage, licenseQueryWrapper);
        return Result.ok(page);
    }
}
