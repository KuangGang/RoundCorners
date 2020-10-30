package com.kproduce.roundcorners.util

import android.content.Context

/**
 * @author KG on 2017/6/5.
 */
object DensityUtil {
    /**
     * 根据手机的分辨率将dp的单位转成px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dpValue * scale
    }

    /**
     * 根据手机的分辨率将px(像素)的单位转成dp
     */
    fun px2dip(context: Context, pxValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return pxValue / scale
    }
}