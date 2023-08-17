package com.license4j.license.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(name = "许可")
@Data
@TableName("stmf_license")
public class License {

    @Schema(name = "id")
    private Long id;

    @Schema(name = "公司名称")
    private String companyName;

    @Schema(name = "被授权系统名称")
    private String systemName;

    @Schema(name = "许可证书内容")
    private String license;

    @Schema(name = "许可证书名称")
    private String licenseName;

    @Schema(name = "私钥")
    private String  priKey;

    @Schema(name = "公钥")
    private String  pubKey;

    @Schema(name = "有效期开始时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(name = "有效期结束时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Schema(name = "证书创建人")
    private String createBy;

    @Schema(name = "创建时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(name = "证书修改人")
    private String updateBy;

    @Schema(name = "修改时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
