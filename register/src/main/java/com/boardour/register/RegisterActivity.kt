package com.boardour.register

import android.widget.Toast
import cn.carhouse.titlebar.DefTitleBar
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.BindActivity
import com.boardour.comm.RouterPath
import com.boardout.register.R
import com.boardout.register.databinding.ActivityRegisterBinding

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
            if (it == true) {
                finish()
            }
        }
    }

    inner class Click {
        // 注册
        fun register() {
            val name = viewModel.name.value
            val pass = viewModel.pwd.value
            if (name.isNullOrEmpty()) {
                Toast.makeText(this@RegisterActivity, "请输入帐号", Toast.LENGTH_SHORT).show()
                return
            }
            if (pass.isNullOrEmpty()) {
                Toast.makeText(this@RegisterActivity, "请输入密码", Toast.LENGTH_SHORT).show()
                return
            }
            viewModel.requestData(this@RegisterActivity, name, pass)
        }
    }

}