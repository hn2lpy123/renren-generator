package io.renren.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.renren.utils.constant.ExtraFieldType;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ContentRowHeight(12)
@HeadRowHeight(24)
public class ExtraField implements Serializable {

    @ExcelIgnore
    public static final String CLASS_SUFFIX = "ClassName";

    @NotBlank(message = "extraFieldType不能为空")
    @ExcelProperty(index = 0, value = "ExtraFieldType")
    @ColumnWidth(20)
    private String extraFieldType;

    @NotBlank(message = "extraFieldName不能为空")
    @ExcelProperty(index = 1, value = "ExtraFieldName")
    @ColumnWidth(25)
    private String extraFieldName;

    @NotBlank(message = "extraFieldValue不能为空")
    @ExcelProperty(index = 2, value = "ExtraFieldValue")
    @ColumnWidth(35)
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

    @Override
    public String toString() {
        return "ExtraField{" +
                "extraFieldType='" + extraFieldType + '\'' +
                ", extraFieldName='" + extraFieldName + '\'' +
                ", extraFieldValue='" + extraFieldValue + '\'' +
                '}';
    }
}
