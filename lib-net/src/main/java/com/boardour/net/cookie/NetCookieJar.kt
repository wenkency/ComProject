package com.boardour.net.cookie

import com.boardour.mmkv.MMKVUtils
import com.google.gson.reflect.TypeToken
import com.retrofit.core.RestCreator
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl


object NetCookieJar : CookieJar {
    private const val loginUrl = "https://www.wanandroid.com/user/login"

    // 缓存的Cookie
    private val cookies = arrayListOf<Cookie>()
    private const val key = "token"
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        // 直接返回
        if (cookies.size > 0) {
            return cookies
        }
        // 读取缓存
        val token = MMKVUtils.getString(key)
        token?.let {
            val type = object : TypeToken<ArrayList<Cookie>>() {}.type
            val list: List<Cookie> = RestCreator.gson.fromJson(it, type)
            cookies.addAll(list)
        }
        return cookies
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        // 登录的url
        if (loginUrl == url.toString()) {
            // 缓存
            MMKVUtils.putString(key, RestCreator.gson.toJson(cookies))
        }
    }

    fun clear() {
        cookies.clear()
        MMKVUtils.remove(key)
    }
}