package com.boardour.comproject

import android.app.Application
import android.util.Log
import cn.carhouse.web.WebUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.base.BaseConfig
import com.base.BuildConfig
import com.boardour.comm.AppDialog
import com.boardour.comproject.utils.MultidexUtils
import com.boardour.mmkv.MMKVUtils
import com.boardour.net.cookie.NetCookieJar
import com.lven.loading.LoadingManager
import com.retrofit.config.RestConfig
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlin.concurrent.thread


/**
 * 应用的Application
 */
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

    /*override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        val isMainProcess = MultidexUtils.isMainProcess(base)
        if (isMainProcess && !MultidexUtils.isVMMultidexCapable()) {
            //MultidexUtils.loadMultiDex(base)
            //MultiDex.install(base)
            BoostMultiDex.install(this)
        }
    }*/

    override fun onCreate() {
        super.onCreate()
        val isMainProcess = MultidexUtils.isMainProcess(this)
        if (!isMainProcess) {
            return
        }
        val temp = System.currentTimeMillis()
        /*AppStartTaskDispatcher.create()
            .setShowLog(true)
            .setAllTaskWaitTimeOut(5000)
            .addAppStartTask(object : AppStartTask() {
                override fun run() {
                    init()
                }

                override fun isRunOnMainThread(): Boolean {
                    return false
                }

                override fun needWait(): Boolean {
                    return true
                }

            })
            .start()
            .await()*/
        init()
        Log.e("TAG", "init ${System.currentTimeMillis() - temp}")
    }

    private fun init() {
        // MMKV 缓存
        MMKVUtils.init(this)
        // 项目通用Dialog配置
        BaseConfig.dialog = AppDialog()
        // 网络初始化 http://httpbin.org/
        RestConfig.baseUrl("https://www.wanandroid.com/")
            .debugUrl("https://www.wanandroid.com/")
            .register(this)
        RestConfig.cookieJar = NetCookieJar
        // 加载页面：https://github.com/wenkency/loading
        LoadingManager.BASE_LOADING_LAYOUT_ID = R.layout.loading_pager_loading
        LoadingManager.BASE_RETRY_LAYOUT_ID = R.layout.loading_pager_error
        LoadingManager.BASE_DATA_ERROR_LAYOUT_ID = R.layout.loading_pager_data_error




        // ARouter
        if (BuildConfig.DEBUG) {
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化


        thread {
            // Web初始化
            WebUtils.getInstance().init(this)
        }
    }

}