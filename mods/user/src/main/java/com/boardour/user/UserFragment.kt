package com.boardour.user

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.base.BaseFragment
import com.base.utils.ViewModelUtils
import com.boardour.comm.RouterHelper
import com.boardour.comm.ft_login.UserViewModel
import com.boardout.user.R


class UserFragment : BaseFragment() {
    private val viewModel: UserNetViewModel by lazy {
        ViewModelUtils.getViewModel(this, UserNetViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.user_fragment
    }

    override fun initViews() {
        val tv = findViewById<TextView>(R.id.tv_user)
        val tvLogin = findViewById<TextView>(R.id.tv_register)
        val btnUnLogin = findViewById<Button>(R.id.btn_unlogin)

        UserViewModel.user.observe(this) {
            if (it != null) {
                tv.text = it.username
            }
        }
        UserViewModel.isLogin.observe(this) {
            if (it == true) {
                tv.visibility = View.VISIBLE
                tvLogin.visibility = View.GONE
                btnUnLogin.visibility = View.VISIBLE
            } else {
                tv.visibility = View.GONE
                tvLogin.visibility = View.VISIBLE
                btnUnLogin.visibility = View.GONE
            }
        }

        tvLogin.setOnClickListener {
            // 如果没有登录
            if (UserViewModel.isLogin.value == false) {
                RouterHelper.toLogin(getAppActivity())
            }
        }

        viewModel.unLogin.observe(this) {
            if (it) {
                UserViewModel.removeUser()
            }
        }
        btnUnLogin.setOnClickListener {
            viewModel.unLogin(getAppActivity())
        }
    }
}