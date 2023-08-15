package com.license4j.license.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PublicKeyAndLicenseKey {
    @ApiModelProperty(value = "公钥")
    private String pubKey;
    @ApiModelProperty(value = "许可证")
    private String licenseKey;
}