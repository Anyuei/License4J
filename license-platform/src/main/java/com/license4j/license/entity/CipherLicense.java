package com.license4j.license.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: AnYunPei
 * @Date: 2022/3/10 11:15
 * @Description:
 **/
@Data
public class CipherLicense {

    @ApiModelProperty(value = "开始时间", required = true)
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;


    @ApiModelProperty(value = "结束时间", required = true)
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "授权码唯一code信息", required = true)
    private String licenseCode;

    @ApiModelProperty(value = "系统名称", required = true)
    private String systemName;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "许可证书名称")
    private String licenseName;
    public static Boolean checkInputTime(LocalDateTime startLocalDateTime,LocalDateTime endLocalDateTime){
        if (startLocalDateTime.isEqual(endLocalDateTime)) {
            throw new RuntimeException("开始时间不能等于结束时间");
        }
        if (startLocalDateTime.isAfter(endLocalDateTime)) {
            throw new RuntimeException("开始时间必须大于小于结束时间");
        }
        if (endLocalDateTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("结束时间必须大于当前时间");
        }
        return true;
    }
}
