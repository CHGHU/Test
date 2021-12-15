package com.app.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
public class FutureDemo {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        log.info("开始时间: {}", start);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(() -> {
            try {
                log.info("任务时停开始");
                Thread.sleep(5000);
                log.info("任务时停结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long now = System.currentTimeMillis();
            log.info("线程时间: {}s", (now - start) / 1000);
        });

        try {
            future.get(3, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            log.info("主线程在等待返回结果时中断！");
        } catch (ExecutionException e) {
            log.info("主线程在等待返回结果，出现异常中断！");
        } catch (TimeoutException e) {
            log.info("主线程在等待返回结果，任务超时中断！");

        } finally {
            executor.shutdownNow();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();
        log.info("结束时间: {}s", (end - start) / 1000);
    }

}