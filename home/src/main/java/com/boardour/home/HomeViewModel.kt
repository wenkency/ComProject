package com.boardour.home

import androidx.lifecycle.MutableLiveData
import com.base.viewmodel.PageStateViewModel
import com.boardour.home.bean.ArticleListBean
import com.boardour.home.bean.BannerItem
import com.boardour.net.callback.NetCallback
import com.boardour.net.callback.PageCallback
import com.boardour.net.viewmodel.NetViewModel
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
    fun requestBanner(pageState: PageStateViewModel) {
        // Banner数据
        HomeRepository.banner(object : PageCallback<List<BannerItem>>(pageState) {
            override fun onLoadSucceed(data: List<BannerItem>) {
                bannerData.value = data
            }

            override fun onAfter() {
                finishRequest.value = true
            }
        })
    }

    fun requestArticleList(pageNum: Int) {
        // 文章列表
        HomeRepository.articleList(pageNum, object : NetCallback<ArticleListBean>() {
            override fun onSucceed(data: ArticleListBean, client: RestClient) {
                articleData.value = data
            }

            override fun onAfter() {
                finishRequest.value = true
            }
        })
    }
}