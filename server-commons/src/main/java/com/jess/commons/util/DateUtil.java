package com.jess.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期工具类
 * Created by zhongxuexi on 2018/7/22.
 */
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
    public static String getDays(){
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
     * @Title: compareDate
     * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @author luguosui
     */
    public static boolean compareDate(String s, String e) {
        if(fomatDate(s)==null||fomatDate(e)==null){
            return false;
        }
        return fomatDate(s).getTime() >=fomatDate(e).getTime();
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
    public static int getDiffYear(String startTime,String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }
    /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate= format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        //System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * 得到n天之后的日期
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
     * 得到n天之后是周几
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
     * 获取n个月后的日期
     */
    public static String getAfterMonthDate(String month) throws Exception {
        int daysInt = Integer.parseInt(month);

        //Calendar canlendar = Calendar.getInstance(); // java.util包
        Calendar canlendar = Calendar.getInstance(); // java.util包
        //canlendar.setTime(sdfTime.parse("1994-1-30 12:12:25"));
        canlendar.add(Calendar.MONTH, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }


    /**
     n个月后的今天
     */
    public static Date getAfterMonthBao(Date sj,int daysInt) throws Exception {
        //Calendar canlendar = Calendar.getInstance(); // java.util包
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.setTime(sj);
        canlendar.add(Calendar.MONTH, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        return date;
    }

    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDay(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 获取当前时间 精确到小时
     * @return
     */
    public static String getPlanNumber()
    {
        //SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = sdfd.format(new Date());
        String rs=dateStr.substring(2,dateStr.length()-6);
        return rs;
    }

    /**
     * 格式化时间输出,短时间
     * @param date
     * @return
     */
    public static String getDateWo(Date date){
        return sdfDay.format(date);
    }


    /**
     * 得到n天之后的日期的时间戳,短时间
     * @param days
     * @return  返回秒数
     */
    public static int getAfterDayInt(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfd.format(date);

        return (int)(fomatDate(dateStr).getTime()/1000);
    }

    /**
     * 得到n天之后的日期的时间戳,长时间
     * @return  返回秒数
     */
    public static int getToDayLong() {
        return (int)(new Date().getTime()/1000);
    }


    /*
     * 精确到天的秒数
     */
    public static int getAddTimeInt(long t) {
        try {
            Date date=new Date(t*1000);
            String now=sdfDay.format(date);
            return (int)(sdfDay.parse(now).getTime()/1000);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 格式化日期
     *
     * @return  获得长时间精确到秒
     */
    public static Date fomatDateLong(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前小时
     * @return
     */
    public static int getHour()
    {
        Calendar cal = Calendar.getInstance();
        //int year = cal.get(Calendar.YEAR);//获取年份
        //int month=cal.get(Calendar.MONTH);//获取月份
        //int day=cal.get(Calendar.DATE);//获取日
        int hour=cal.get(Calendar.HOUR_OF_DAY);//小时
        //int minute=cal.get(Calendar.MINUTE);//分
        //int second=cal.get(Calendar.SECOND);//秒
        //int dayofweek = cal.get(Calendar.DAY_OF_WEEK);//一周的第几天
        //if(1==1)
        //return false;
        //if(hour<9 || (hour >= 12 && hour <13 )  || hour >16)
        // {
        //     return true;
        // }

        return hour;
    }

    /*
     * 获取n个月后的日期--精确到天
     */
    public static String getAfterMonthDay(String month) throws Exception {
        int daysInt = Integer.parseInt(month);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.MONTH, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        String dateStr = sdfDay.format(date);
        return dateStr;
    }

    /**
     * 获取今天日期 yyyy-MM-dd
     * @return
     */
    public static Date getNow(){
        return fomatDate(DateUtil.getDateWo(new Date()));
    }

    /*
     * 将时间戳转换为字符串类型  yyyy年MM月dd
     */
    public static String getDateStr(long time){
        Date date=new Date(time);
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy年MM月dd");
        return sdfDay.format(date);
    }


    /**
     * 得到n天之后的日期
     * @param days
     * @return
     * @throws Exception
     */
    public static Date getBeforeDayDate(int days) throws Exception {
        int daysInt = days;
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfd.format(date);
        Date dateNow=sdfDay.parse(dateStr);
        return dateNow;
    }
    /*
     * 取整天的时间戳范围,精确到天
     */
    public static int getDateToDayOneInt(){
        String str=getDay();
        Date dat=fomatDate(str);
        return (int)(dat.getTime()/1000);
    }

    /**
     * 精确到某一个的时间戳   yyyy-MM-dd
     * @param date
     * @return
     */
    public static int StrDateToInt(String date){
        try {
            return (int)(sdfDay.parse(date).getTime()/1000);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取前几个月前几天的日期详情
     * @param month 月数
     * @param day  天数   + 往后   -往前
     * @return  日期时间  yyyy-mm-dd
     * @throws Exception
     */
    public static Date getAfterMonthDay(int month,int day) throws Exception {
        //Calendar canlendar = Calendar.getInstance(); // java.util包
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DAY_OF_MONTH, day);
        canlendar.add(Calendar.MONTH, month); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        return fomatDate(sdfDay.format(date));
    }

    /**
     * 获取前几个月前几天的日期详情
     * @param month 月数
     * @param day  天数   + 往后   -往前
     * @return  日期时间  yyyy-mm-dd
     * @throws Exception
     */
    public static Date getAfterMonthDay(Date datePlan,int month,int day) throws Exception {
        //Calendar canlendar = Calendar.getInstance(); // java.util包
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.setTime(datePlan);
        canlendar.add(Calendar.DAY_OF_MONTH, day);
        canlendar.add(Calendar.MONTH, month); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        return fomatDate(sdfDay.format(date));
    }

    /**
     * 时间戳转换为时间字符串
     * @param sec
     * @return
     */
    public static String int2LongDate(long sec){
        Date date=new Date(sec*1000);
        return sdfTime.format(date);
    }

    /**
     * 获取本月的第一天
     * @return
     */
    public static String getTheMonthFirstDay(){
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH,1);
        String first = sdfDay.format(c.getTime());
        System.out.println("===============first:"+first);
        return first;
    }


    /**
     * 获取本月的最后一天
     * @return
     */
    public static String getTheMonthLastDay(){

        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH,
                ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = sdfDay.format(ca.getTime());
        System.out.println("===============last:"+last);
        return last;
    }

    /*
     * 本月+1
     */
    public static String toDayAddOneMonth() throws Exception{
        return sdfDay.format(getAfterMonthBao(fomatDate(getTheMonthFirstDay()), 1));
    }


    /**
     * 获取date+time合成之后的时间的时间戳
     * @param planDate
     * @param time
     * @return
     * @throws Exception
     */
    public static int planDateAndDayTime(Date planDate,Date time) throws Exception{
        String plan=sdfTime.format(planDate);
        String day=sdfTime.format(time);

        String start=plan.substring(0,day.indexOf(" "));
        String end=day.substring(day.indexOf(" ")+1);

        int ms=(int)(sdfTime.parse(start+" "+end).getTime()/1000);
        return ms;

    }

    /*
     * 设置指定小时的时间
     */
    public static String sheHourTime(int hour){
        Calendar c = Calendar.getInstance();
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        String first = sdfTime.format(c.getTime());
        return first;
    }

    /**
     * 两个日期之间的天数差
     * @param start
     * @param end
     * @return
     */
    public static int getDaySubInt(long start,long end){
        String str_begin=sdfDay.format(new Date(start*1000));
        String str_end=sdfDay.format(new Date(end*1000));
        return (int) getDaySub(str_begin,str_end);
    }

    //时间格式化 yyyy-MM-dd
    public static String Date2Str(Date date){
        return sdfDay.format(date);
    }

    /**
     * 判断传递过来的日期是否是当天
     *
     * @param date
     * @return
     */
    public static boolean isCurrentDay(Date date) {
        try {
            String dateStr = sdfDay.format(date);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d = format.parse(dateStr);
            return getNow().compareTo(d) == 0;
        } catch (Exception e) {
            return false;
        }
    }


    /*
    获取格式化时间
     */
    public static String getDateStr(String dateTime){
        String toDay="";
        SimpleDateFormat geshi = new SimpleDateFormat("yyyy年MM月dd");
        try {
            toDay=geshi.format(sdfDay.parse(dateTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return toDay;
    }

    /*
    2016年05月03 10:32
     */
    public static String getDateStrMo(){
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy年MM月dd HH:mm");
        return sdf.format(new Date());

    }

    public static int addYear(int daysInt){
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.YEAR, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdfd.format(date);
        return DateUtil.StrDateToInt(dateStr);
    }

    public static int getAfterMonthDayInt(Date datePlan,int month,int day) throws Exception {
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.setTime(datePlan);
        canlendar.add(Calendar.DAY_OF_MONTH, day);
        canlendar.add(Calendar.MONTH, month); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        return (int)(date.getTime()/1000);
    }

    public static String dateToLLPayFormat(String s) {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss",
                Locale.getDefault());
        if (s.length() == 10) {
            return dataFormat.format(new Date(Long.valueOf(s + "000")));
        } else {
            return dataFormat.format(new Date(Long.valueOf(s + "000")));
        }
    }

    public static String changeTimeZone(long time, String format) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);//yyyyMMddHHmmss
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String dateStrTmp = dateFormat.format(date);
        return dateStrTmp;
    }

}
