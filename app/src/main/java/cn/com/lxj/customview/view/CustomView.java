package cn.com.lxj.customview.view;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * DATE 2019/4/4 14:30
 * AUTHOR lxj
 * 南無阿彌陀佛
 * Email:466970436@qq.com
 */
public class CustomView extends View {

    protected Context mCurrentContext;
    protected int mViewWidth;
    protected int mViewHeight;
    protected Paint mDefaultPaint = new Paint();
    protected TextPaint mDefaultTextPaint = new TextPaint();

    public CustomView(Context context) {
        super(context);
        this.mCurrentContext = context;
        init(null, 0);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCurrentContext = context;
        init(attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCurrentContext = context;
        init(attrs, defStyleAttr);
    }

    protected void init(AttributeSet attrs, int defStyle) {
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }
}
