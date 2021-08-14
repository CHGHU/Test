package com.app.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

public class MaHaoRanTest {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("请输入字符串：");
        String inputString = sc.next();
        System.out.println("输入字符串：" + inputString);

        test11(inputString);

        System.out.println("\n--------------------------------------------------------------------------------------------------\n");

        System.out.print("请输入字符串：");
        inputString = sc.next();
        System.out.println("输入字符串：" + inputString);

        test2(inputString);

        System.out.println("\n--------------------------------------------------------------------------------------------------\n");

        System.out.print("请输入字符串：");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        inputString = br.readLine();
        System.out.println("输入字符串：" + inputString);

        test3(inputString);
    }

    public static void test11(String inputString) {
        int result = 0;

        int inputSize = inputString.length();

        for (int i = 0; i < inputSize - 1; i++) {
            for (int j = 0; j <= i; j++) {
                String tempString = inputString.substring(j, inputSize - i + j);
                System.out.print("\n\n===>>> " + tempString);

                result += test12(tempString);
            }
        }

        System.out.println("\nresult: " + result);
    }

    public static int test12(String inputString) {
        int result = 0;

        int half1 = inputString.length() % 2 == 0 ? inputString.length() / 2 : (inputString.length() - 1) / 2;
        int half2 = inputString.length() % 2 == 0 ? inputString.length() / 2 : (inputString.length() + 1) / 2;

        String front = inputString.substring(0, half1);
        String behind = inputString.substring(half2);
        System.out.print("\n===>>>\t" + front + "\t|\t" + behind);

        if (front.equals(new StringBuffer(behind).reverse().toString())) {
            System.out.print("\t✔");
            result = 1;
        }

        System.out.println();
        return result;
    }

    public static void test1(String inputString) {
        int result = 0;

        int size = 0;
        int length = inputString.length();

        if (length % 2 == 0) {
            size = length / 2;

        } else {
            size = (length - 1) / 2;
            String front = inputString.substring(0, size);
            String behind = inputString.substring(size + 1);
            System.out.print("\n===>>>\t" + front + "\t|\t" + behind);

            if (front.equals(new StringBuffer(behind).reverse().toString())) {
                System.out.print("\t✔");
                result++;
            }
            System.out.println();
        }

        for (int i = 1; i <= size; i++) {
            String frontString = inputString.substring(0, i);
            String behindString = inputString.substring(i);
            System.out.println("\n===>>>\t" + frontString + "\t|\t" + behindString);

            for (int j = 0; j < i; j++) {
                String front = inputString.substring(j, i);
                String behind = inputString.substring(i, i * 2 - j);
                System.out.print(front + "\t" + behind);

                if (front.equals(new StringBuffer(behind).reverse().toString())) {
                    System.out.print("\t✔");
                    result++;
                }
                System.out.println();
            }
        }

        for (int i = size; i < length - 1; i++) {
            String frontString = inputString.substring(i * 2 - length + 2, i + 1);
            String behindString = inputString.substring(i + 1);
            System.out.println("\n===>>>\t" + frontString + "\t|\t" + behindString);

            for (int j = length - i - 1; j > 0; j--) {
                String front = inputString.substring(i - j + 1, i + 1);
                String behind = inputString.substring(i + 1, i + j + 1);
                System.out.print(front + "\t" + behind);

                if (front.equals(new StringBuffer(behind).reverse().toString())) {
                    System.out.print("\t✔");
                    result++;
                }
                System.out.println();
            }

        }

        System.out.println("\nresult: " + result);
    }

    public static void test2(String inputString) {
        int result = 1;

        int inputSize = inputString.length();

        test: for (int i = 0; i < inputSize - 1; i++) {
            for (int j = 0; j <= i; j++) {
                String tempString = inputString.substring(j, inputSize - i + j);
                System.out.print("===>>> " + tempString);

                int length = test21(tempString);
                if (length > result) {
                    result = length;
                    break test;
                }
            }
        }

        System.out.println("\nresult: " + result);
    }

    public static int test21(String inputString) {
        int result = inputString.length();

        String[] inputArray = inputString.split("");

        test: for (int i = 0; i < inputArray.length; i++) {
            for (int j = 0; j < inputArray.length; j++) {
                if (i != j && inputArray[i].equals(inputArray[j])) {
                    result = 1;
                    break test;
                }
            }
        }

        if (result != 1) {
            System.out.print("\t✔");
        }
        System.out.println();

        return result;
    }

    public static void test3(String inputString) {
        String result = "";

        for (int i = inputString.length() - 1; i >= 0; i--) {
            if (inputString.endsWith(".")) {
                inputString = inputString.substring(0, inputString.length() - 1);
            } else {
                break;
            }
        }

        String[] inputArray = inputString.split(" ");

        for (int i = 0; i < inputArray.length; i++) {
            if (StringUtils.isNotBlank(inputArray[i])) {
                result += new StringBuffer(inputArray[i]).reverse().toString() + " ";
            }
        }

        result = result.substring(0, result.length() - 1) + ".";
        System.out.println("\n" + result);
    }

}