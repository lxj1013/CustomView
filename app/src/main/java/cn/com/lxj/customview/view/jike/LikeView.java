package cn.com.lxj.customview.view.jike;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import cn.com.lxj.customview.R;

import static cn.com.lxj.customview.util.Utils.measureHeight;
import static cn.com.lxj.customview.util.Utils.measureWidth;

/**
 * DATE 2019/4/1 14:44
 * AUTHOR lxj
 * 南無阿彌陀佛
 * Email:466970436@qq.com
 */
public class LikeView extends View {

    private static final String TAG = "JiKeLikeView";
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;
    private static final float SCALE_MIN = 0.8f;
    private static final float SCALE_MAX = 1.0f;
    private static final int SCALE_DURATION = 200;
    private int mViewWidth;
    private int mViewHeight;

    private Paint mPaint;

    private Bitmap mLikedBitmap;
    private Bitmap mLikedShiningBitmap;
    private Bitmap mUnLikedBitmap;
    private float mScale = 1.0f;
    private boolean isLike = false;
    private boolean isUp = false;//缩小时是否已经action_up
    private boolean isScaleMin = false;//是否在缩小
    private ObjectAnimator scaleMinAnim;
    private ObjectAnimator scaleMaxAnim;
    private LikeClickListener mLikeClickListener;

    public LikeView(Context context) {
        super(context);
        init(null, 0);
    }

    public LikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LikeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        mViewWidth = 0;
        mViewHeight = 0;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LikeView, defStyle, 0);

        int likedSrc = a.getResourceId(R.styleable.LikeView_liked_src, R.drawable.ic_messages_like_selected);
        int likedShiningSrc = a.getResourceId(R.styleable.LikeView_liked_shining_src, R.drawable.ic_messages_like_selected_shining);
        int unLikedSrc = a.getResourceId(R.styleable.LikeView_un_liked_src, R.drawable.ic_messages_like_unselected);

        setLikedBitmap(likedSrc);
        setLikedShiningBitmap(likedShiningSrc);
        setUnLikedBitmap(unLikedSrc);

        a.recycle();

        scaleMinAnim = ObjectAnimator.ofFloat(this, "scale", 1.0f, SCALE_MIN);
        scaleMinAnim.setDuration(SCALE_DURATION);
        scaleMinAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isUp) {
                    isScaleMin = false;
                    scaleMaxAnim.start();
                }
            }
        });
        scaleMaxAnim = ObjectAnimator.ofFloat(this, "scale", SCALE_MIN, SCALE_MAX);
        scaleMaxAnim.setDuration(SCALE_DURATION);
        scaleMaxAnim.setInterpolator(new OvershootInterpolator());
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


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mViewWidth >> 1, mViewHeight >> 1);
        canvas.scale(mScale, mScale);
//        canvas.drawBitmap(mLikedBitmap, (-mLikedBitmap.getWidth()) >> 1, (-mLikedBitmap.getHeight()) >> 1, mPaint);
//        canvas.drawBitmap(mUnLikedBitmap, (-mUnLikedBitmap.getWidth()) >> 1, (-mUnLikedBitmap.getHeight()) >> 1, mPaint);
        mPaint.setAlpha(255);
        if (isLike) {
            if (isScaleMin) {
                canvas.drawBitmap(mUnLikedBitmap, (-mUnLikedBitmap.getWidth()) >> 1, (-mUnLikedBitmap.getHeight()) >> 1, mPaint);
            } else {
                canvas.drawBitmap(mLikedBitmap, (-mLikedBitmap.getWidth()) >> 1, (-mLikedBitmap.getHeight()) >> 1, mPaint);
                canvas.drawBitmap(mLikedShiningBitmap, (-mLikedShiningBitmap.getWidth()) >> 1, (-mLikedShiningBitmap.getHeight() - mLikedBitmap.getHeight()) >> 1, mPaint);
            }
        } else {
            if (isScaleMin) {
                canvas.drawBitmap(mLikedBitmap, (-mLikedBitmap.getWidth()) >> 1, (-mLikedBitmap.getHeight()) >> 1, mPaint);
                mPaint.setAlpha((int) (mScale * 255 * 5 - 255 * 4));
                canvas.drawBitmap(mLikedShiningBitmap, (-mLikedShiningBitmap.getWidth()) >> 1, (-mLikedShiningBitmap.getHeight() - mLikedBitmap.getHeight()) >> 1, mPaint);
            } else {
                canvas.drawBitmap(mUnLikedBitmap, (-mUnLikedBitmap.getWidth()) >> 1, (-mUnLikedBitmap.getHeight()) >> 1, mPaint);
            }
        }
    }

    private void setScale(float scale) {
        mScale = scale;
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Toast.makeText(getContext(), event.toString(), Toast.LENGTH_SHORT).show();
//        Log.e(TAG, "onTouchEvent: " + event.toString());

//        invalidate();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (mLikeClickListener != null) {
                mLikeClickListener.onLikeDown();
            }
            isLike = !isLike;
            isScaleMin = true;
            isUp = false;
            scaleMinAnim.start();
        } else if (action == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            if (x < 0 || y < 0 || x > mViewWidth || y > mViewHeight) {
                isLike = !isLike;
            }
            if (scaleMinAnim.isRunning()) {
                isUp = true;
            } else {
                isScaleMin = false;
                scaleMaxAnim.start();
            }
            if (mLikeClickListener != null) {
                mLikeClickListener.onLikeUp(isUp);
            }
        }
        return true;
    }

    public void setLikedBitmap(@IdRes int likedSrc) {
        mLikedBitmap = BitmapFactory.decodeResource(getResources(), likedSrc);
    }

    public void setLikedShiningBitmap(@IdRes int likedShiningSrc) {
        mLikedShiningBitmap = BitmapFactory.decodeResource(getResources(), likedShiningSrc);
    }

    public void setUnLikedBitmap(@IdRes int unLikedSrc) {
        mUnLikedBitmap = BitmapFactory.decodeResource(getResources(), unLikedSrc);
    }

    public void setLikeClickListener(LikeClickListener likeClickListener) {
        mLikeClickListener = likeClickListener;
    }

}
