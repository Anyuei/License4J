package com.license4j.license.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("新公钥设置请求")
public class NewPubKeyRequest {
    @ApiModelProperty("新的公钥")
    private String newPubKey;
}
