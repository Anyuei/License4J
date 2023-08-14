package com.license4j.license.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "通用返回体")
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回处理消息
     */
    @ApiModelProperty(value = "返回信息", example = "操作成功")
    private String message = "操作成功！";

    /**
     * 返回代码
     */
    @ApiModelProperty(value = "状态码", example = "200")
    private Integer code;

    /**
     * 返回数据对象 data
     */
    @ApiModelProperty(value = "返回数据")
    private Object result;

    public static Result ok() {
        Result r = new Result();
        r.setCode(CommonConstant.RESULT_OK_200);
        r.setMessage("操作成功");
        return r;
    }

    public static Result ok(Object data) {
        Result r = new Result();
        r.setCode(CommonConstant.RESULT_OK_200);
        r.setMessage("操作成功");
        r.setResult(data);
        return r;
    }

    public static Result error(String msg) {
        return error(CommonConstant.RESULT_INTERNAL_SERVER_ERROR_500, msg);
    }

    public static Result errorParam(String msg) {
        return error(CommonConstant.RESULT_PARAMETER_ERROR, msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }

    public static Result noAuth() {
        return error(CommonConstant.RESULT_NO_AUTH, "操作权限不足，请联系管理员授权");
    }
}
