package com.license4j.license.entity;

import java.io.File;
import java.time.format.DateTimeFormatter;

/**
 * 公共常量池
 *
 * @author SongJian
 */
public interface CommonConstant {

    /* 业务正常 */ int RESULT_OK_200 = 200;

    /* 用户未登录 */
    int RESULT_NO_AUTH = 401;

    /* 会话过期，请重新登录 */
    int RESULT_LOGIN_AGAIN = 420;
    /* 密码错误 */
    int RESULT_PASSWORD_ERROR = 421;
    /* 用户名已存在 */
    int RESULT_NAME_ALREADY_EX = 422;
    /* 账号已过期 */
    int RESULT_ACCOUNT_EXPIRY = 423;
    /* 账号未激活 */
    int RESULT_ACCOUNT_UNACTIVATED = 424;
    /* 账号已锁定 */
    int RESULT_ACCOUNT_LOCKED = 425;
    /* 服务器端拒绝执行的操作 */
    int RESULT_REFUSED_OPERATION = 430;
    /* 用户不存在 */
    int RESULT_USER_NOT_EXISTS = 404;
    /* 服务器内部错误 */ int RESULT_INTERNAL_SERVER_ERROR_500 = 500;
    /* 参数错误 */ int RESULT_PARAMETER_ERROR = 508;

    String SYMBOL_COLON = ":";
    String SYMBOL_STAR = "*";
    String SYMBOL_COMMA = ",";
    String SYMBOL_DOT = ".";
    String TIP_UNAUTHORIZED = "访问权限不足，请联系管理员";

    String PARAMETER_PARSE_ERROR = "参数解析异常，请检查参数是否合法";

    String HTTP_METHOD_POST = "POST";
    String HTTP_METHOD_DELETE = "DELETE";
    String HTTP_METHOD_PUT = "PUT";
    String HTTP_METHOD_GET = "GET";
    String HTTP_METHOD_OPTION = "OPTION";

    String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter PATTERN_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    String PROD = "prod";
}
