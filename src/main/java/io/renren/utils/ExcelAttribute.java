package io.renren.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 自定义注解
 *
 * @author dengshuai
 * @version v1.0
 * Copyright (c) 2019 by dengshuai. All rights reserved.
 * @date 2019/10/19 14:18
 * 修饰METHOD只对无参PUBLIC METHOD有效 updated by lipingyu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ExcelAttribute {
    /** 对应的列名称 */
    String name() default "";

    /** 列序号 */
    int sort();

    /** 字段类型对应的格式 */
    String format() default "";
}