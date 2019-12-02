package io.renren.entity;

import io.renren.utils.constant.ExtraFieldType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ExtraField implements Serializable {

    public static final String CLASS_SUFFIX = "ClassName";

    private String extraFieldType;

    private String extraFieldName;

    private String extraFieldValue;

    public ExtraField() {
    }

    public ExtraField(String extraFieldType, String extraFieldName, String extraFieldValue) {
        this.extraFieldType = extraFieldType;
        this.extraFieldName = extraFieldName;
        this.extraFieldValue = extraFieldValue;
    }

    public Map<String, Object> convertMap() {
        Map<String, Object> extraFieldMap = new HashMap<>();
        extraFieldMap.put(extraFieldName, extraFieldValue);
        if (extraFieldType.equals(ExtraFieldType.CLASS.getValue()) && extraFieldValue.contains(".")) {
            extraFieldMap.put(extraFieldName + CLASS_SUFFIX, extraFieldValue.substring(extraFieldValue.lastIndexOf(".")));
        }
        return extraFieldMap;
    }

    public String getExtraFieldType() {
        return extraFieldType;
    }

    public void setExtraFieldType(String extraFieldType) {
        this.extraFieldType = extraFieldType;
    }

    public String getExtraFieldName() {
        return extraFieldName;
    }

    public void setExtraFieldName(String extraFieldName) {
        this.extraFieldName = extraFieldName;
    }

    public String getExtraFieldValue() {
        return extraFieldValue;
    }

    public void setExtraFieldValue(String extraFieldValue) {
        this.extraFieldValue = extraFieldValue;
    }
}
