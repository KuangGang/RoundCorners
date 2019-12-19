package com.kproduce.roundcorners;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author kuanggang on 2019/12/10
 */
public class RoundHelper {

    private float[] mRadii;
    private float[] mStrokeRadii;

    private Paint mPaint;
    private RectF mRectF;

    private Path mPath;
    private Path mTempPath;

    private Xfermode mXfermode;

    private boolean isCircle;

    private int mWidth;
    private int mHeight;
    private int mStrokeWidth;
    private int mStrokeColor;

    public void init(Context context, AttributeSet attrs, View view) {
        // 禁止硬件加速，硬件加速会有一些问题，这里禁用掉
        if (view instanceof ViewGroup && view.getBackground() == null) {
            view.setBackgroundColor(Color.parseColor("#00000000"));
        }

        mRadii = new float[8];
        mStrokeRadii = new float[8];
        mPaint = new Paint();
        mRectF = new RectF();
        mPath = new Path();
        mTempPath = new Path();
        mXfermode = new PorterDuffXfermode(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ? PorterDuff.Mode.DST_OUT : PorterDuff.Mode.DST_IN);
        mStrokeColor = Color.WHITE;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundCorner);
        if (array == null) {
            return;
        }
        int radius = array.getDimensionPixelSize(R.styleable.RoundCorner_rRadius, 0);
        int radiusLeft = array.getDimensionPixelSize(R.styleable.RoundCorner_rLeftRadius, radius);
        int radiusRight = array.getDimensionPixelSize(R.styleable.RoundCorner_rRightRadius, radius);
        int radiusTop = array.getDimensionPixelSize(R.styleable.RoundCorner_rTopRadius, radius);
        int radiusBottom = array.getDimensionPixelSize(R.styleable.RoundCorner_rBottomRadius, radius);

        int radiusTopLeft = array.getDimensionPixelSize(R.styleable.RoundCorner_rTopLeftRadius, radiusTop > 0 ? radiusTop : radiusLeft);
        int radiusTopRight = array.getDimensionPixelSize(R.styleable.RoundCorner_rTopRightRadius, radiusTop > 0 ? radiusTop : radiusRight);
        int radiusBottomLeft = array.getDimensionPixelSize(R.styleable.RoundCorner_rBottomLeftRadius, radiusBottom > 0 ? radiusBottom : radiusLeft);
        int radiusBottomRight = array.getDimensionPixelSize(R.styleable.RoundCorner_rBottomRightRadius, radiusBottom > 0 ? radiusBottom : radiusRight);
        mStrokeWidth = array.getDimensionPixelSize(R.styleable.RoundButton_rStrokeWidth, 0);
        mStrokeColor = array.getColor(R.styleable.RoundButton_rStrokeColor, mStrokeColor);

        array.recycle();
        if (!isCircle) {
            setRadius(radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight);
        }
    }

    private void setRadius(int topLeftDp, int topRightDp, int bottomLeftDp, int bottomRightDp) {
        mRadii[0] = mRadii[1] = topLeftDp - mStrokeWidth;
        mRadii[2] = mRadii[3] = topRightDp - mStrokeWidth;
        mRadii[4] = mRadii[5] = bottomRightDp - mStrokeWidth;
        mRadii[6] = mRadii[7] = bottomLeftDp - mStrokeWidth;

        mStrokeRadii[0] = mStrokeRadii[1] = topLeftDp;
        mStrokeRadii[2] = mStrokeRadii[3] = topRightDp;
        mStrokeRadii[4] = mStrokeRadii[5] = bottomRightDp;
        mStrokeRadii[6] = mStrokeRadii[7] = bottomLeftDp;
    }

    public void onSizeChanged(int width, int height) {
        mWidth = width;
        mHeight = height;

        if (isCircle) {
            int radius = Math.min(height, width) / 2 - mStrokeWidth;
            setRadius(radius, radius, radius, radius);
        }
        if (mRectF == null) {
            return;
        }
        mRectF.set(0, 0, width, height);
    }

    public void preDraw(Canvas canvas) {
        canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG);
        if (mStrokeWidth > 0) {
            float sx = 1.0f * (mWidth - 2 * mStrokeWidth) / mWidth;
            float sy = 1.0f * (mHeight - 2 * mStrokeWidth) / mHeight;
            // 缩小画布，使图片内容不被borders覆盖
            canvas.scale(sx, sy, mWidth / 2.0f, mHeight / 2.0f);
        }
    }

    public void drawPath(Canvas canvas) {
        mPaint.reset();
        mPath.reset();

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(mXfermode);

        mPath.addRoundRect(mRectF, mRadii, Path.Direction.CCW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTempPath.reset();
            mTempPath.addRect(mRectF, Path.Direction.CCW);
            mTempPath.op(mPath, Path.Op.DIFFERENCE);
            canvas.drawPath(mTempPath, mPaint);
        } else {
            canvas.drawPath(mPath, mPaint);
        }
        mPaint.setXfermode(null);
        canvas.restore();

        // draw stroke
        if (mStrokeWidth > 0) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setColor(mStrokeColor);

            mPath.reset();
            mPath.addRoundRect(mRectF, mStrokeRadii, Path.Direction.CCW);
            canvas.drawPath(mPath, mPaint);
        }
    }

    public RectF getRectF() {
        return mRectF;
    }

    public void setCircle(boolean isCircle) {
        this.isCircle = isCircle;
    }
}
