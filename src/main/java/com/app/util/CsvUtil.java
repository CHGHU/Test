package com.app.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

public class CsvUtil {

    public static BufferedWriter getFileStream(String filePath) {
        BufferedWriter csvWriter = null;

        try {
            File csvFile = new File(filePath);
            File parent = csvFile.getParentFile();
            if (parent == null || !parent.exists()) {
                parent.mkdirs();
            }
            if (csvFile.exists()) {
                csvFile.delete();
            }
            csvFile.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(csvFile);
            fileOutputStream.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
            csvWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"), 1024);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvWriter;
    }

    public static void closeFileStream(BufferedWriter csvWriter) {
        try {
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeCsvFile(BufferedWriter csvWriter, List<List<Object>> dataList) throws IOException {
        if (dataList != null && !dataList.isEmpty()) {
            for (List<Object> row : dataList) {
                writeRow(csvWriter, row);
            }
        }
        csvWriter.flush();
    }

    public static void writeRow(BufferedWriter csvWriter, List<Object> row) throws IOException {
        for (Iterator<Object> iterator = row.iterator(); iterator.hasNext(); ) {
            Object data = iterator.next();
            String rowStr = "\"" + data + "\",";
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }

    public static void deleteCsvFile(String filePath) {
        try {
            File csvFile = new File(filePath);
            if (csvFile.exists()) {
                csvFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}