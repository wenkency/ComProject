package com.boardour.register

import cn.carhouse.titlebar.DefTitleBar
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.BindActivity
import com.boardour.comm.RouterPath
import com.boardour.register.databinding.ActivityRegisterBinding

/**
 * 注册页面
 */
@Route(path = RouterPath.REGISTER)
class RegisterActivity : BindActivity<RegisterViewModel, ActivityRegisterBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_register

    override fun initTitle(titleBar: DefTitleBar) {
        titleBar.setTitle("注册页面")
    }

    override fun onBind(binding: ActivityRegisterBinding, viewModel: RegisterViewModel) {
        // UI相关
        binding.vm = viewModel
        // 点击事件
        binding.click = Click()
    }

    inner class Click {
        fun requestData() {
            viewModel.requestData(dialogState)
        }
    }

}