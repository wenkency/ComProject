package com.boardour.user

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import cn.carhouse.views.XTextLayout
import com.base.BaseFragment
import com.base.utils.FragmentUtils
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
        val tvCollect = findViewById<XTextLayout>(R.id.tv_collect)
        val ivIcon = findViewById<ImageView>(R.id.iv_icon)
        tvCollect.ivLeft.scaleType = ImageView.ScaleType.FIT_XY
        tvCollect.ivRight.scaleType = ImageView.ScaleType.FIT_XY

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



        viewModel.unLogin.observe(this) {
            if (it) {
                UserViewModel.removeUser()
            }
        }
        btnUnLogin.setOnClickListener {
            viewModel.unLogin(getAppActivity())
        }

        tvCollect.setOnClickListener {
            // 到收藏页面
            collect()
        }

        // 登录
        tvLogin.setOnClickListener {
            toLogin()
        }
        ivIcon.setOnClickListener {
            toLogin()
        }
    }

    private fun collect() {
        if (UserViewModel.isLogin.value == false) {
            Toast.makeText(getAppActivity(), "请先登录!", Toast.LENGTH_SHORT).show()
            return
        }
        RouterHelper.toCollect(getAppActivity())
    }

    private fun toLogin() {
        // 如果没有登录
        if (UserViewModel.isLogin.value == false) {
            RouterHelper.toLogin(getAppActivity())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UserViewModel.user.removeObservers(this)
        UserViewModel.isLogin.removeObservers(this)
        FragmentUtils.removeFragment(parentFragmentManager,this)
    }
}