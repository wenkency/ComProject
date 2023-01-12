package com.boardour.net.callback

import android.util.Log
import com.boardout.net.BuildConfig
import com.retrofit.callback.ICallback
import com.retrofit.callback.ParameterTypeImpl
import com.retrofit.callback.getType
import com.retrofit.core.RestClient
import com.retrofit.core.RestCreator

/**
 * 玩Android 解析数据统一格式
 */
abstract class NetCallback<T> : ICallback {


    // 成功回调
    override fun onSuccess(result: String, client: RestClient) {
        if (BuildConfig.DEBUG) {
            Log.e("TAG", "请求连接:${client.url}")
            Log.e("TAG", "请求结果:${result}")
        }


        val type = this.getType()
        val isStr = type.toString() == "${String::class.java}"
        // 如果是字符串，就直接返回
        if (isStr) {
            onSucceed(result as T, client)
            return
        }
        // 解析实际类型
        val netType = ParameterTypeImpl(NetBean::class.java, type)
        val netBean: NetBean<T> = RestCreator.gson.fromJson(result, netType)
        if (netBean.isSucceed()) {
            onSucceed(netBean.data, client)
        } else {
            onError(netBean.errorCode, netBean.errorMsg, client)
        }
    }

    override fun onError(code: Int, message: String, client: RestClient) {
        if (BuildConfig.DEBUG) {
            Log.e("TAG", "错误号:$code")
            Log.e("TAG", "错误信息:$message")
        }
    }

    abstract fun onSucceed(data: T, client: RestClient)
}