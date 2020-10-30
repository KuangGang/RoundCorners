package com.kproduce.roundcorners

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.kproduce.roundcorners.util.RoundHelper
import com.kproduce.roundcorners.util.RoundMethodInterface

/**
 * @author kuanggang on 2019/12/10
 */
class RoundRelativeLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) : RelativeLayout(context, attrs, defStyleAttr), RoundMethodInterface {
    private val mHelper = RoundHelper()
    init {
        mHelper.init(context, attrs, this)
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mHelper.onSizeChanged(w, h)
    }

    override fun draw(canvas: Canvas) {
        mHelper.preDraw(canvas)
        super.draw(canvas)
        mHelper.drawPath(canvas)
    }

    override fun setRadius(radiusDp: Float) {
        mHelper.setRadius(radiusDp)
    }

    override fun setRadius(radiusTopLeftDp: Float, radiusTopRightDp: Float, radiusBottomLeftDp: Float, radiusBottomRightDp: Float) {
        mHelper.setRadius(radiusTopLeftDp, radiusTopRightDp, radiusBottomLeftDp, radiusBottomRightDp)
    }

    override fun setRadiusLeft(radiusDp: Float) {
        mHelper.setRadiusLeft(radiusDp)
    }

    override fun setRadiusRight(radiusDp: Float) {
        mHelper.setRadiusRight(radiusDp)
    }

    override fun setRadiusTop(radiusDp: Float) {
        mHelper.setRadiusTop(radiusDp)
    }

    override fun setRadiusBottom(radiusDp: Float) {
        mHelper.setRadiusBottom(radiusDp)
    }

    override fun setRadiusTopLeft(radiusDp: Float) {
        mHelper.setRadiusTopLeft(radiusDp)
    }

    override fun setRadiusTopRight(radiusDp: Float) {
        mHelper.setRadiusTopRight(radiusDp)
    }

    override fun setRadiusBottomLeft(radiusDp: Float) {
        mHelper.setRadiusBottomLeft(radiusDp)
    }

    override fun setRadiusBottomRight(radiusDp: Float) {
        mHelper.setRadiusBottomRight(radiusDp)
    }

    override fun setStrokeWidth(widthDp: Float) {
        mHelper.setStrokeWidth(widthDp)
    }

    override fun setStrokeColor(color: Int) {
        mHelper.setStrokeColor(color)
    }

    override fun setStrokeWidthColor(widthDp: Float, color: Int) {
        mHelper.setStrokeWidthColor(widthDp, color)
    }


}