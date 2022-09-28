package com.boardour.comproject

import android.view.View
import cn.carhouse.titlebar.DefTitleBar
import com.base.AppActivity
import com.boardour.comm.RouterHelper

/**
 * 主页面
 */
class MainActivity : AppActivity() {
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initTitle(titleBar: DefTitleBar) {
        titleBar.setTitle("首页")
        titleBar.clearBackImage()
    }


    fun toLogin(view: View) {
        RouterHelper.toRegister(this@MainActivity)
    }

    override fun isFinishActivityAnim(): Boolean {
        return false
    }
}