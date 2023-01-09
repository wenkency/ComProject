package com.boardour.comproject

import android.app.Application
import cn.carhouse.web.WebUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.base.BaseConfig
import com.boardour.comm.AppDialog
import com.retrofit.config.RestConfig
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlin.concurrent.thread

class AppApplication : Application() {


    // static 代码段可以防止内存泄露
    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            //layout.setPrimaryColorsId(R.color.colorBasePrimary, android.R.color.white);// 全局设置主题颜色
            ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));// 指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> // 指定为经典Footer， 默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }

    override fun onCreate() {
        super.onCreate()

        thread {
            if (BuildConfig.DEBUG) {
                ARouter.openLog()     // 打印日志
                ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            }
            ARouter.init(this) // 尽可能早，推荐在Application中初始化
        }

        // 1. 项目通用Dialog配置
        BaseConfig.dialog = AppDialog()
        // 2. 网络初始化 http://httpbin.org/
        RestConfig.baseUrl("https://www.wanandroid.com/")
            .debugUrl("https://www.wanandroid.com/")
            .register(this)
        thread {
            // 3. Web初始化
            WebUtils.getInstance().init(this)
        }
    }


}