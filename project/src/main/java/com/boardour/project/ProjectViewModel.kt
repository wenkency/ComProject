package com.boardour.project

import androidx.lifecycle.MutableLiveData
import com.boardour.net.callback.NetCallback
import com.boardour.net.viewmodel.NetViewModel
import com.boardour.project.bean.ArticleBean
import com.boardour.project.bean.ArticleListBean
import com.retrofit.ApiClient
import com.retrofit.core.RestClient

class ProjectViewModel : NetViewModel() {
    // 项目顶部分类数据
    val articleData = MutableLiveData<List<ArticleBean>>()
    val articleListData = MutableLiveData<ArticleListBean>()
    val finish = MutableLiveData<Boolean>()
    fun requestProject() {
        val url = "/project/tree/json"
        ApiClient.get(this, url, object : NetCallback<List<ArticleBean>>() {
            override fun onSucceed(data: List<ArticleBean>, client: RestClient) {
                articleData.value = data
            }

            override fun onAfter() {
                finish.value = true
            }
        })
    }

    fun requestProjectList(position: Int, pageNum: Int, cid: String) {
        val url = "/project/list/${pageNum}/json"

        ApiClient.get(this, url, object : NetCallback<ArticleListBean>() {
            override fun onSucceed(data: ArticleListBean, client: RestClient) {
                // 手动添加位置
                data.position = position
                articleListData.value = data
            }
        }, "cid", cid)
    }
}