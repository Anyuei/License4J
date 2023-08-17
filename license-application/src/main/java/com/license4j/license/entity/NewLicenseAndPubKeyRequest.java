package com.license4j.license.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("新许可和公钥设置请求")
public class NewLicenseAndPubKeyRequest {
    @ApiModelProperty("新的许可")
    private String newLicense;
    @ApiModelProperty("新的公钥")
    private String newPubKey;
}
