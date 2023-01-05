package com.boardour.home

import androidx.lifecycle.MutableLiveData
import com.base.viewmodel.DialogStateViewModel
import com.boardour.home.bean.ArticleListBean
import com.boardour.home.bean.BannerItem
import com.boardour.net.callback.DialogCallback
import com.boardour.net.callback.NetCallback
import com.boardour.net.viewmodel.NetViewModel
import com.retrofit.RetrofitPresenter
import com.retrofit.core.RestClient

class HomeViewModel : NetViewModel() {
    // Banner数据
    var bannerData = MutableLiveData<List<BannerItem>>()

    // 文章列表数据
    var articleData = MutableLiveData<ArticleListBean>()

    // 停止网络请求
    var finishRequest = MutableLiveData<Boolean>()

    /**
     * 请求网络数据
     */
    fun requestBanner() {
        // 模拟注册
        RetrofitPresenter.get(
            this,
            "/banner/json",
            object : NetCallback<List<BannerItem>>() {
                override fun onSucceed(data: List<BannerItem>, client: RestClient) {
                    bannerData.value = data
                }

                override fun onAfter() {
                    finishRequest.value = true
                }
            })
    }

    fun requestArticleList(pageNum: Int) {
        val url = "/article/list/${pageNum}/json"
        RetrofitPresenter.get(this, url, object : NetCallback<ArticleListBean>() {
            override fun onSucceed(data: ArticleListBean, client: RestClient) {
                articleData.value = data
            }

            override fun onAfter() {
                finishRequest.value = true
            }
        })
    }
}