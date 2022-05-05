package com.app.demo;

import lombok.extern.slf4j.Slf4j;

import java.awt.Robot;

@Slf4j
public class RobotDemo {

    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            int i = 450;
            while (true) {
                if (i == 500) {
                    i = 450;
                }
                log.info(String.valueOf(i));
                robot.mouseMove(i * 2, i);
                i += 10;
                Thread.sleep(100 * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}