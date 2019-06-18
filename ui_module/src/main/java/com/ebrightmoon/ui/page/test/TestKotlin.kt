package com.ebrightmoon.ui.page.test

import android.util.Log
import android.util.Log.println

/**
 * Time: 2019/6/17
 * Author:wyy
 * Description:
 *
 */
class TestKotlin {

    fun test():Unit
    {
        val items = listOf("apple", "banana", "kiwifruit")
        for (index in items.indices) {
           println(items[index])
        }
    }
}