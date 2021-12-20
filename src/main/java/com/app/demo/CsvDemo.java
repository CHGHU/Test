package com.app.demo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.app.util.CsvUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CsvDemo {

    private static final int TEST_LENGTH = 10;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        String filePath = "D:/下载/CsvDemo.csv";
        BufferedWriter csvWriter = CsvUtil.getFileStream(filePath);

        List<List<Object>> rows = new ArrayList<>();
        for (int i = 1; i <= TEST_LENGTH; i++) {
            List<Object> row = CollUtil.newArrayList("aa" + i, "常用字" + i, "生僻字璟昇赟" + i, "符号？！℃ぁ" + i, DateUtil.date(), 3.22676575765);
            rows.add(row);
        }

        try {
            CsvUtil.writeCsvFile(csvWriter, rows);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CsvUtil.closeFileStream(csvWriter);
        }

        long end = System.currentTimeMillis();
        log.info("总耗时【{}】秒", (end - start) / 1000);
    }

}