package com.boardour.login

import android.app.Activity
import android.widget.Toast
import com.base.BaseConfig
import com.boardour.comm.bean.UserBean
import com.boardour.comm.ft_login.UserViewModel
import com.boardour.net.callback.NetCallback
import com.boardour.net.viewmodel.NetViewModel
import com.retrofit.RxClient
import com.retrofit.core.RestClient

class LoginViewModel : NetViewModel() {
    /**
     * 登录
     */
    fun login(activity: Activity, name: String, password: String) {
        RxClient.postForm(
            this,
            "/user/login",
            LoginData(name, password),
            object : NetCallback<UserBean>() {
                override fun onBefore(client: RestClient) {
                    BaseConfig.dialog?.showDialog(activity)
                }

                override fun onSucceed(data: UserBean, client: RestClient) {
                    UserViewModel.putLogin(true)
                    UserViewModel.putUser(data)
                }

                override fun onAfter() {
                    BaseConfig.dialog?.dismissDialog(true)
                }

                override fun onError(code: Int, message: String, client: RestClient) {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                }

            }
        )
    }
}