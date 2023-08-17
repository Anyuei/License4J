package com.license4j.license.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CipherLicense {

    @Schema(name = "开始时间", required = true)
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;


    @Schema(name = "结束时间", required = true)
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Schema(name = "授权码唯一code信息", required = true)
    private String licenseCode;

    @Schema(name = "系统名称", required = true)
    private String systemName;

    @Schema(name = "公司名称")
    private String companyName;

    @Schema(name = "许可证书名称")
    private String licenseName;
    public static void checkInputTime(LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime){
        if (startLocalDateTime.isEqual(endLocalDateTime)) {
            throw new RuntimeException("开始时间不能等于结束时间");
        }
        if (startLocalDateTime.isAfter(endLocalDateTime)) {
            throw new RuntimeException("开始时间必须大于小于结束时间");
        }
        if (endLocalDateTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("结束时间必须大于当前时间");
        }
    }
}
