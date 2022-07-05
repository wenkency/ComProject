package com.boardour.register

import com.base.jetpack.BaseMutableLiveData
import com.base.viewmodel.DialogStateViewModel
import com.boardour.net.callback.DialogCallback
import com.boardour.net.viewmodel.NetViewModel
import com.retrofit.RetrofitPresenter

class RegisterViewModel : NetViewModel() {

    var result = BaseMutableLiveData("register pager")

    fun requestData(dialogState: DialogStateViewModel) {
        RetrofitPresenter.post(this, "post", object : DialogCallback<String>(dialogState) {
            override fun onLoadSucceed(data: String) {
                result.value = data
            }

        })
    }

}