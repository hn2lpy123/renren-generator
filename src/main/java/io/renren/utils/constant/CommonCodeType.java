package io.renren.utils.constant;

public enum CommonCodeType {
    /**
     * 正常返回-成功
     */
    SUCCESS(1, "SUCCESS"),
    /**
     * 业务失败
     */
    FAILURE(0, "FAILURE"),
    REPEAT_SUBMIT(1002, "重复提交表单"),
    EXECUTE_SQL_FAIL(1001, "数据库操作失败"),
    DATABASE_CONNECT_FAIL(1000, "数据库连接失败");

    private int code;
    private String value;

    CommonCodeType(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public CommonCodeType getCodeType(int code) {
        for(CommonCodeType commonCodeType : CommonCodeType.values()){
            if(code == commonCodeType.getCode()){
                return commonCodeType;
            }
        }
        return  CommonCodeType.FAILURE;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
