package cn.com.lxj.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.AttributeSet;

import cn.com.lxj.customview.R;

import static android.provider.Telephony.Mms.Part.TEXT;
import static cn.com.lxj.customview.util.DensityUtils.px2sp;
import static cn.com.lxj.customview.util.DensityUtils.sp2px;
import static cn.com.lxj.customview.util.Utils.measureHeight;
import static cn.com.lxj.customview.util.Utils.measureWidth;

/**
 * DATE 2019/4/4 14:28
 * AUTHOR lxj
 * 南無阿彌陀佛
 * Email:466970436@qq.com
 */
public class DrawTextTestView extends CustomView {

    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 100;

    private String chNumber = "壹贰叁肆伍陆柒捌玖拾佰仟万亿元圆角分零整";
    private String chSymbol = "【】、；‘’，。、《》？：“”｛｝|~·";
    private String enSymbol = "`~[]/;';./<>?:\\";
    private String number = "0123456789";
    private String english = "abcdefghijklmnopqrstuvwxyz";
    private String upEnglish = "ABCEDFGHIJKLMNOPQRSTUVWXYZ";

    private Paint.FontMetrics mFontMetrics;
    private float ascent;
    private float bottom;
    private float descent;
    private float leading;
    private float top;

    public DrawTextTestView(Context context) {
        super(context);
        init();
    }

    public DrawTextTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawTextTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDefaultPaint.setAntiAlias(true);
        mDefaultPaint.setColor(getResources().getColor(R.color.colorGreen));
        mDefaultPaint.setTextSize(sp2px(mCurrentContext, 14));

        mDefaultTextPaint.setAntiAlias(true);
        mDefaultTextPaint.setTextAlign(Paint.Align.CENTER);
        mDefaultTextPaint.setTextSize(sp2px(mCurrentContext, 80));
        mFontMetrics = mDefaultTextPaint.getFontMetrics();
        ascent = mFontMetrics.ascent;
        bottom = mFontMetrics.bottom;
        descent = mFontMetrics.descent;
        leading = mFontMetrics.leading;
        top = mFontMetrics.top;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec, DEFAULT_WIDTH), measureHeight(heightMeasureSpec, DEFAULT_HEIGHT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mViewWidth >> 1, mViewHeight >> 1);
        canvas.drawLine(-mViewWidth >> 1, 0, mViewWidth >> 1, 0, mDefaultPaint);
        canvas.drawLine(0, -mViewHeight >> 1, 0, mViewHeight >> 1, mDefaultPaint);

        mDefaultPaint.setColor(getResources().getColor(R.color.colorRed));
        canvas.drawText("ascent", -mViewWidth >> 1, ascent, mDefaultPaint);
        canvas.drawLine(-mViewWidth >> 1, ascent, mViewWidth >> 1, ascent, mDefaultPaint);

        mDefaultPaint.setColor(getResources().getColor(R.color.colorOrange));
        canvas.drawText("bottom", -mViewWidth >> 1, bottom + 30, mDefaultPaint);
        canvas.drawLine(-mViewWidth >> 1, bottom, mViewWidth >> 1, bottom, mDefaultPaint);

        mDefaultPaint.setColor(getResources().getColor(R.color.colorPurple));
        canvas.drawText("descent", -mViewWidth >> 1, descent, mDefaultPaint);
        canvas.drawLine(-mViewWidth >> 1, descent, mViewWidth >> 1, descent, mDefaultPaint);

        mDefaultPaint.setColor(getResources().getColor(R.color.colorBlue));
        canvas.drawText("leading", -mViewWidth >> 1, leading, mDefaultPaint);
        canvas.drawLine(-mViewWidth >> 1, leading, mViewWidth >> 1, leading, mDefaultPaint);

        mDefaultPaint.setColor(getResources().getColor(R.color.colorIndigo));
        canvas.drawText("top", -mViewWidth >> 1, top, mDefaultPaint);
        canvas.drawLine(-mViewWidth >> 1, top, mViewWidth >> 1, top, mDefaultPaint);

//        canvas.restore();
//        StaticLayout mStaticLayout = new StaticLayout(english, mDefaultTextPaint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
//        mStaticLayout.draw(canvas);
//
        canvas.drawText("ǚaA8jk您", 0, 0, mDefaultTextPaint);
    }
}
