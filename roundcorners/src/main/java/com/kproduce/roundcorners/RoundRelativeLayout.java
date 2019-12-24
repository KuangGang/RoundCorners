package com.kproduce.roundcorners;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.kproduce.roundcorners.util.RoundHelper;
import com.kproduce.roundcorners.util.RoundMethodInterface;

/**
 * @author kuanggang on 2019/12/10
 */
public class RoundRelativeLayout extends RelativeLayout implements RoundMethodInterface {

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
        mHelper.preDraw(canvas);
        super.draw(canvas);
        mHelper.drawPath(canvas);
    }

    @Override
    public void setRadius(int radius) {
        mHelper.setRadius(radius);
    }

    @Override
    public void setRadius(int radiusTopLeft, int radiusTopRight, int radiusBottomLeft, int radiusBottomRight) {
        mHelper.setRadius(radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight);
    }

    @Override
    public void setRadiusLeft(int radius) {
        mHelper.setRadiusLeft(radius);
    }

    @Override
    public void setRadiusRight(int radius) {
        mHelper.setRadiusRight(radius);
    }

    @Override
    public void setRadiusTop(int radius) {
        mHelper.setRadiusTop(radius);
    }

    @Override
    public void setRadiusBottom(int radius) {
        mHelper.setRadiusBottom(radius);
    }

    @Override
    public void setRadiusTopLeft(int radius) {
        mHelper.setRadiusTopLeft(radius);
    }

    @Override
    public void setRadiusTopRight(int radius) {
        mHelper.setRadiusTopRight(radius);
    }

    @Override
    public void setRadiusBottomLeft(int radius) {
        mHelper.setRadiusBottomLeft(radius);
    }

    @Override
    public void setRadiusBottomRight(int radius) {
        mHelper.setRadiusBottomRight(radius);
    }

    @Override
    public void setStrokeWidth(int width) {
        mHelper.setStrokeWidth(width);
    }

    @Override
    public void setStrokeColor(int color) {
        mHelper.setStrokeColor(color);
    }

    @Override
    public void setStrokeWidthColor(int width, int color) {
        mHelper.setStrokeWidthColor(width, color);
    }
}
