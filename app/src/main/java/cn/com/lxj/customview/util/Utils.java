package cn.com.lxj.customview.util;

import android.view.View;

/**
 * DATE 2019/4/3 17:39
 * AUTHOR lxj
 * 南無阿彌陀佛
 * Email:466970436@qq.com
 */
public class Utils {
    public static int measureWidth(int widthMeasureSpec,int defaultWidth) {
        int width;
        int specMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int specSize = View.MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == View.MeasureSpec.EXACTLY) {
            width = specSize;
        } else {
            width = defaultWidth;
            if (specMode == View.MeasureSpec.AT_MOST) {
                width = Math.min(width, specSize);
            }
        }
        return width;
    }

    public static int measureHeight(int heightMeasureSpec,int defaultHeight) {
        int height;
        int specMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int specSize = View.MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == View.MeasureSpec.EXACTLY) {
            height = specSize;
        } else {
            height = defaultHeight;
            if (specMode == View.MeasureSpec.AT_MOST) {
                height = Math.min(height, specSize);
            }
        }
        return height;
    }
}
