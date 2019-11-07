package io.renren.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName StringUtils
 * @Description 字符串工具类
 * @Author lipingyu
 * @Date 2019/10/23 11:15
 * @Version 1.0
 */
public class StringUtils {
    /**
     * 判断字符串中是否包含中文
     * @param str
     * 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否是正确的日期格式
     * @param str
     * 待校验字符串
     * @return 是否是正确的日期格式
     */
    public static boolean isVaildDateFromat(String str) {
        String reg = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])" +
                "|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))" +
                "[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]" +
                "((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        return Pattern.matches(reg, str);
    }

    public static void main(String[] args) {
        System.out.println(isVaildDateFromat("2019-88-99"));
    }
}
