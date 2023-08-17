package com.license4j.license.entity.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class LoginRequest {

    @Schema(name = "管理员名称")
    @NotBlank(message = "管理员名称不能为空")
    private String managerName;

    @Schema(name = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

}
