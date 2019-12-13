package com.kproduce.roundcorners;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @author kuanggang on 2019/12/10
 */
public class RoundViewPager extends ViewPager {

    private RoundHelper mHelper = new RoundHelper();

    public RoundViewPager(Context context) {
        this(context, null);
    }

    public RoundViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
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
