package com.boardour.user

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.boardour.net.callback.NetDialogCallback
import com.boardour.net.cookie.NetCookieJar
import com.boardour.net.viewmodel.NetViewModel
import com.retrofit.RxPresenter
import com.retrofit.callback.BeanCallback
import com.retrofit.core.RestClient

class UserNetViewModel : NetViewModel() {

    var unLogin = MutableLiveData<Boolean>()

    fun unLogin(activity: Activity) {
        val url = "/user/logout/json"
        RxPresenter.get(this, url, object : NetDialogCallback<String>(activity) {
            override fun onSucceed(data: String, client: RestClient) {
                unLogin.value = true
                // 清除Cookie
                NetCookieJar.clear()
            }
        })
    }
}