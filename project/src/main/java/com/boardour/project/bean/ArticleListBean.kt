package com.boardour.project.bean

data class ArticleListBean(
    val curPage: Int,
    val pageCount: Int,
    val datas: List<ArticleBean>,
    var position:Int
)