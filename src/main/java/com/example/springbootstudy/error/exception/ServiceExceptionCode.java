package com.example.springbootstudy.error.exception;

/**
 * 服务错误代码
 */
public class ServiceExceptionCode {
    /**
     * 未知错误
     */
    public static final int DEFAULT_ERROR = 10000;
    /**
     * 无效参数
     */
    public static final int PARAMS_ERROR = 10001;
    /**
     * 用户未注册
     */
    public static final int USER_NO_REGISTER = 10002;
    /**
     * 用户密码错误
     */
    public static final int USER_PASSWORD_ERROR = 10003;
    /**
     * 短信验证码错误
     */
    public static final int SMS_CODE_ERROR = 10004;
    /**
     * 短信验证码已过期
     */
    public static final int SMS_CODE_EXPIRED = 10005;
}
