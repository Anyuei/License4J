package com.license4j.license.entity.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.license4j.license.entity.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ListLicenseRequest extends Pagination {

    @Schema(name = "开始时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @NotBlank(message = "请输入授权开始时间")
    private LocalDateTime startTime;


    @Schema(name = "结束时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @NotBlank(message = "请输入授权结束时间")
    private LocalDateTime endTime;

    @Schema(name = "许可名称")
    private String licenseName;

    @Schema(name = "公司名称")
    @NotBlank(message = "请输入被授权公司名称")
    private String companyName;

    @Schema(name = "系统名称")
    @NotBlank(message = "请输入被授权系统名称")
    private String systemName;
}
