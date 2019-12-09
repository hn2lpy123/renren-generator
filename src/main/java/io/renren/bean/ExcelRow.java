package io.renren.bean;

/**
 * Excel导入基础类
 *
 * @Author lipingyu
 * @Date 2019-12-09
 */
public class ExcelRow extends BaseBean {

    public static final int SUCCESS_CODE = 0;

    public static final int FAILED_CODE = 2;

    /**
     * 所属行数，从0开始
     */
    private int rowNum = SUCCESS_CODE;

    /**
     * 校验码，当承载“行”的对象有设置注解，且校验不通过时，会将结果放置于此字段
     */
    private int validateCode;

    /**
     * 校验消息，业务尽量使用校验码做判断
     */
    private String validateMessage;

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(int validateCode) {
        this.validateCode = validateCode;
    }

    public String getValidateMessage() {
        return validateMessage;
    }

    public void setValidateMessage(String validateMessage) {
        this.validateMessage = validateMessage;
    }

}
