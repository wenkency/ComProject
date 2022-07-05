package com.boardour.login

import android.view.View
import cn.carhouse.titlebar.DefTitleBar
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.AppActivity
import com.boardour.comm.RouterHelper
import com.boardour.comm.RouterPath

/**
 * 登录页面
 */
@Route(path = RouterPath.LOGIN)
class LoginActivity : AppActivity() {
    override fun getLayoutId(): Int = R.layout.activity_login
    override fun initTitle(titleBar: DefTitleBar) {
        titleBar.setTitle("登录页面")
    }


    fun toRegister(view: View) {
        RouterHelper.toRegister(this)
    }
}