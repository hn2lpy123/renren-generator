package io.renren.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static String transferDateFormat(String oldDateStr) {
        if (StringUtils.isBlank(oldDateStr)) {
            return null;
        }
        String newStr = null;
        Date date;
        String dateStr;
        try {
            dateStr = oldDateStr.replace("Z", "");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            date = sdf.parse(dateStr);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            newStr = sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newStr;
    }

    public static Date transferStringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return sdf.parse(dateString);
    }

    public static String transferDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    public static String transferDateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 转换当前时间
     * @param date
     * @return
     */
    public static String transferDateToString2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 根据当前时间获取前一天时间
     * @param date
     * @return
     */
    public static String getBeforDate(Date date) {
        //得到日历
        Calendar calendar = Calendar.getInstance();
        //把当前时间赋给日历
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date dBefore = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(dBefore);
    }

    /**
     * 获取两个日期之间的所有日期
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static List<String> getDays(String startDate, String endDate) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }

    public static boolean isLessThanCurrentTime(String str) {
        String regx = "^([01]\\d|2[01234]):([0-5]\\d|60)$";
        if (!str.matches(regx)) {
            throw new RuntimeException("时间格式不正确（HH:mm）");
        }
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String currentTime = format.format(new Date());
        return str.compareTo(currentTime) < 0;
    }


    public static void main(String[] args) throws ParseException {
        String date = null;
        if (DateUtil.isLessThanCurrentTime("09:30")) {
            date = DateUtil.transferDateToString2(new Date());
        } else {
            date = DateUtil.getBeforDate(new Date());
        }
        System.out.println(date);
    }

}
