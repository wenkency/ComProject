package com.boardour.comm.ft_login

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.boardour.comm.RouterPath

/**
 * 提供给其它模块使用
 */
object LoginProvider {
    init {
        // 初始化LoginService
        ARouter.getInstance().inject(this)
    }

    @Autowired(name = RouterPath.LOGIN_PROVIDER)
    @JvmField
    var loginProvider: ILoginProvider? = null
}