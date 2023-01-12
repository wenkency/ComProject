package com.boardour.net.callback

import android.app.Activity
import android.widget.Toast
import com.base.BaseConfig
import com.retrofit.core.RestClient

/**
 * 玩Android 解析数据统一格式
 */
abstract class NetDialogCallback<T>(val activity: Activity) : NetCallback<T>() {
    override fun onBefore(client: RestClient) {
        BaseConfig.dialog?.showDialog(activity)
    }

    override fun onAfter() {
        BaseConfig.dialog?.dismissDialog(true)
    }

    override fun onError(code: Int, message: String, client: RestClient) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}