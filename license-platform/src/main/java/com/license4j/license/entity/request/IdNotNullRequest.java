package com.license4j.license.entity.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class IdNotNullRequest {

    @ApiModelProperty(value = "id")
    @NotNull
    private Long id;

}
