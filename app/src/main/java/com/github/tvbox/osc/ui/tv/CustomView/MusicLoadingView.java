package com.github.tvbox.osc.ui.tv.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.github.tvbox.osc.Rstyleable;
import java.util.ArrayList;
import java.util.List;
public class MusicLoadingView extends View {
    public Paint c;
    public RectF d;
    public boolean e;
    public int f;
    public float g;
    public int h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public int n;
    public float o;
    public int p;
    public int q;
    public boolean r;
    public int s;
    public boolean t;
    public int u;
    public boolean v;
    public List<Integer> w;
    public List<Integer> x;
    public List<Integer> y;
    public Runnable z;

    public MusicLoadingView(Context context) {
        super(context);
        this.e = false;
        this.p = 0;
        this.w = new ArrayList();
        this.x = new ArrayList();
        this.y = new ArrayList();
    }

    public MusicLoadingView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e = false;
        this.p = 0;
        this.w = new ArrayList();
        this.x = new ArrayList();
        this.y = new ArrayList();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, Rstyleable.MusicLoadingView);
        this.h = obtainStyledAttributes.getInteger(3, 1);
        this.i = (int) obtainStyledAttributes.getDimension(8, d(3.0f));
        this.f = (int) obtainStyledAttributes.getDimension(7, d(7.0f));
        this.k = obtainStyledAttributes.getColor(2, Color.parseColor("#FFFFE105"));
        this.m = (int) obtainStyledAttributes.getDimension(4, d(50.0f));
        this.n = (int) obtainStyledAttributes.getDimension(5, d(10.0f));
        this.g = obtainStyledAttributes.getFloat(1, 0.1f) * this.m;
        this.j = obtainStyledAttributes.getInt(0, d(6.0f));
        this.o = obtainStyledAttributes.getDimension(6, d(3.0f));
        int i = this.f;
        int i2 = this.i;
        this.l = ((i + i2) * this.h * 4) + i2;
        int i3 = this.n;
        this.q = i3;
        this.r = false;
        int i4 = this.m;
        this.s = (i3 + i4) / 2;
        this.t = true;
        this.u = i4;
        this.v = true;
        Paint paint = new Paint();
        this.c = paint;
        paint.setStyle(Paint.Style.FILL);
        this.c.setAntiAlias(true);
        this.c.setColor(this.k);
        RectF rectF = new RectF();
        this.d = rectF;
        rectF.left = 0.0f;
        rectF.right = this.i;
        for (int i5 = 0; i5 <= this.h * 4; i5++) {
            (i5 % 4 == 0 ? this.w : i5 % 2 != 0 ? this.x : this.y).add(Integer.valueOf(i5));
        }
    }

    public MusicLoadingView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.e = false;
        this.p = 0;
        this.w = new ArrayList();
        this.x = new ArrayList();
        this.y = new ArrayList();
    }

    public final int a(int i, boolean z) {
        float f = i;
        int max = (int) (z ? Math.max(f - this.g, this.n) : Math.min(f + this.g, this.m));
        this.p = max;
        return max;
    }

    public final boolean b(int i, boolean z) {
        if (z) {
            if (i <= this.n) {
                return false;
            }
            return z;
        } else if (i >= this.m) {
            return true;
        } else {
            return z;
        }
    }

    public final void c(Canvas canvas, int i) {
        int i2;
        int i3;
        RectF rectF = this.d;
        int i4 = this.i;
        float f = (this.f + i4) * i;
        rectF.left = f;
        rectF.right = f + i4;
        rectF.top = (this.m - this.p) / 2;
        rectF.bottom = i3 + i2;
        float f2 = this.o;
        canvas.drawRoundRect(rectF, f2, f2, this.c);
    }

    public int d(float f) {
        return (int) ((f * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int a = a(this.q, this.r);
        this.q = a;
        this.r = b(a, this.r);
        for (Integer num : this.w) {
            c(canvas, num.intValue());
        }
        int a2 = a(this.s, this.t);
        this.s = a2;
        this.t = b(a2, this.t);
        for (Integer num2 : this.x) {
            c(canvas, num2.intValue());
        }
        int a3 = a(this.u, this.v);
        this.u = a3;
        this.v = b(a3, this.v);
        for (Integer num3 : this.y) {
            c(canvas, num3.intValue());
        }
    }

    @Override
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(this.l, this.m);
    }
}
