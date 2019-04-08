package cn.com.lxj.customview.view.jike;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static cn.com.lxj.customview.util.Utils.measureHeight;
import static cn.com.lxj.customview.util.Utils.measureWidth;

/**
 * DATE 2019/4/3 17:37
 * AUTHOR lxj
 * 南無阿彌陀佛
 * Email:466970436@qq.com
 */
public class LikeCountView extends View implements LikeClickListener {
    private static final String TAG = "LikeCountView";

    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 100;
    private static final int SCROLL_DURATION = 200;
    private int mViewWidth;
    private int mViewHeight;
    private Paint mPaint;
    private Paint.FontMetrics mFontMetrics;
    private int count = 0;
    private float mTextHeight;
    private float mTextWidth;
    private float mUnChangeTextWidth;
    private StringBuilder mUnChangingStr;
    private String mOldStr = "";
    private String mNewStr = "";
    private boolean isUpChange;
    private boolean isAdd = false;

    private float mOldTextY = 0;
    private float mNewTextY = 0;

    private float mTextScrollY = 0;
    private float mTextScrollLength = 0;

    private ObjectAnimator mTextAnim;

    public LikeCountView(Context context) {
        super(context);
    }

    public LikeCountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LikeCountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawLine(0, mViewHeight >> 1, mViewWidth, mViewHeight >> 1, mPaint);
//        canvas.drawLine(mViewWidth >> 1, 0, mViewWidth >> 1, mViewHeight, mPaint);
        canvas.translate(mViewWidth >> 1, mViewHeight >> 1);
//        float textWidth = mPaint.measureText("0123456789");
//        canvas.drawText("0123456789", -textWidth / 2, mTextHeight / 2, mPaint);
        canvas.drawText(mUnChangingStr.toString(), -mTextWidth / 2, mTextHeight / 2, mPaint);
        canvas.drawText(mOldStr, -mTextWidth / 2 + mUnChangeTextWidth, mTextHeight / 2 + mTextScrollY, mPaint);
        canvas.drawText(mNewStr, -mTextWidth / 2 + mUnChangeTextWidth, mTextHeight / 2 + mNewTextY + mTextScrollY, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec, DEFAULT_WIDTH), measureHeight(heightMeasureSpec, DEFAULT_HEIGHT));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(45);
        mFontMetrics = mPaint.getFontMetrics();
        mTextHeight = (Math.abs(mFontMetrics.ascent) - Math.abs(mFontMetrics.descent));
        mUnChangingStr = new StringBuilder();
        mOldStr = String.valueOf(count);
        mTextWidth = mPaint.measureText(mOldStr);
    }

    @Override
    public void onLikeDown() {
//        Log.e(TAG, "onLikeDown: ");
//        setCount(count);
    }

    @Override
    public void onLikeUp(boolean isUp) {
//        Log.e(TAG, "onLikeUp: " + isUp);
        mTextScrollLength = -(mViewHeight + mTextHeight) / 2;
        mNewTextY = -(mTextHeight + mViewHeight) / 2;
        if (isUp) {
            if (!isAdd) {
                count++;
                mNewTextY = -mNewTextY;
            } else {
                count--;
                mTextScrollLength = -mTextScrollLength;
            }
            isAdd = !isAdd;
            setTextString(count);
//            Log.e(TAG, "onLikeUp: un=" + mUnChangingStr.toString() + " old=" + mOldStr + " new=" + mNewStr);
            mTextAnim = ObjectAnimator.ofFloat(this, "textScrollY", 0.0f, mTextScrollLength);
            mTextAnim.setDuration(SCROLL_DURATION);
            mTextAnim.start();
        }
    }

    private void setTextString(int count) {
        String strNew = String.valueOf(count);
        String strOld;
        mUnChangingStr.setLength(0);
        if (isAdd) {
            strOld = String.valueOf(count - 1);
        } else {
            strOld = String.valueOf(count + 1);
        }
        if (strNew.length() != strOld.length()) {
            mOldStr = strOld;
            mNewStr = strNew;
        } else {
            for (int i = 0; i < strNew.length(); i++) {
                if (strOld.toCharArray()[i] == strNew.toCharArray()[i]) {
                    mUnChangingStr.append(strNew.toCharArray()[i]);
                } else {
                    mOldStr = strOld.substring(i);
                    mNewStr = strNew.substring(i);
                    break;
                }
            }
        }
        mTextWidth = mPaint.measureText(strNew.length() > strOld.length() ? strNew : strOld);
        mUnChangeTextWidth = mPaint.measureText(mUnChangingStr.toString());
    }


    public int getCount() {
        return count;
    }


    public void setCount(int count) {
        this.count = count;
        mUnChangingStr.setLength(0);
        mUnChangeTextWidth = 0;
        mNewStr = mOldStr = String.valueOf(count);
        mTextWidth = mPaint.measureText(mOldStr);
        invalidate();
    }


    public void setTextScrollY(float textScrollY) {
        mTextScrollY = textScrollY;
        invalidate();
    }
}
