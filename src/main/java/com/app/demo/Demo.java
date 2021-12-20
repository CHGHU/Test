package com.app.demo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Demo {

    public static void main(String[] args) {

        List<List<?>> rows = new ArrayList<>();

        for (int i = 1; i <= 2700000; i++) {
            log.info("{}", i);
            List<?> row = CollUtil.newArrayList("A" + 1, "常用字" + 1, "生僻字璟昇赟" + 1, "符号？！℃ぁ" + 1, DateUtil.date(), 3.22676575765);
            rows.add(row);
        }

        List<List<List<?>>> rowsList = ListUtils.partition(rows, 1000000);
        for (int i = 0; i < rowsList.size(); i++) {
            log.info("{}", rowsList.get(i).size());
        }

    }

}