package com.license4j.license.entity.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: AnYunPei
 * @Date: 2022/3/10 11:15
 * @Description:
 **/
@Data
public class IdNotNullRequest {

    @ApiModelProperty(value = "id")
    @NotNull
    private Long id;

}
