package com.ebrightmoon.ui.page.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Time: 2019-10-15
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
        long addresult=add(100,20);
        System.out.println(addresult);
    }

    private long add(int x,int y)
    {
        return x+y;
    }
    private void test()
    {
        List<String> strings=new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.add("5");

        for (String str:strings)
        {
            System.out.println(str);
        }
    }

    public void debug() {
        this.name = "debug";
        System.out.println(name);

    }

    public static void main(String[] args) {
        new DebugDemo().testName();
        System.out.println("main");
    }


}

