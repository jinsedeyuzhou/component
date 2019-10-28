package com.ebrightmoon.ui.page.kotlin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ebrightmoon.common.base.BaseActivity
import com.ebrightmoon.ui.R
import kotlinx.android.synthetic.main.activity_kotlin_test.*

/**
 * Time: 2019-08-15
 * Author:wyy
 * Description:
 *
 */
class KotlinTestActivity : BaseActivity() {
    private var str:String="dsaff"

    override fun onCreate(paramBundle: Bundle?) {
        super.onCreate(paramBundle)
        setContentView(R.layout.activity_kotlin_test)
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun bindEvent() {
        btn_kotlin.setOnClickListener {
            Toast.makeText(mContext,"kotlin",Toast.LENGTH_LONG).show()
        }
    }

    override fun processClick(paramView: View?) {
    }
}