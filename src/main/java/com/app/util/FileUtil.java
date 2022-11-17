package com.app.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Slf4j
public class FileUtil {

    public static String readFile(String filePath) {
        StringBuffer sb = new StringBuffer();

        File file = new File(filePath);
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

        } catch (Exception e) {
            log.error("FileUtil readFile ERROR!", e);
        }

        return sb.toString();
    }

}