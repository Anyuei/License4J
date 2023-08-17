package com.license4j.license.entity;

import java.io.File;
import java.time.format.DateTimeFormatter;

public interface CommonConstant {

    /* 业务正常 */ int RESULT_OK_200 = 200;

    /* 用户未登录 */
    int RESULT_NO_AUTH = 401;


    String PARAMETER_PARSE_ERROR = "参数解析异常，请检查参数是否合法";
}
