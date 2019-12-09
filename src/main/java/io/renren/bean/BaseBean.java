package io.renren.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

public class BaseBean implements Serializable {

    public String toString() {
        try {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        } catch (Exception var2) {
            var2.printStackTrace();
            return super.toString();
        }
    }
}
