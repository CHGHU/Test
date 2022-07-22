package com.app.vo;

import lombok.Data;

@Data
public class User {

    //姓名
    private String name;

    //年龄
    private Integer age;

    //描述
    private String disc;

    //画像
    private String[] label;

    public User(String name, Integer age, String disc, String[] label) {
        this.name = name;
        this.age = age;
        this.disc = disc;
        this.label = label;
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

}