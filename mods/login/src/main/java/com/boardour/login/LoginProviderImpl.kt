package com.boardour.login

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.boardour.comm.RouterPath
import com.boardour.comm.ft_login.ILoginProvider

/**
 * 登录模块实现类
 */
@Route(path = RouterPath.LOGIN_PROVIDER)
open class LoginProviderImpl : ILoginProvider {

    override fun init(context: Context?) {

    }

    override fun isLogin(): Boolean = true

    override fun userName(): String? {
        return "kotlin lven"
    }


}