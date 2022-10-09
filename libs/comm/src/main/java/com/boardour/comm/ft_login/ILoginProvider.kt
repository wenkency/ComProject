package com.boardour.comm.ft_login

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 定义接口，在Login模块实现
 */
interface ILoginProvider : IProvider {
    // 登录状态
    fun isLogin(): Boolean

    // 用户名
    fun userName(): String?
}