package io.renren.utils.constant;

public enum ExtraFieldType {

    CLASS("class", "类"),

    STRING("string", "字符串");

    private String name;
    private String value;


    ExtraFieldType(String value, String name) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
