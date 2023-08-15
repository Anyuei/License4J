package com.license4j.license.entity.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @ApiModelProperty(value = "管理员名称")
    @NotBlank(message = "管理员名称不能为空")
    private String managerName;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

}
