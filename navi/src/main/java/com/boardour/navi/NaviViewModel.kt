package com.boardour.navi

import androidx.lifecycle.MutableLiveData
import com.base.viewmodel.DialogStateViewModel
import com.boardour.navi.bean.NaviBean
import com.boardour.net.callback.DialogCallback
import com.boardour.net.viewmodel.NetViewModel
import com.retrofit.RetrofitPresenter

class NaviViewModel : NetViewModel() {
    // 这个是导航列表数据
    val articleData = MutableLiveData<List<NaviBean>>()

    // 导航数据
    fun requestNavi(dialogState: DialogStateViewModel) {
        val url = "/navi/json"
        RetrofitPresenter.get(this, url, object : DialogCallback<List<NaviBean>>(dialogState) {
            override fun onLoadSucceed(data: List<NaviBean>) {
                // 如果文章列表为空就过滤掉
                val filterData = data.filter {
                    it.articles.isNotEmpty()
                }
                articleData.value = filterData
            }

        })
    }
}