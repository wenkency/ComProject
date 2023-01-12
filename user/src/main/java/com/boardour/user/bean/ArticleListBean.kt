package com.boardour.user.bean

import com.boardour.user.bean.ArticleBean

data class ArticleListBean(
    val curPage: Int,
    val pageCount: Int,
    val datas: List<ArticleBean>,
    var position:Int
)