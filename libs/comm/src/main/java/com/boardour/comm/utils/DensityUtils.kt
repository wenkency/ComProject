package com.boardour.comm.utils

import android.content.res.Resources
import android.util.TypedValue

object DensityUtils {
    /**
     * 获取屏幕宽
     */
    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    /**
     * 获取屏幕高
     */
    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param value 虚拟像素
     * @return 像素
     */
    fun dp2px(value: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, value,
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param value 像素
     * @return 虚拟像素
     */
    fun px2dp(value: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX, value.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }
}