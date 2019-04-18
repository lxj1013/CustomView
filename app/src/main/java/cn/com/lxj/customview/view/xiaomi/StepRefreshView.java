package cn.com.lxj.customview.view.xiaomi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import cn.com.lxj.customview.R;
import cn.com.lxj.customview.util.DensityUtils;
import cn.com.lxj.customview.util.Utils;
import cn.com.lxj.customview.view.CustomView;

/**
 * DATE 2019/4/8 15:56
 * AUTHOR lxj
 * 南無阿彌陀佛
 * Email:466970436@qq.com
 */
public class StepRefreshView extends CustomView {

    private static final String TAG = "StepRefreshView";
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 500;
    private static final int ROTATE_DURATION = 3 * 1000;
    private static final int DEFAULT_LINE_NUMBER = 10;
    private static final int DEFAULT_CXY_RANGE = 15;

    private float mRadius;
    private float mRotate;

    private float[] randomCx;
    private float[] randomCy;
    private float[] randomRadius;
    private ObjectAnimator rotateAnim;
    private SweepGradient mSweepGradient;
    private float lastRotate = 0;

    public StepRefreshView(Context context) {
        super(context);
    }

    public StepRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StepRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(AttributeSet attrs, int defStyle) {
        super.init(attrs, defStyle);
        mSweepGradient = new SweepGradient(0, 0, getResources().getColor(R.color.colorTransparent), getResources().getColor(R.color.colorWhite));
//        mDefaultPaint.setAntiAlias(true);
        mDefaultPaint.setStyle(Paint.Style.STROKE);
        mDefaultPaint.setStrokeWidth(2);
        mDefaultPaint.setShader(mSweepGradient);

        mDefaultTextPaint.setColor(Color.WHITE);
//        mDefaultTextPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(Utils.measureWidth(widthMeasureSpec, DEFAULT_WIDTH), Utils.measureHeight(heightMeasureSpec, DEFAULT_HEIGHT));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        randomCx = new float[DEFAULT_LINE_NUMBER];
        randomCy = new float[DEFAULT_LINE_NUMBER];
        randomRadius = new float[DEFAULT_LINE_NUMBER];
        mRadius = (float) (mViewWidth < mViewHeight ? mViewWidth : mViewHeight) / 3;
        for (int i = 0; i < DEFAULT_LINE_NUMBER; i++) {
            randomCx[i] = (float) ((Math.random() * 2 - 1) * DEFAULT_CXY_RANGE);
            randomCy[i] = (float) ((Math.random() * 2 - 1) * DEFAULT_CXY_RANGE);
            randomRadius[i] = (float) ((Math.random() * 0.05 + 0.95) * mRadius);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mViewWidth >> 1, mViewHeight >> 1);
        canvas.rotate(mRotate);
//        reset();

        drawCircle(canvas);
//        drawParticle(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setAnimStop();
    }


    private void reset() {
        mDefaultPaint.setStyle(Paint.Style.STROKE);
        mDefaultPaint.setShader(mSweepGradient);
    }

    float cx[] = new float[DEFAULT_LINE_NUMBER];
    float radius[] = new float[DEFAULT_LINE_NUMBER];

    private void drawCircle(Canvas canvas) {
        mDefaultTextPaint.setAlpha(225);
        for (int i = 0; i < DEFAULT_LINE_NUMBER; i++) {
            canvas.drawCircle(randomCx[i], randomCy[i], randomRadius[i], mDefaultPaint);
            if (lastRotate >= mRotate % 30) {
                cx[i] = randomCx[i] + randomRadius[i] * (float) ((Math.random() * 0.3) + 0.8);
                radius[i] = randomRadius[i] / 20 * (float) ((Math.random() * 0.5) + 0.5);
            }
            lastRotate = mRotate % 30;
            canvas.drawCircle(cx[i], randomCy[i], radius[i], mDefaultTextPaint);

            canvas.rotate(3);
            mDefaultTextPaint.setAlpha((int) (225 * Math.random()));
        }
    }

    private void drawParticle(Canvas canvas) {
        mDefaultPaint.setStyle(Paint.Style.FILL);
        mDefaultPaint.setShader(null);
        mDefaultPaint.setColor(Color.WHITE);
        for (int i = 0; i < DEFAULT_LINE_NUMBER; i++) {
            canvas.drawCircle(randomCx[i] + randomRadius[i] * 1.3f, randomCy[i], randomRadius[i] / 20, mDefaultPaint);
            canvas.rotate(3);
        }
    }

    public void setRotate(float rotate) {
        mRotate = rotate;
        invalidate();
    }

    public void setAnimStart() {
        if (rotateAnim != null) {
            if (rotateAnim.isRunning()) {
                rotateAnim.cancel();
            }
        } else {
            rotateAnim = ObjectAnimator.ofFloat(this, "rotate", 0, 360);
            rotateAnim.setDuration(ROTATE_DURATION);
            rotateAnim.setInterpolator(new LinearInterpolator());
            rotateAnim.setRepeatMode(ValueAnimator.RESTART);
            rotateAnim.setRepeatCount(ValueAnimator.INFINITE);
            rotateAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    Toast.makeText(mCurrentContext, "stop", Toast.LENGTH_SHORT).show();
                }
            });
        }
        rotateAnim.start();

    }

    public void setAnimStop() {
        if (rotateAnim != null) {
            if (rotateAnim.isRunning()) {
                rotateAnim.cancel();
            }
            rotateAnim = null;
        }
    }
}
