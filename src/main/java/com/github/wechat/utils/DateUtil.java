package com.github.wechat.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private final static SimpleDateFormat sdfDay = new SimpleDateFormat(
            "yyyy-MM-dd");

    private final static SimpleDateFormat sdfDays = new SimpleDateFormat(
            "yyyyMMdd");

    private final static SimpleDateFormat sdfTime = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return sdfYear.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return sdfDay.format(new Date());
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays() {
        return sdfDays.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowTime(String format) {
        SimpleDateFormat sdfTime = new SimpleDateFormat(
                format);
        return sdfTime.format(new Date());
    }

    /**
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @Title: compareDate
     * @Description: TODO(日期比较 ， 如果s > = e 返回true 否则返回false)
     * @author luguosui
     */
    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) == null || fomatDate(e) == null) {
            return false;
        }
        return fomatDate(s).getTime() >= fomatDate(e).getTime();
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 格式化日期
     *
     * @return
     */
    public static Date fomatDateTime(String dateTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return fmt.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 格式化日期
     *
     * @return
     */
    public static String fomat(Date date, String format) {
        DateFormat fmt = new SimpleDateFormat(format);
        return fmt.format(date);
    }

    /**
     * 获取当前时间之前或之后几分钟 minute
     *
     * @return
     */
    public static Date getTimeByMinute(int delay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, delay);
        return calendar.getTime();
    }


    /**
     * 格式化日期
     *
     * @return
     * @throws Exception
     */
    public static String formatDate(String date, String format, String toFormat) throws Exception {
        DateFormat fmt = new SimpleDateFormat(format);
        DateFormat tofmt = new SimpleDateFormat(toFormat);
        return tofmt.format(fmt.parse(date));
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long aa = 0;
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        //System.out.println("相隔的天数="+day);
        return day;
    }

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        return dateStr;
    }


    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(Date beginDate, int days) {
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.setTime(beginDate);
        canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        return dateStr;
    }

    /**
     * 得到n天之后是周几
     *
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDateString(long timeTamp) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeTamp);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static Date stampToDate(long timeTamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeTamp);
        String format = simpleDateFormat.format(date);
        try {
            return simpleDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static int getTimeStampToDay() {
        return (int) (System.currentTimeMillis() / 1000 / (60 * 60 * 24));
    }

    /**
     * 取当前天的日期，转位整形
     *
     * @return
     */
    public static int getDayToInt() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        return Integer.parseInt(date);
    }

    /**
     * 比较时间与当先系统时间的大小
     *
     * @param dataStr
     * @return
     */
    public static int compareWidthCurrentTime(String dataStr) {
        try {
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long timeBegin = fmt.parse(dataStr).getTime();
            return timeBegin > System.currentTimeMillis() ? 1 : -1;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;

    }


    /*** 
     * 日期与月份相加
     *
     */
    public static String dateAddMonth(String datetime, int month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MONTH, month);
        date = cl.getTime();
        return sdf.format(date);
    }


    /**
     * 是否在指定小时区间内
     *
     * @param begin
     * @param end
     * @return
     */
    public static boolean isHourMid(int begin, int end) {
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        if (hour >= begin && hour < end) {
            return true;
        }
        return false;
    }

    public static String get13Timestamp(){
        long timeStampSec = System.currentTimeMillis()/1000;
        String timestamp = String.format("%010d", timeStampSec);
        return timestamp;
    }


        public static void main(String[] args) {
//        System.out.println(getDays());
//        System.out.println(getAfterDayWeek("3"));
//        System.out.println(stampToDate(1558591846000L));
//
//        String createTime = "2019-8-17 11:00:12";
//        System.out.println(getDaySub(createTime, DateUtil.getTime()));

        System.out.println(isHourMid(14,14));

//        String afterDayDate = DateUtil.getAfterDayDate(fomatDate(createTime), 3);
//        System.out.println(afterDayDate);
//        System.out.println(DateUtil.compareWidthCurrentTime(afterDayDate));

            String expiredTime = DateUtil.getAfterDayDate(new Date(), 30);
            System.out.println(expiredTime);
            System.out.println(fomatDateTime(expiredTime));

        }

}
