package com.boardour.comm.ft_login

import androidx.lifecycle.MutableLiveData
import com.boardour.comm.bean.UserBean
import com.boardour.mmkv.MMKVUtils

/**
 * 用户数据共享类
 */
object UserViewModel {
    private const val loginKey = "login"
    private const val userKey = "UserBean"
    var isLogin = MutableLiveData<Boolean>()
    var user = MutableLiveData<UserBean>()

    init {
        isLogin.value = MMKVUtils.getBoolean(loginKey)
        user.value = MMKVUtils.getObject(userKey, UserBean::class.java)
    }


    /**
     * 缓存用户信息
     */
    fun putUser(userBean: UserBean) {
        MMKVUtils.putObject(userKey, userBean)
        user.value = userBean
    }

    // 移除用户信息
    fun removeUser() {
        MMKVUtils.remove(userKey)
        MMKVUtils.remove(loginKey)
        user.value = UserBean("")
        isLogin.value = false
    }

    fun putLogin(login: Boolean) {
        MMKVUtils.putBoolean(loginKey, login)
        isLogin.value = login
    }
}