package com.license4j.license.entity.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

/**
 *
 */
@Data
public class LoginRequest {

    @ApiModelProperty(value = "管理员名称")
    @NotNull
    private String managerName;

    @ApiModelProperty(value = "密码")
    @NotNull
    private String password;

}
