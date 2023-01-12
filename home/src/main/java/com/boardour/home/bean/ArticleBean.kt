package com.boardour.home.bean

import com.boardour.comm.bean.IViewType

// 文章列表
data class ArticleBean(
    val author: String,
    val shareUser: String,
    val chapterName: String,
    val superChapterName: String,
    val title: String,
    val niceDate: String,
    var collect: Boolean,
    val link: String,
    val id: String
) : IViewType {
    override fun getItemViewType(): Int {
        return 2
    }
}
