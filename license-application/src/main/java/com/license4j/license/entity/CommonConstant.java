package com.license4j.license.entity;

import java.io.File;
import java.time.format.DateTimeFormatter;

public interface CommonConstant {

    /* 业务正常 */ int RESULT_OK_200 = 200;

    /* 用户未登录 */
    int RESULT_NO_AUTH = 401;

    /* 不允许进行人工监控业务 */
    int RESULT_REJECT_MONITOR = 444;

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
    /* 手机号重复 */
    int RESULT_DUPLICATE_PHONE_NUMBER = 202;

    /* 服务器内部错误 */ int RESULT_INTERNAL_SERVER_ERROR_500 = 500;
    /* 参数错误 */ int RESULT_PARAMETER_ERROR = 508;
    /* 欲新增的数据已存在 */ int RESULT_PARAMETER_ALREADY_EXIST = 509;

    /**
     * hrtc异常
     */
    int HRTC_ERROR = 541;

    String SYMBOL_COLON = ":";
    String SYMBOL_STAR = "*";
    String SYMBOL_COMMA = ",";
    String SYMBOL_DOT = ".";

    String TIP_INTERNAL_SERVER_ERROR = "服务器内部异常，请联系管理员";
    String ACCESS_NOT_ALLOWED = "AccessNotAllowed：";
    String ACCESS_UNAUTHORIZED = "Unauthorized:";
    String PARAM_ERROR = "Parameter Error";

    String TIP_UNAUTHORIZED = "访问权限不足，请联系管理员";

    String PARAMETER_PARSE_ERROR = "参数解析异常，请检查参数是否合法";

    String HTTP_METHOD_POST = "POST";
    String HTTP_METHOD_DELETE = "DELETE";
    String HTTP_METHOD_PUT = "PUT";
    String HTTP_METHOD_GET = "GET";
    String HTTP_METHOD_OPTION = "OPTION";

    String TAG_BATCH = "/batch";
    String TAG_LIST = "/list";

    String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter PATTERN_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    String PROD = "prod";

    String REDIS_PREFIX_REALTIME = "realtime:";
    String REDIS_PREFIX_ONLINE_DEVICE = "online_device:";
    String REDIS_PREFIX_LOCKS = "locks:";
    String LOCAL_RESOURCE_LOCATION = System.getProperty("user.home") + File.separator + "work" + File.separator + "app" + File.separator;
    String RESOURCE_PATH_PREFIX = "/static";
    /* 模型服务地址 */
    String AI_SERVER_ADDRESS_KEY ="AI_SERVER_ADDRESS_KEY";
    /* 账户离线配置阈值name */
    String ACCOUNT_OFFLINE_THRESHOLD_CONFIG_NAME = "account-offline-threshold-info";
}
