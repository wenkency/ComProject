package com.boardour.net.viewmodel

import androidx.lifecycle.ViewModel
import com.retrofit.cancel.ApiCancelUtils
import com.retrofit.cancel.SuspendCancelUtils

// 自动取消网络
open class NetViewModel : ViewModel() {

    /**
     * 自动取消网络
     */
    override fun onCleared() {
        ApiCancelUtils.cancel(this)
        SuspendCancelUtils.cancel(this)
    }
}