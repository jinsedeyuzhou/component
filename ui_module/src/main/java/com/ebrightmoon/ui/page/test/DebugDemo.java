package com.ebrightmoon.ui.page.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Time: 2019-10-30
 * Author:wyy
 * Description:
 */
public class DebugDemo {
    private String name = "default";
    private String password=null;
    public void testName() {
        int num = 10;
        double min = Math.sqrt(num);
        System.out.println(min);
        debug();
        test();
    }
    private void test()
    {
        List<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        for (String str:list)
        {
            System.out.println(str);
        }
    }
    public void debug() {
        this.name = "debug";
        System.out.println(name);
//        password.substring(0,10);
    }
    public static void main(String[] args) {
        new DebugDemo().testName();
        System.out.println("main");
    }


}
