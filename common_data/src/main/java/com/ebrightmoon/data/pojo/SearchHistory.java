package com.ebrightmoon.data.pojo;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：create by  Administrator on 2018/9/12
 * 邮箱：
 */
@Entity
public class SearchHistory {
    @Id
    private Long id;

    private String name;

    @Generated(hash = 822577210)
    public SearchHistory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
