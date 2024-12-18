package com.warkiz.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.base.a5.b;
import androidx.base.q4.e;
public class IndicatorStayLayout extends LinearLayout {
    public IndicatorStayLayout(Context context) {
        this(context, null);
    }

    public IndicatorStayLayout(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public IndicatorStayLayout(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setOrientation(1);
    }

    @Override
    public void onFinishInflate() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (childAt instanceof IndicatorSeekBar) {
                IndicatorSeekBar indicatorSeekBar = (IndicatorSeekBar) childAt;
                indicatorSeekBar.setIndicatorStayAlways(true);
                View indicatorContentView = indicatorSeekBar.getIndicatorContentView();
                if (indicatorContentView == null) {
                    throw new IllegalStateException("Can not find any indicator in the IndicatorSeekBar, please make sure you have called the attr: SHOW_INDICATOR_TYPE for IndicatorSeekBar and the value is not IndicatorType.NONE.");
                }
                if (!(indicatorContentView instanceof IndicatorSeekBar)) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                    marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, b.z(indicatorSeekBar.getContext(), 2.0f) - indicatorSeekBar.getPaddingTop());
                    addView(indicatorContentView, childCount, marginLayoutParams);
                    indicatorSeekBar.V.setVisibility(View.INVISIBLE);
                    indicatorSeekBar.postDelayed(new e(indicatorSeekBar), 300L);
                } else {
                    throw new IllegalStateException("IndicatorSeekBar can not be a contentView for Indicator in case this inflating loop.");
                }
            }
        }
        super.onFinishInflate();
    }

    @Override
    public void setOrientation(int i) {
        if (i != 1) {
            throw new IllegalArgumentException("IndicatorStayLayout is always vertical and does not support horizontal orientation");
        }
        super.setOrientation(i);
    }
}
