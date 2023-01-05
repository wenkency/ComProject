package com.boardour.home.bean

data class ArticleListBean(
    val curPage: Int,
    val pageCount: Int,
    val datas: List<ArticleBean>
)