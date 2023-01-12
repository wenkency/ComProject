package com.boardour.net.callback

/**
 * 玩Android 解析数据统一格式
 */
class NetBean<T>(val data: T, val errorCode: Int, val errorMsg: String) {
    fun isSucceed(): Boolean {
        return 0 == errorCode
    }
}