package com.ebrightmoon.data.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class NewsFeed {
    @Id
    private int id;

    @Generated(hash = 1734658965)
    public NewsFeed(int id) {
        this.id = id;
    }

    @Generated(hash = 628574352)
    public NewsFeed() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
