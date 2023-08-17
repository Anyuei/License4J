package com.license4j.license.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PublicKeyAndLicenseKey {
    @Schema(name = "公钥")
    private String pubKey;
    @Schema(name = "许可证")
    private String licenseKey;
}