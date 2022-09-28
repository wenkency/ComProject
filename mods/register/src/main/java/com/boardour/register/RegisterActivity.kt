package com.boardour.register

import android.widget.Toast
import cn.carhouse.titlebar.DefTitleBar
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.BindActivity
import com.boardour.comm.RouterHelper
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

        // 注册成功事件
        viewModel.registerSuccess.observe(this) {
            // 到登录页面
            if (it) {
                RouterHelper.toLogin(this)
            }
        }
    }

    inner class Click {
        fun requestData() {
            Toast.makeText(
                this@RegisterActivity,
                "${viewModel.name.value}:${viewModel.pwd.value}",
                Toast.LENGTH_LONG
            ).show()
            viewModel.requestData(dialogState)
        }
    }

}