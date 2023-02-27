package com.boardour.net.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.retrofit.cancel.ApiCancelUtils
import com.retrofit.cancel.SuspendCancelUtils

// 自动取消网络
open class NetAndroidViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * 自动取消网络
     */
    override fun onCleared() {
        ApiCancelUtils.cancel(this)
        SuspendCancelUtils.cancel(this)
    }
}