package com.boardour.login

import android.util.Log
import android.view.View
import cn.carhouse.titlebar.DefTitleBar
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
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

    // 传参
    @Autowired
    @JvmField
    var userName: String? = null

    override fun initData() {
        ARouter.getInstance().inject(this)  // Start auto inject.
        Log.e("TAG", "username $userName")
    }

    fun toRegister(view: View) {
        RouterHelper.toRegister(this)
    }
}