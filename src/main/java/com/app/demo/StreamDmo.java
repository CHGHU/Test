package com.app.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class StreamDmo {

    public static void main(String[] args) {

        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);

        Optional<Map.Entry<String, Integer>> max = map.entrySet().stream().max(Map.Entry.comparingByValue());
        log.info("{}", max.toString());

        int max2 = Collections.max(map.values());
        log.info("{}", max2);
    }

}