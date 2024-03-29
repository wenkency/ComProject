package com.boardour.net.callback

import com.base.viewmodel.PageStateViewModel
import com.retrofit.core.RestClient

/**
 * 这个是带页面加载状态的封装
 */
abstract class PageCallback<T>(
    private val pageState: PageStateViewModel,
    private val isShowContent: Boolean = true
) : NetCallback<T>() {

    override fun onBefore(client: RestClient) {
        pageState.showLoadingContent.value = isShowContent
        pageState.showLoading.value = true
    }

    final override fun onSucceed(data: T, client: RestClient) {
        pageState.showContent.value = true
        onLoadSucceed(data)
    }

    abstract fun onLoadSucceed(data: T)

    final override fun onError(code: Int, message: String, client: RestClient) {
        pageState.showNetOrDataError.value = true
        onError(code, message)
    }

    open fun onError(code: Int, message: String) {

    }
}