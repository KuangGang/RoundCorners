package com.kproduce.roundcorners.util

/**
 * @author kuanggang
 */
interface RoundMethodInterface {
    fun setRadius(radiusDp: Float)
    fun setRadius(radiusTopLeftDp: Float, radiusTopRightDp: Float, radiusBottomLeftDp: Float, radiusBottomRightDp: Float)
    fun setRadiusLeft(radiusDp: Float)
    fun setRadiusRight(radiusDp: Float)
    fun setRadiusTop(radiusDp: Float)
    fun setRadiusBottom(radiusDp: Float)
    fun setRadiusTopLeft(radiusDp: Float)
    fun setRadiusTopRight(radiusDp: Float)
    fun setRadiusBottomLeft(radiusDp: Float)
    fun setRadiusBottomRight(radiusDp: Float)
    fun setStrokeWidth(widthDp: Float)
    fun setStrokeColor(color: Int)
    fun setStrokeWidthColor(widthDp: Float, color: Int)
}