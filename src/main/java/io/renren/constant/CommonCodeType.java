package io.renren.constant;

public enum CommonCodeType {
    /**
     * 正常返回-成功
     */
    SUCCESS(1, "SUCCESS"),
    /**
     * 业务失败
     */
    FAILURE(0, "FAILURE"),
    MOBILE_REGESTED(1001, "手机号已注册"),
    MOBILE_UNREGESTED(1002, "手机号未注册"),
    USER_STATUS_ABNORMAL(1003, "用户状态异常"),
    VERIFYCODE_EXPIRED(1004, "验证码已过期"),
    DATE_FORMAT_ERROR(1005, "日期格式不正确"),
    LESS_CURRENT_DATE(1006, "传入日期需小于当前日期"),
    UNKNOWN_ERROR(5001, "未知错误"),
    SERVER_ERROR(5002, "系统错误");

    private int code;
    private String value;

    CommonCodeType(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
