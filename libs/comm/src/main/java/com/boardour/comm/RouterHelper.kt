package com.boardour.comm

import android.app.Activity
import com.alibaba.android.arouter.launcher.ARouter
import com.boardour.comm.ft_login.LoginProvider

/**
 * 这个是跳转帮助类
 */
object RouterHelper {

    /**
     * 跳转到登录页面
     */
    fun toLogin(activity: Activity) {
        ARouter.getInstance()
            .build(RouterPath.LOGIN)
            .withString("userName", LoginProvider.getInstance().loginProvider?.userName())
            .navigation(activity)
    }

    fun toRegister(activity: Activity) {
        ARouter.getInstance()
            .build(RouterPath.REGISTER)
            .navigation(activity)
    }
}