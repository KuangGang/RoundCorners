package com.kproduce.roundcorners.util;

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

import com.kproduce.roundcorners.R;

/**
 * @author kuanggang on 2019/12/10
 */
public class RoundHelper {

    private View mView;

    private Paint mPaint;
    private RectF mRectF;
    private RectF mStrokeRectF;

    private Path mPath;
    private Path mTempPath;

    private Xfermode mXfermode;

    private boolean isCircle;

    private float[] mRadii;
    private float[] mStrokeRadii;

    private int mWidth;
    private int mHeight;
    private int mStrokeWidth;
    private int mStrokeColor;

    private int mRadiusTopLeft;
    private int mRadiusTopRight;
    private int mRadiusBottomLeft;
    private int mRadiusBottomRight;

    public void init(Context context, AttributeSet attrs, View view) {
        if (view instanceof ViewGroup && view.getBackground() == null) {
            view.setBackgroundColor(Color.parseColor("#00000000"));
        }
        mView = view;
        mRadii = new float[8];
        mStrokeRadii = new float[8];
        mPaint = new Paint();
        mRectF = new RectF();
        mStrokeRectF = new RectF();
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

        mRadiusTopLeft = array.getDimensionPixelSize(R.styleable.RoundCorner_rTopLeftRadius, radiusTop > 0 ? radiusTop : radiusLeft);
        mRadiusTopRight = array.getDimensionPixelSize(R.styleable.RoundCorner_rTopRightRadius, radiusTop > 0 ? radiusTop : radiusRight);
        mRadiusBottomLeft = array.getDimensionPixelSize(R.styleable.RoundCorner_rBottomLeftRadius, radiusBottom > 0 ? radiusBottom : radiusLeft);
        mRadiusBottomRight = array.getDimensionPixelSize(R.styleable.RoundCorner_rBottomRightRadius, radiusBottom > 0 ? radiusBottom : radiusRight);

        mStrokeWidth = array.getDimensionPixelSize(R.styleable.RoundButton_rStrokeWidth, 0);
        mStrokeColor = array.getColor(R.styleable.RoundButton_rStrokeColor, mStrokeColor);

        array.recycle();
        if (!isCircle) {
            setRadius();
        }
    }

    private void setRadius() {
        mRadii[0] = mRadii[1] = mRadiusTopLeft - mStrokeWidth;
        mRadii[2] = mRadii[3] = mRadiusTopRight - mStrokeWidth;
        mRadii[4] = mRadii[5] = mRadiusBottomRight - mStrokeWidth;
        mRadii[6] = mRadii[7] = mRadiusBottomLeft - mStrokeWidth;

        mStrokeRadii[0] = mStrokeRadii[1] = mRadiusTopLeft;
        mStrokeRadii[2] = mStrokeRadii[3] = mRadiusTopRight;
        mStrokeRadii[4] = mStrokeRadii[5] = mRadiusBottomRight;
        mStrokeRadii[6] = mStrokeRadii[7] = mRadiusBottomLeft;
    }

    public void onSizeChanged(int width, int height) {
        mWidth = width;
        mHeight = height;

        if (isCircle) {
            int radius = Math.min(height, width) / 2 - mStrokeWidth;
            mRadiusTopLeft = radius;
            mRadiusTopRight = radius;
            mRadiusBottomRight = radius;
            mRadiusBottomLeft = radius;
            setRadius();
        }
        if (mRectF != null) {
            mRectF.set(0, 0, width, height);
        }
        if (mStrokeRectF != null) {
            mStrokeRectF.set((mStrokeWidth * 1.0f / 2), (mStrokeWidth * 1.0f / 2), width - mStrokeWidth * 1.0f / 2, height - mStrokeWidth * 1.0f / 2);
        }
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
            mPath.addRoundRect(mStrokeRectF, mStrokeRadii, Path.Direction.CCW);
            canvas.drawPath(mPath, mPaint);
        }
    }

    public void setCircle(boolean isCircle) {
        this.isCircle = isCircle;
    }

    public void setRadius(int radius) {
        mRadiusTopLeft = radius;
        mRadiusTopRight = radius;
        mRadiusBottomLeft = radius;
        mRadiusBottomRight = radius;
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setRadius(int radiusTopLeft, int radiusTopRight, int radiusBottomLeft, int radiusBottomRight) {
        mRadiusTopLeft = radiusTopLeft;
        mRadiusTopRight = radiusTopRight;
        mRadiusBottomLeft = radiusBottomLeft;
        mRadiusBottomRight = radiusBottomRight;
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setRadiusLeft(int radius) {
        mRadiusTopLeft = radius;
        mRadiusBottomLeft = radius;
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setRadiusRight(int radius) {
        mRadiusTopRight = radius;
        mRadiusBottomRight = radius;
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setRadiusTop(int radius) {
        mRadiusTopLeft = radius;
        mRadiusTopRight = radius;
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setRadiusBottom(int radius) {
        mRadiusBottomLeft = radius;
        mRadiusBottomRight = radius;
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setRadiusTopLeft(int radius) {
        mRadiusTopLeft = radius;
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setRadiusTopRight(int radius) {
        mRadiusTopRight = radius;
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setRadiusBottomLeft(int radius) {
        mRadiusBottomLeft = radius;
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setRadiusBottomRight(int radius) {
        mRadiusBottomRight = radius;
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setStrokeWidth(int width) {
        mStrokeWidth = width;
        if (mView != null) {
            setRadius();
            onSizeChanged(mWidth, mHeight);
            mView.invalidate();
        }
    }

    public void setStrokeColor(int color) {
        mStrokeColor = color;
        if (mView != null) {
            mView.invalidate();
        }
    }

    public void setStrokeWidthColor(int width, int color) {
        mStrokeWidth = width;
        mStrokeColor = color;
        if (mView != null) {
            setRadius();
            onSizeChanged(mWidth, mHeight);
            mView.invalidate();
        }
    }
}
