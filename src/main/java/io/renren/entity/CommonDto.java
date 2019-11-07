package io.renren.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.renren.constant.CommonCodeType;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class CommonDto<T> implements Serializable {
    private CommonCodeType code;

    private String message;

    private T data;

    public CommonDto() {
    }

    public CommonDto(CommonCodeType code) {
        this.code = code;
    }

    public CommonDto(CommonCodeType code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonDto(CommonCodeType code, T data) {
        this.code = code;
        this.data = data;
    }

    public CommonDto(CommonCodeType code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @JsonIgnore
    public CommonCodeType getCodeType() {
        return code;
    }

    public int getCode() {
        return code.getCode();
    }

    public CommonDto setCode(CommonCodeType code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return StringUtils.isNotBlank(message) ? message : code.getValue();
    }

    public CommonDto setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public CommonDto setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "CommonDto{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * 返回错误
     *
     * @return CommonDto code=CommonCodeType.FAILURE
     */
    public CommonDto err() {
        return err(null);
    }

    /**
     * 返回错误,携带错误信息
     *
     * @param message 错误信息
     * @return CommonDto code=CommonCodeType.FAILURE
     */
    public CommonDto err(String message) {
        this.setCode(CommonCodeType.FAILURE);
        this.setMessage(message);
        return this;
    }

    /**
     * 返回成功
     *
     * @return CommonDto code=CommonCodeType.SUCCESS
     */
    public CommonDto success() {
        return success(null);
    }

    public CommonDto success(String message) {
        return success(message, null);
    }

    public CommonDto<T> success(String message, T data) {
        this.setCode(CommonCodeType.SUCCESS);
        this.setMessage(message);
        this.setData(data);
        return this;
    }


    /* error - 0*/
    public static CommonDto generateErr(String errMessage) {
        return new CommonDto(CommonCodeType.FAILURE, errMessage);
    }

    /* success - 1*/
    public static CommonDto generateSuccess() {
        return generateSuccess((String) null);
    }

    public static CommonDto generateSuccess(String errMessage) {
        return new CommonDto(CommonCodeType.SUCCESS, errMessage);
    }

    /* manual*/
    public static CommonDto generate(CommonCodeType code) {
        return generate(code, (String) null);
    }

    public static CommonDto generate(CommonCodeType code, String errMessage) {
        return new CommonDto(code, errMessage);
    }
}
