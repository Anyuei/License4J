package com.license4j.license.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class IdNotNullRequest {

    @Schema(name = "id")
    @NotNull
    private Long id;

}
