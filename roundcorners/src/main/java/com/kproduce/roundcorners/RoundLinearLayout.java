package com.kproduce.roundcorners;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author kuanggang on 2019/12/10
 */
public class RoundLinearLayout extends LinearLayout {

    private RoundHelper mHelper = new RoundHelper();

    public RoundLinearLayout(Context context) {
        this(context, null);
    }

    public RoundLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RoundLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper.init(context, attrs, this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHelper.onSizeChanged(w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(mHelper.getRectF(), null, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        mHelper.drawPath(canvas);
        canvas.restore();
    }
}
