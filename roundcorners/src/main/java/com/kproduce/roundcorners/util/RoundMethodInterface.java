package com.kproduce.roundcorners.util;

/**
 * @author kuanggang
 */
public interface RoundMethodInterface {
    void setRadius(int radius);

    void setRadius(int radiusTopLeft, int radiusTopRight, int radiusBottomLeft, int radiusBottomRight);

    void setRadiusLeft(int radius);

    void setRadiusRight(int radius);

    void setRadiusTop(int radius);

    void setRadiusBottom(int radius);

    void setRadiusTopLeft(int radius);

    void setRadiusTopRight(int radius);

    void setRadiusBottomLeft(int radius);

    void setRadiusBottomRight(int radius);

    void setStrokeWidth(int width);

    void setStrokeColor(int color);

    void setStrokeWidthColor(int width, int color);
}
