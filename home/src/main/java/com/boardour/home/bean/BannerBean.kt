package com.boardour.home.bean

import com.boardour.comm.bean.IViewType

// Banner数据
data class BannerBean(val items: List<BannerItem>) : IViewType {
    override fun getItemViewType(): Int {
        return 1
    }
}
