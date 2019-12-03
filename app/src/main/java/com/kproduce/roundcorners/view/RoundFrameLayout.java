package com.kproduce.roundcorners.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.kproduce.roundcorners.RoundHelper;

public class RoundFrameLayout extends FrameLayout {

    private RoundHelper mHelper = new RoundHelper();

    public RoundFrameLayout(Context context) {
        this(context, null);
    }

    public RoundFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RoundFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper.init(context, attrs, this);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mHelper.clipPath(canvas, this);
    }
}
