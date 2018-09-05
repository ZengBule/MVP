package com.example.jett.mvp_project.widgit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.jett.mvp_project.R;

import java.util.List;

/**
 * viewPager indicator
 */
public class ViewPagerIndicator extends LinearLayout {

    private Paint mPaint;
    private Path mPath;

    /**
     * 三角形的宽度
     */
    private int mTriangleWidth;
    /**
     * 三角形的高度
     */
    private int mTriangleHeight;

    private static final float RADIO_TRIANGLE_WIDTH = 1 / 6F;

    private int mInitTranslationX;
    private int mTranslationX;

    private static final int COUNT_DEFAULT_NUM = 4;
    private int mTabVisiableCount;

    private List<String> mTitles;
    private ViewPager mViewPager;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //获取可见tab的个数
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);

        mTabVisiableCount = a.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, COUNT_DEFAULT_NUM);

        if (mTabVisiableCount < 0) {
            mTabVisiableCount = COUNT_DEFAULT_NUM;
        }
        a.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.parseColor("#ffffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));//链接处不要太尖锐


    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 2);
        canvas.drawPath(mPath, mPaint);

        canvas.restore();

        super.dispatchDraw(canvas);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w / mTabVisiableCount * RADIO_TRIANGLE_WIDTH);
        mInitTranslationX = w / mTabVisiableCount / 2 - mTriangleWidth / 2;
        initTriangle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int cCount = getChildCount();
        if (cCount == 0) return;
        for (int i = 0; i < cCount; i++) {
            View view = getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth() / mTabVisiableCount;
            view.setLayoutParams(lp);
        }
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 初始化三角形
     */
    private void initTriangle() {
        mTriangleHeight = mTriangleWidth / 2;

        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    public void scroll(int position, float offset) {

        int tabWidth = getWidth() / mTabVisiableCount;
        mTranslationX = (int) (tabWidth * (position + offset));

        //容器移动，在tab处于移动至最后一个时
        if (position > (mTabVisiableCount - 2) && offset > 0
                && getChildCount() > mTabVisiableCount) {
            if (mTabVisiableCount != 1)
                this.scrollTo((position - (mTabVisiableCount - 1)) * tabWidth
                        + (int) (tabWidth * offset), 0);
            else {
                this.scrollTo((int) (position * tabWidth + tabWidth * offset), 0);
            }
        }

        invalidate();
    }


    public void setTabItemTitles(List<String> titles) {


        if (titles != null && titles.size() > 0) {
            this.removeAllViews();
            mTitles = titles;
            for (int i = 0; i < mTitles.size(); i++) {
                if (mTitles.size() == 3) {
                    if (i == 0) {
                        addView(generateGravityLeftTextView(mTitles.get(i)));
                    } else if (i == 1) {
                        addView(generateTextView(mTitles.get(i)));

                    } else if (i == 2) {
                        addView(generateGravityRightTextView(mTitles.get(i)));

                    }
                } else {

                    addView(generateTextView(mTitles.get(i)));
                }
            }

            setItemClickEvent();
        }

    }

    private static final int COLOR_TEXT_NORMAL = 0x77ffffff;
    private static final int COLOR_TEXT_HEIGHTLIGHT = 0xffffffff;

    public void setVisibleTabCount(int visibleCount) {
        mTabVisiableCount = visibleCount;
    }

    private View generateTextView(String title) {
        TextView tv = new TextView(getContext());

        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mTabVisiableCount;
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setLayoutParams(lp);
        return tv;
    }

    private View generateGravityLeftTextView(String title) {
        TextView tv = new TextView(getContext());

        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mTabVisiableCount;
        tv.setText(title);
        tv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setLayoutParams(lp);
        return tv;
    }

    private View generateGravityRightTextView(String title) {
        TextView tv = new TextView(getContext());

        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mTabVisiableCount;
        tv.setText(title);
        tv.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setLayoutParams(lp);
        return tv;
    }


    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position, positionOffset);
                heightLightTextView(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resetTextViewColor() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }

    private void heightLightTextView(int pos, float offset) {
        resetTextViewColor();
        View view = getChildAt(pos);
        if (view instanceof TextView) {

            ((TextView) view).setTextColor(COLOR_TEXT_HEIGHTLIGHT);
        }
    }

    private void setItemClickEvent() {
        for (int i = 0; i < getChildCount(); i++) {
            final int j = i;
            View view = getChildAt(i);
            if (view instanceof TextView) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(j);
                    }
                });
            }
        }
    }
}
