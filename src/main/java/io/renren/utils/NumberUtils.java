package io.renren.utils;

import java.text.DecimalFormat;

/**
 * @ClassName NumberUtils
 * @Description 数值计算工具类
 * @Author lipingyu
 * @Date 2019/10/22 10:22
 * @Version 1.0
 */
public class NumberUtils {

    /**
     * 计算两个number1/number2的值并转化成百分比
     * @param number1
     * @param number2
     * @return
     */
    public static String transfer2Percent(Double number1, Double number2) {
        if (number2.doubleValue() == 0) {
            return "0.00%";
        }
        return new DecimalFormat("0.00%").format((double)number1/number2);
    }

    public static String transfer2Percent(Integer number1, Integer number2) {
        if (number2.intValue() == 0) {
            return "0.00%";
        }
        return new DecimalFormat("0.00%").format((double)number1/number2);
    }

    public static void main(String[] args) {
        System.out.println(transfer2Percent(1, 2));
    }
}
