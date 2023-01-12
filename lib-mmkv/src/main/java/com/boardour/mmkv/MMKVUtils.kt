package com.boardour.mmkv

import android.content.Context
import com.google.gson.Gson
import com.tencent.mmkv.MMKV

object MMKVUtils {
    private val gson = Gson()
    private val mmkv: MMKV by lazy {
        MMKV.defaultMMKV()
    }

    fun init(context: Context): String {
        return MMKV.initialize(context)
    }

    fun putString(key: String, value: String?) {
        mmkv.putString(key, value)
    }

    fun getString(key: String): String? {
        return mmkv.getString(key, null)
    }

    fun putObject(key: String, obj: Any) {
        putString(key, gson.toJson(obj))
    }

    fun <T> getObject(key: String, cls: Class<T>): T? {
        val value = getString(key)
        if (value.isNullOrEmpty()) {
            return null
        }
        return gson.fromJson(value, cls)
    }

    fun putBoolean(key: String, value: Boolean) {
        mmkv.putBoolean(key, value)

    }

    fun getBoolean(key: String): Boolean {
        return mmkv.getBoolean(key, false)
    }

    fun remove(key: String) {
        mmkv.remove(key)
    }

    fun clear() {
        mmkv.clear()
    }

}