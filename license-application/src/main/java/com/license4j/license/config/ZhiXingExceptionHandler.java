package com.license4j.license.config;

import com.license4j.license.entity.CommonConstant;
import com.license4j.license.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ZhiXingExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handHttpMessageNotReadableException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(CommonConstant.RESULT_PARAMETER_ERROR, CommonConstant.PARAMETER_PARSE_ERROR);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleBindException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return Result.error(CommonConstant.RESULT_PARAMETER_ERROR, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        log.error(e.getMessage());
        return Result.error(CommonConstant.RESULT_PARAMETER_ERROR, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

//    @ExceptionHandler(UnauthorizedException.class)
//    public Result handleUnauthorizedException(UnauthorizedException e) {
//        log.error(e.getMessage());
//        return Result.error(CommonConstant.RESULT_NO_AUTH, CommonConstant.TIP_UNAUTHORIZED);
//    }
}