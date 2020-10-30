package com.kproduce.roundcorners.util

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.kproduce.roundcorners.R

/**
 * @author kuanggang on 2019/12/10
 */
class RoundHelper {
    private var mContext: Context? = null
    private var mView: View? = null
    private var mPaint: Paint = Paint()
    private var mRectF: RectF = RectF()
    private var mStrokeRectF: RectF = RectF()
    private var mPath: Path = Path()
    private var mTempPath: Path = Path()
    private var mXfermode: Xfermode = PorterDuffXfermode(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PorterDuff.Mode.DST_OUT else PorterDuff.Mode.DST_IN)
    private var isCircle = false
    private var mRadii: FloatArray = FloatArray(8)
    private var mStrokeRadii: FloatArray = FloatArray(8)
    private var mWidth = 0
    private var mHeight = 0
    private var mStrokeColor = Color.WHITE
    private var mStrokeWidth = 0f
    private var mRadiusTopLeft = 0f
    private var mRadiusTopRight = 0f
    private var mRadiusBottomLeft = 0f
    private var mRadiusBottomRight = 0f
    fun init(context: Context, attrs: AttributeSet?, view: View) {
        if (view is ViewGroup && view.getBackground() == null) {
            view.setBackgroundColor(Color.parseColor("#00000000"))
        }
        mContext = context
        mView = view
        context.obtainStyledAttributes(attrs, R.styleable.RoundCorner)?.apply {
            val radius = getDimension(R.styleable.RoundCorner_rRadius, 0f)
            val radiusLeft = getDimension(R.styleable.RoundCorner_rLeftRadius, radius)
            val radiusRight = getDimension(R.styleable.RoundCorner_rRightRadius, radius)
            val radiusTop = getDimension(R.styleable.RoundCorner_rTopRadius, radius)
            val radiusBottom = getDimension(R.styleable.RoundCorner_rBottomRadius, radius)
            mRadiusTopLeft = getDimension(R.styleable.RoundCorner_rTopLeftRadius, if (radiusTop > 0) radiusTop else radiusLeft)
            mRadiusTopRight = getDimension(R.styleable.RoundCorner_rTopRightRadius, if (radiusTop > 0) radiusTop else radiusRight)
            mRadiusBottomLeft = getDimension(R.styleable.RoundCorner_rBottomLeftRadius, if (radiusBottom > 0) radiusBottom else radiusLeft)
            mRadiusBottomRight = getDimension(R.styleable.RoundCorner_rBottomRightRadius, if (radiusBottom > 0) radiusBottom else radiusRight)
            mStrokeWidth = getDimension(R.styleable.RoundButton_rStrokeWidth, 0f)
            mStrokeColor = getColor(R.styleable.RoundButton_rStrokeColor, mStrokeColor)
            recycle()
        }
        if (!isCircle) {
            setRadius()
        }
    }

    private fun setRadius() {
        mRadii[1] = mRadiusTopLeft - mStrokeWidth
        mRadii[0] = mRadii[1]
        mRadii[3] = mRadiusTopRight - mStrokeWidth
        mRadii[2] = mRadii[3]
        mRadii[5] = mRadiusBottomRight - mStrokeWidth
        mRadii[4] = mRadii[5]
        mRadii[7] = mRadiusBottomLeft - mStrokeWidth
        mRadii[6] = mRadii[7]
        mStrokeRadii[1] = mRadiusTopLeft
        mStrokeRadii[0] = mStrokeRadii[1]
        mStrokeRadii[3] = mRadiusTopRight
        mStrokeRadii[2] = mStrokeRadii[3]
        mStrokeRadii[5] = mRadiusBottomRight
        mStrokeRadii[4] = mStrokeRadii[5]
        mStrokeRadii[7] = mRadiusBottomLeft
        mStrokeRadii[6] = mStrokeRadii[7]
    }

    fun onSizeChanged(width: Int, height: Int) {
        mWidth = width
        mHeight = height
        if (isCircle) {
            val radius = Math.min(height, width) * 1f / 2 - mStrokeWidth
            mRadiusTopLeft = radius
            mRadiusTopRight = radius
            mRadiusBottomRight = radius
            mRadiusBottomLeft = radius
            setRadius()
        }
        mRectF[0f, 0f, width.toFloat()] = height.toFloat()
        mStrokeRectF[mStrokeWidth / 2, mStrokeWidth / 2, width - mStrokeWidth / 2] = height - mStrokeWidth / 2
    }

    fun preDraw(canvas: Canvas) {
        canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG)
        if (mStrokeWidth > 0) {
            val sx = (mWidth - 2 * mStrokeWidth) / mWidth
            val sy = (mHeight - 2 * mStrokeWidth) / mHeight
            // 缩小画布，使图片内容不被borders覆盖
            canvas.scale(sx, sy, mWidth / 2.0f, mHeight / 2.0f)
        }
    }

    fun drawPath(canvas: Canvas) {
        mPaint.reset()
        mPath.reset()
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mPaint.xfermode = mXfermode
        mPath.addRoundRect(mRectF, mRadii, Path.Direction.CCW)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTempPath.reset()
            mTempPath.addRect(mRectF, Path.Direction.CCW)
            mTempPath.op(mPath, Path.Op.DIFFERENCE)
            canvas.drawPath(mTempPath, mPaint)
        } else {
            canvas.drawPath(mPath, mPaint)
        }
        mPaint.xfermode = null
        canvas.restore()

        // draw stroke
        if (mStrokeWidth > 0) {
            mPaint.style = Paint.Style.STROKE
            mPaint.strokeWidth = mStrokeWidth
            mPaint.color = mStrokeColor
            mPath.reset()
            mPath.addRoundRect(mStrokeRectF, mStrokeRadii, Path.Direction.CCW)
            canvas.drawPath(mPath, mPaint)
        }
    }

    fun setCircle(isCircle: Boolean) {
        this.isCircle = isCircle
    }

    fun setRadius(radiusDp: Float) {
        if (mContext == null) {
            return
        }
        val radiusPx = DensityUtil.dip2px(mContext!!, radiusDp)
        mRadiusTopLeft = radiusPx
        mRadiusTopRight = radiusPx
        mRadiusBottomLeft = radiusPx
        mRadiusBottomRight = radiusPx
        mView?.invalidate()
    }

    fun setRadius(radiusTopLeftDp: Float, radiusTopRightDp: Float, radiusBottomLeftDp: Float, radiusBottomRightDp: Float) {
        mContext?.let { context ->
            mRadiusTopLeft = DensityUtil.dip2px(context, radiusTopLeftDp)
            mRadiusTopRight = DensityUtil.dip2px(context, radiusTopRightDp)
            mRadiusBottomLeft = DensityUtil.dip2px(context, radiusBottomLeftDp)
            mRadiusBottomRight = DensityUtil.dip2px(context, radiusBottomRightDp)
            mView?.invalidate()
        }

    }

    fun setRadiusLeft(radiusDp: Float) {
        if (mContext == null) {
            return
        }
        val radiusPx = DensityUtil.dip2px(mContext!!, radiusDp)
        mRadiusTopLeft = radiusPx
        mRadiusBottomLeft = radiusPx
        mView?.invalidate()
    }

    fun setRadiusRight(radiusDp: Float) {
        if (mContext == null) {
            return
        }
        val radiusPx = DensityUtil.dip2px(mContext!!, radiusDp)
        mRadiusTopRight = radiusPx
        mRadiusBottomRight = radiusPx
        mView?.invalidate()

    }

    fun setRadiusTop(radiusDp: Float) {
        if (mContext == null) {
            return
        }
        val radiusPx = DensityUtil.dip2px(mContext!!, radiusDp)
        mRadiusTopLeft = radiusPx
        mRadiusTopRight = radiusPx
        mView?.invalidate()
    }

    fun setRadiusBottom(radiusDp: Float) {
        if (mContext == null) {
            return
        }
        val radiusPx = DensityUtil.dip2px(mContext!!, radiusDp)
        mRadiusBottomLeft = radiusPx
        mRadiusBottomRight = radiusPx
        mView?.invalidate()
    }

    fun setRadiusTopLeft(radiusDp: Float) {
        if (mContext == null) {
            return
        }
        mRadiusTopLeft = DensityUtil.dip2px(mContext!!, radiusDp)
        mView?.invalidate()
    }

    fun setRadiusTopRight(radiusDp: Float) {
        if (mContext == null) {
            return
        }
        mRadiusTopRight = DensityUtil.dip2px(mContext!!, radiusDp)
        mView?.invalidate()
    }

    fun setRadiusBottomLeft(radiusDp: Float) {
        if (mContext == null) {
            return
        }
        mRadiusBottomLeft = DensityUtil.dip2px(mContext!!, radiusDp)
        mView?.invalidate()
    }

    fun setRadiusBottomRight(radiusDp: Float) {
        if (mContext == null) {
            return
        }
        mRadiusBottomRight = DensityUtil.dip2px(mContext!!, radiusDp)
        mView?.invalidate()
    }

    fun setStrokeWidth(widthDp: Float) {
        if (mContext == null) {
            return
        }
        mStrokeWidth = DensityUtil.dip2px(mContext!!, widthDp)
        if (mView != null) {
            setRadius()
            onSizeChanged(mWidth, mHeight)
            mView?.invalidate()
        }
    }

    fun setStrokeColor(color: Int) {
        mStrokeColor = color
        mView?.invalidate()
    }

    fun setStrokeWidthColor(widthDp: Float, color: Int) {
        if (mContext == null) {
            return
        }
        mStrokeWidth = DensityUtil.dip2px(mContext!!, widthDp)
        mStrokeColor = color
        if (mView != null) {
            setRadius()
            onSizeChanged(mWidth, mHeight)
            mView?.invalidate()
        }
    }
}