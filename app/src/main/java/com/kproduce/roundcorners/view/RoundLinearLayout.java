package com.kproduce.roundcorners.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.kproduce.roundcorners.R;

public class RoundLinearLayout extends LinearLayout {

    private Path mPath;
    private Path mTempPath;
    private Paint mPaint;
    private float[] mRadii;

    public RoundLinearLayout(Context context) {
        this(context, null);
    }

    public RoundLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RoundLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // 禁止硬件加速，硬件加速会有一些问题，这里禁用掉
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        if (getBackground() == null) {
            setBackgroundColor(Color.parseColor("#00000000"));
        }

        mRadii = new float[8];
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPath = new Path();
        mTempPath = new Path();

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

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        clipPath(canvas);
    }

    private void clipPath(Canvas canvas) {
        mPath.reset();
        mPath.addRoundRect(new RectF(0, 0, getWidth(), getHeight()),
                mRadii,
                Path.Direction.CW);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTempPath.reset();
            mTempPath.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CW);
            mTempPath.op(mPath, Path.Op.DIFFERENCE);
            canvas.drawPath(mTempPath, mPaint);
        } else {
            canvas.clipPath(mPath);
        }
    }

}
