package com.example.meydoon.helper;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by hooma on 2/21/2017.
 */
public class MyViewPager extends ViewPager {

    private Boolean isPagingEnabled;


    public MyViewPager(Context context) {
        super(context);
        this.isPagingEnabled = true;
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isPagingEnabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean enabled) {
        this.isPagingEnabled = enabled;
    }
}
