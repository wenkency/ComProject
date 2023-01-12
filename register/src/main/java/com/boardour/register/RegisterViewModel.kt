package com.boardour.register

import android.app.Activity
import com.base.jetpack.BaseMutableLiveData
import com.base.viewmodel.DialogStateViewModel
import com.boardour.comm.bean.UserBean
import com.boardour.comm.ft_login.UserViewModel
import com.boardour.net.callback.DialogCallback
import com.boardour.net.callback.NetDialogCallback
import com.boardour.net.viewmodel.NetViewModel
import com.retrofit.RetrofitPresenter
import com.retrofit.core.RestClient

/**
 * 注册页面
 */
class RegisterViewModel : NetViewModel() {
    // 用户名
    var name = BaseMutableLiveData<String>()

    // 密码
    var pwd = BaseMutableLiveData<String>()

    var result = BaseMutableLiveData("注册")

    // 注册成功
    var registerSuccess = BaseMutableLiveData(false)

    /**
     * 请求网络数据
     */
    fun requestData(activity: Activity,name: String, pass: String) {
        // 模拟注册
        RetrofitPresenter.postForm(
            this,
            "/user/register",
            RegisterData(name, pass, pass),
            object : NetDialogCallback<UserBean>(activity) {
                override fun onSucceed(data: UserBean, client: RestClient) {
                    registerSuccess.value = true
                    UserViewModel.putLogin(true)
                    UserViewModel.putUser(data)
                }

            })
    }

}