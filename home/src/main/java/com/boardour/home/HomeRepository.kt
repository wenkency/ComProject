package com.boardour.home

import com.retrofit.ApiClient
import com.retrofit.callback.ICallback

// 职责：负责数据的提供，暴露数据
object HomeRepository {

    // 文章列表
    fun articleList(pageNum: Int, callback: ICallback) {
        val url = "/article/list/${pageNum}/json"
        ApiClient.get(this, url, callback)
    }

    // Banner数据
    fun banner(callback: ICallback) {
        val url = "/banner/json"
        ApiClient.get(this, url, callback)
    }
}