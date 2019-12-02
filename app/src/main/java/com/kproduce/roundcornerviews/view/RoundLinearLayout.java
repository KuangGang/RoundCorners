package com.kproduce.roundcornerviews.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.kproduce.roundcornerviews.R;

public class RoundLinearLayout extends LinearLayout {

    private Context mContext;
    private Path mPath;
    private float[] mRadii;

    public RoundLinearLayout(Context context) {
        this(context, null);
    }

    public RoundLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RoundLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        mRadii = new float[8];
        mPath = new Path();
        mPath.setFillType(Path.FillType.EVEN_ODD);

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

    public void setRadius(int radiusDp) {
        setRadius(radiusDp, radiusDp, radiusDp, radiusDp);
    }

    public void setRadius(int topLeftDp, int topRightDp, int bottomLeftDp, int bottomRightDp) {
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
        int saveCount = canvas.save();
        checkPathChanged();
        canvas.clipPath(mPath);
        super.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    private void checkPathChanged() {
        mPath.reset();
        mPath.addRoundRect(new RectF(0, 0, getWidth(), getHeight()),
                mRadii,
                Path.Direction.CW);
    }

}
