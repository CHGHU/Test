package com.app.demo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelDemo {

    private static final int TEST_LENGTH = 3000000;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        int sheet = 1;
        BigExcelWriter writer = ExcelUtil.getBigWriter("D:/ExcelDemo.xlsx");
        writerColumn(writer, sheet);

        for (int i = 0; i < TEST_LENGTH / 5; i++) {
            log.info("{}", i * 5 + 1);
            if (i > (1000000 / 5 * sheet)) {
                writer.setSheet("sheet" + ++sheet);
                writerColumn(writer, sheet);
            }

            List<?> row2 = CollUtil.newArrayList("aa1", "常用字1", "生僻字璟昇赟1", "符号？！℃ぁ1", DateUtil.date(), 250.7676);
            List<?> row3 = CollUtil.newArrayList("aa2", "常用字2", "生僻字璟昇赟2", "符号？！℃ぁ2", DateUtil.date(), 0.111);
            List<?> row4 = CollUtil.newArrayList("aa3", "常用字3", "生僻字璟昇赟3", "符号？！℃ぁ3", DateUtil.date(), 35);
            List<?> row5 = CollUtil.newArrayList("aa4", "常用字4", "生僻字璟昇赟4", "符号？！℃ぁ4", DateUtil.date(), 28.00);
            List<?> row1 = CollUtil.newArrayList("aa5", "常用字5", "生僻字璟昇赟5", "符号？！℃ぁ5", DateUtil.date(), 3.22676575765);
            List<List<?>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);

            writer.write(rows);
        }

        writer.autoSizeColumnAll();
        writer.close();

        long end = System.currentTimeMillis();
        log.info("总耗时【{}】秒", (end - start) / 1000);
    }

    private static void writerColumn(BigExcelWriter writer, int sheet) {
        List<?> columnRow = CollUtil.newArrayList("标题A" + sheet, "标题B" + sheet, "标题C" + sheet, "标题D" + sheet, "标题E" + sheet, "标题F" + sheet);
        List<List<?>> columnRows = new ArrayList<>();
        columnRows.add(columnRow);

        writer.write(columnRows);
    }

}