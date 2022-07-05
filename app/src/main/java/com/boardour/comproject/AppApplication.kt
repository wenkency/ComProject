package com.boardour.comproject

import android.app.Application
import com.alibaba.android.arouter.BuildConfig
import com.alibaba.android.arouter.launcher.ARouter
import com.base.BaseConfig

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ARouter.openLog()     // 打印日志
        ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
        // 配置返回按钮，参考TitleBarConfig，项目：https://github.com/wenkency/titlebar
        //BaseConfig.IC_TITLE_BACK = R.drawable.ic_launcher_background

    }
}