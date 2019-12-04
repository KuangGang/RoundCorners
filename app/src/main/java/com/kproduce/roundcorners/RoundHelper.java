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

public class RoundHelper {

    private float[] mRadii;

    private Paint mPaint;
    private RectF mRectF;

    private Path mPath;
    private Path mTempPath;

    private Xfermode mXfermode;

    public void init(Context context, AttributeSet attrs, View view) {
        // 禁止硬件加速，硬件加速会有一些问题，这里禁用掉
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        if (view.getBackground() == null) {
            view.setBackgroundColor(Color.parseColor("#00000000"));
        }

        mRadii = new float[8];
        mPaint = new Paint();
        mRectF = new RectF();
        mPath = new Path();
        mTempPath = new Path();
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

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

        array.recycle();
        setRadius(radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight);
    }

    private void setRadius(int topLeftDp, int topRightDp, int bottomLeftDp, int bottomRightDp) {
        mRadii[0] = topLeftDp;
        mRadii[1] = topLeftDp;
        mRadii[2] = topRightDp;
        mRadii[3] = topRightDp;
        mRadii[4] = bottomRightDp;
        mRadii[5] = bottomRightDp;
        mRadii[6] = bottomLeftDp;
        mRadii[7] = bottomLeftDp;
    }

    public void onSizeChanged(int width, int height) {
        if (mRectF == null) {
            return;
        }
        mRectF.set(0, 0, width, height);
    }


    public void clipPath(Canvas canvas) {
        mPaint.reset();
        mPath.reset();
        mTempPath.reset();

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(mXfermode);

        mPath.addRoundRect(mRectF, mRadii, Path.Direction.CW);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTempPath.addRect(mRectF, Path.Direction.CW);
            mTempPath.op(mPath, Path.Op.DIFFERENCE);
            canvas.drawPath(mTempPath, mPaint);
        } else {
            canvas.clipPath(mPath);
        }
    }
}
