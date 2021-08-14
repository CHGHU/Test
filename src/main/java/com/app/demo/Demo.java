package com.app.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class Demo {

    public static void main(String[] args) {
        String path = "D:\\Library\\workspace\\GitHub\\aaa.iml";
        int tail = path.lastIndexOf(File.separator);
        log.info("==>> " + tail);

        path = path.substring(0, tail);
        log.info("==>> " + path);
    }

}