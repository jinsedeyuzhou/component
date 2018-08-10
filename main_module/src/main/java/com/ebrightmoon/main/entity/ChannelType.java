package com.ebrightmoon.main.entity;

import java.io.Serializable;

public class ChannelType implements Serializable {
    public static final String KEY_COURSE_TYPE= "key_course_type";
    //课程ID
    private int id;
    //课程 标题
    private String name;
    //课程类型
    private int type;

    public ChannelType(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
