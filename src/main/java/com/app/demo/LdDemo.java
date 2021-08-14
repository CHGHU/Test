package com.app.demo;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class LdDemo {

    public static void main(String[] args) throws Exception {
    }

    public static void testBigDecimal() {
        BigDecimal orderAmount = new BigDecimal("15");
        BigDecimal subtractAmount = new BigDecimal("15");
        BigDecimal increaseAmount = new BigDecimal("3");
        int result = orderAmount.subtract(subtractAmount).divideAndRemainder(increaseAmount)[1].compareTo(new BigDecimal(0));
        boolean flag = (orderAmount.subtract(subtractAmount).divideAndRemainder(increaseAmount)[1].compareTo(new BigDecimal(0)) == 0);
        System.out.println(orderAmount);
        System.out.println(subtractAmount);
        System.out.println(increaseAmount);
        System.out.println(result);
        System.out.println(flag);
    }

    private static void testSortBigDecimal() {
        List<BigDecimal> productSubsections = new ArrayList<>();

        productSubsections.forEach(item -> System.out.println(item));
        System.out.println();

        productSubsections.sort((p1, p2) -> p2.compareTo(p1));

        productSubsections.forEach(item -> System.out.println(item));
        System.out.println();
    }

    private static void testStr() {
        String s = "abc.def|ghi$jk";
        String[] a = s.split("\\.|\\||\\$");
        for (String t : a) {
            System.out.println(t);
        }

        String soo = ",1,21,131,S1109";
        System.out.println(soo);

        String s1 = "测试中文";
        System.out.println(s1 + ": " + s1.length());
        String s2 = "TEST";
        System.out.println(s2 + ": " + s2.length());

        System.out.println();
        BigDecimal b1 = new BigDecimal("23");
        System.out.println(b1);
        BigDecimal b2 = new BigDecimal("23.00");
        System.out.println(b2);
        System.out.println(b1.compareTo(b2));

        System.out.println();
        String bs1 = b1.toString();
        System.out.println(bs1);
        String bs2 = b2.toString();
        System.out.println(bs2);
    }

    private static void testRegex() {
        // String regex = "^[1-9]+[0-9]*$";
        // String regex = "-?[0-9]+.?[0-9]+";
        // String regex = "-?[0-9]+.?[0-9]+";
        String regex = "(-?[1-9]\\d*\\.?\\d+)|(-?0\\.\\d*[1-9])|(\\d+)";

        // boolean nnn1 = Pattern.compile(regex).matcher(null).matches();
        // System.out.println("null\t" + nnn1);
        boolean nnn2 = Pattern.compile(regex).matcher("").matches();
        System.out.println("\"\"\t" + nnn2);

        boolean n101 = Pattern.compile(regex).matcher("3").matches();
        System.out.println("3\t" + n101);
        boolean n102 = Pattern.compile(regex).matcher("13").matches();
        System.out.println("13\t" + n102);

        boolean n103 = Pattern.compile(regex).matcher("23").matches();
        System.out.println("23\t" + n103);
        boolean n104 = Pattern.compile(regex).matcher("23.00").matches();
        System.out.println("23.00\t" + n104);

        boolean n1 = Pattern.compile(regex).matcher("123456.98").matches();
        System.out.println("123456.98\t" + n1);
        boolean n2 = Pattern.compile(regex).matcher("123456.0").matches();
        System.out.println("123456.0\t" + n2);
        boolean n3 = Pattern.compile(regex).matcher("-123456.98").matches();
        System.out.println("-123456.98\t" + n3);
        boolean n4 = Pattern.compile(regex).matcher("0.98").matches();
        System.out.println("0.98\t" + n4);
        boolean n5 = Pattern.compile(regex).matcher(".98").matches();
        System.out.println(".98\t" + n5);
        boolean n6 = Pattern.compile(regex).matcher("-.98").matches();
        System.out.println("-.98\t" + n6);
        boolean n7 = Pattern.compile(regex).matcher("1234").matches();
        System.out.println("1234\t" + n7);

        boolean n11 = Pattern.compile(regex).matcher("0").matches();
        System.out.println("0\t" + n11);
        boolean n12 = Pattern.compile(regex).matcher("0.").matches();
        System.out.println("0.\t" + n12);
        boolean n13 = Pattern.compile(regex).matcher("0.00").matches();
        System.out.println("0.00\t" + n13);

        boolean n901 = Pattern.compile(regex).matcher("in0").matches();
        System.out.println("in0\t" + n901);
    }

    private static Long getDiffDays(Date startDate, Date endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start = sdf.format(startDate);
        String end = sdf.format(endDate);
        startDate = sdf.parse(start);
        endDate = sdf.parse(end);
        return startDate.getTime() - endDate.getTime();
    }

}