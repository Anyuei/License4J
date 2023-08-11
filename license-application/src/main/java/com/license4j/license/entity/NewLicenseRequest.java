package com.license4j.license.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("新许可设置请求")
public class NewLicenseRequest {
    @ApiModelProperty("新的许可")
    private String newLicense;
}
