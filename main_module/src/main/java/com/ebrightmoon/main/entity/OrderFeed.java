package com.ebrightmoon.main.entity;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

public class OrderFeed {
    @Id
    private Long id;

    @Generated(hash = 812990575)
    public OrderFeed(Long id) {
        this.id = id;
    }

    @Generated(hash = 1710357807)
    public OrderFeed() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
