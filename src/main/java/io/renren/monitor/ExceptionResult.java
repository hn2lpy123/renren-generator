package io.renren.monitor;

import io.renren.utils.constant.CommonCodeType;

import java.io.Serializable;

/**
 * @author dengshuai
 * @version v1.0
 * Copyright (c) 2019 by dengshuai. All rights reserved.
 * @description
 * @date 2019/10/19 8:25
 */
public class ExceptionResult implements Serializable {

    private int code;

    private String message;

    private long timestamp;

    public ExceptionResult() {
    }

    public ExceptionResult(CommonCodeType codeType) {
        this.code = codeType.getCode();
        this.message = codeType.getValue();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResult(int code, String msg) {
        this.code = code;
        this.message = msg;
        this.timestamp = System.currentTimeMillis();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
