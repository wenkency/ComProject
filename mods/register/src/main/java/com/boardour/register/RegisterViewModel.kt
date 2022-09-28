package com.boardour.register

import com.base.jetpack.BaseMutableLiveData
import com.base.viewmodel.DialogStateViewModel
import com.boardour.net.callback.DialogCallback
import com.boardour.net.viewmodel.NetViewModel
import com.retrofit.RetrofitPresenter

/**
 * 注册页面
 */
class RegisterViewModel : NetViewModel() {
    // 用户名
    var name = BaseMutableLiveData<String>()

    // 密码
    var pwd = BaseMutableLiveData<String>()

    var result = BaseMutableLiveData("注册，注册成功到登录页面")

    // 注册成功
    var registerSuccess = BaseMutableLiveData(false)


    fun requestData(dialogState: DialogStateViewModel) {
        // 模拟注册
        RetrofitPresenter.post(this, "post", object : DialogCallback<String>(dialogState) {
            override fun onLoadSucceed(data: String) {
                // result.value = data
                registerSuccess.value = true
            }
        })
    }

}