package com.app.demo;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateDemo {

    public static void main(String[] args) {

        LocalDate today = LocalDate.now();
        System.out.println("今天:\t" + today);

        String yesterday = today.minusDays(1).toString();
        System.out.println("昨天:\t" + yesterday);

        String monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toString();
        String sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toString();
        System.out.println("本周:\t" + monday + " ~ " + sunday);

        Month month = today.getMonth();
        Month firstMonthOfQuarter = month.firstMonthOfQuarter();
        Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        String beginMonth = LocalDate.of(today.getYear(), firstMonthOfQuarter, 1).toString();
        String endMonth = LocalDate.of(today.getYear(), endMonthOfQuarter, endMonthOfQuarter.length(today.isLeapYear())).toString();
        System.out.println("本季:\t" + beginMonth + " ~ " + endMonth);

        String beginYear = today.getYear() + "-01-01";
        String endYear = today.getYear() + "-12-31";
        System.out.println("本年:\t" + beginYear + " ~ " + endYear);

    }

    private static void isLastSunday() {
        LocalDate today = LocalDate.now();
        log.info("today：\t{}", today);

        LocalDate sd1 = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        log.info("sd1：\t{}", sd1);

        String sd1Str = sd1.toString();
        log.info("sd1Str：\t{}", sd1Str);

        LocalDate date = LocalDate.of(2021, 1, 17);
        log.info("date：\t{}", date);

        LocalDate sd2 = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        log.info("sd2：\t{}", sd2);

        LocalDate sd3 = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).minusDays(7);
        log.info("sd3：\t{}", sd3);
    }

    private static void testLocalDateTime1() {
        // 取当前日期：
        LocalDate today = LocalDate.now();
        log.info("today：\t{}", today);

        String str = today.toString().substring(5).replace("-", "/");
        log.info("str：\t{}", str);

        // 根据年月日取日期：
        LocalDate dd1 = LocalDate.of(2015, 8, 3);
        log.info("dd1：\t{}", dd1);

        // 根据字符串取：
        LocalDate date = LocalDate.parse("2021-02-28");
        log.info("date：\t{}", date);

        log.info("before：\t{}", date.isBefore(today));
        log.info("after：\t{}", date.isAfter(today));

        date = date.plusDays(1);
        log.info("date：\t{}", date);

        log.info("before：\t{}", date.isBefore(today));
        log.info("after：\t{}", date.isAfter(today));

        log.info("equls：\t{}", date.isEqual(today));
        log.info("==：\t{}", date == today);
    }

    private static void isSundayOfLocalDateTime(String dateStr) {
        log.info(dateStr);

        LocalDate localDate = LocalDate.parse(dateStr);
        log.info("当前时间：{}", localDate);

        System.out.println("week is " + localDate.getDayOfWeek());
        System.out.println("week is " + localDate.getDayOfWeek().getValue());
    }

    private static void isSundayOfCalendar(String dateStr) {
        log.info(dateStr);
        DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = format1.parse(dateStr);
            log.info(date.toString());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                log.info("YES");
            } else {
                log.error("NO");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void testLocalDateTime2() {

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("当前日期时间:\t" + localDateTime);

        localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(6, 0, 0));
        System.out.println("测试日期时间:\t" + localDateTime.toString());

        System.out.println("测试日期时间:\t" + localDateTime.now().isBefore(localDateTime));
        System.out.println("测试日期时间:\t" + localDateTime.now().isAfter(localDateTime));

        LocalDate localDate = localDateTime.toLocalDate();
        System.out.println("当前日期:\t" + localDate);

        LocalDate localDate2 = LocalDate.now();
        System.out.println("当前日期:\t" + localDate2);

        int year = localDateTime.getYear();
        int imonth = localDateTime.getMonthValue();
        Month month = localDateTime.getMonth();
        int day = localDateTime.getDayOfMonth();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int seconds = localDateTime.getSecond();
        int nano = localDateTime.getNano();
        System.out.println("\n" + year + "-" + imonth + "(" + month + ")-" + day + " " + hour + ":" + minute + ":" + seconds + "." + nano);

        int dayOfYear = localDateTime.getDayOfYear();
        System.out.println("\ndayOfYear: " + dayOfYear);
        int dayOfMonth = localDateTime.getDayOfMonth();
        System.out.println("dayOfMonth: " + dayOfMonth);
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        System.out.println("dayOfWeek: " + dayOfWeek);

        LocalDateTime date2 = localDateTime.withDayOfMonth(10).withYear(2012);
        System.out.println("\ndate2: " + date2);

        // 12 december 2014
        LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);
        System.out.println("date3: " + date3);

        // 22 小时 15 分钟
        LocalTime date4 = LocalTime.of(22, 15);
        System.out.println("date4: " + date4);

        // 解析字符串
        LocalTime date5 = LocalTime.parse("20:15:30");
        System.out.println("date5: " + date5);

        LocalDate date6 = LocalDate.parse("2021-04-01");
        System.out.println("date6: " + date6);

    }

}