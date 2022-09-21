package com.boardour.comproject

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.base.BaseConfig
import com.boardour.comm.AppDialog
import com.retrofit.config.RestConfig

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
        // 1. 项目通用Dialog配置
        BaseConfig.dialog = AppDialog()
        // 2. 网络初始化
        RestConfig.baseUrl("http://httpbin.org/")
            .debugUrl("http://httpbin.org/")
            .register(this)
    }
}