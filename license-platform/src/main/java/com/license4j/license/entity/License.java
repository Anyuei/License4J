package com.license4j.license.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel(value = "许可")
@Data
@TableName("stmf_license")
public class License {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "被授权系统名称")
    private String systemName;

    @ApiModelProperty(value = "许可证书内容")
    private String license;

    @ApiModelProperty(value = "许可证书名称")
    private String licenseName;

    @ApiModelProperty(value = "私钥")
    private String  priKey;

    @ApiModelProperty(value = "公钥")
    private String  pubKey;

    @ApiModelProperty(value = "有效期开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "有效期结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "证书创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "证书修改人")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
}
