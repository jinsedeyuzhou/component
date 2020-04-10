package com.ebrightmoon.ui.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.ebrightmoon.ui.BR;

import java.io.Serializable;

/**
 * Time: 2019/6/21
 * Author:wyy
 * Description:
 */
public class UserInfo extends BaseObservable implements Serializable {
    @Bindable
    public String firstName;
    private String lastName;
    private int age;


    public UserInfo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyChange();
//        notifyPropertyChanged(BR.userInfo);
    }
}
