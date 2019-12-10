package com.kproduce.roundcorners;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author kuanggang on 2019/12/10
 */
public class RoundRelativeLayout extends RelativeLayout {

    private RoundHelper mHelper = new RoundHelper();

    public RoundRelativeLayout(Context context) {
        this(context, null);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
