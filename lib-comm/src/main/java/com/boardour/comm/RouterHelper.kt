package com.boardour.comm

import android.app.Activity
import com.alibaba.android.arouter.launcher.ARouter
import com.boardour.comm.ft_login.LoginProvider

/**
 * 这个是跳转帮助类
 */
object RouterHelper {
    /**
     * 收藏
     */
    fun toCollect(activity: Activity){
        ARouter.getInstance()
            .build(RouterPath.COLLECT)
            .navigation(activity)
    }
    /**
     * 跳转到登录页面
     */
    fun toLogin(activity: Activity) {
        ARouter.getInstance()
            .build(RouterPath.LOGIN)
            //.withString("userName", LoginProvider.loginProvider?.userName())
            .navigation(activity)
    }

    /**
     * 跳转到注册页面
     */
    fun toRegister(activity: Activity) {
        ARouter.getInstance()
            .build(RouterPath.REGISTER)
            .navigation(activity)
    }

    /**
     * 跳转到Web页面
     */
    fun toWeb(activity: Activity, url: String, title: String = "") {
        ARouter.getInstance()
            .build(RouterPath.WEB)
            .withString("url", url)
            .withString("title", title)
            .navigation(activity)
    }

}