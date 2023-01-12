package com.boardour.net

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.boardour.net.callback.NetDialogCallback
import com.retrofit.RetrofitPresenter
import com.retrofit.core.RestClient

object CollectPresenter {
    var collect = MutableLiveData<Boolean>()
    var unCollect = MutableLiveData<Boolean>()

    // 收藏
    fun collect(activity: Activity, id: String) {
        val url = "lg/collect/${id}/json"
        RetrofitPresenter.post(activity, url, object : NetDialogCallback<String>(activity) {
            override fun onSucceed(data: String, client: RestClient) {
                collect.value = true
            }
        })
    }

    // 取消收藏
    fun unCollect(activity: Activity, id: String) {
        val url = "lg/uncollect_originId/${id}/json"

        RetrofitPresenter.post(activity, url, object : NetDialogCallback<String>(activity) {
            override fun onSucceed(data: String, client: RestClient) {
                unCollect.value = true
            }
        })
    }
}