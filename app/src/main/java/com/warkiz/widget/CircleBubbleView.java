package com.warkiz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import androidx.base.a5.b;
public class CircleBubbleView extends View {
    public int c;
    public int d;
    public float e;
    public Context f;
    public Path g;
    public Paint h;
    public float i;
    public float j;
    public float k;
    public String l;

    public CircleBubbleView(Context context, float f, int i, int i2, String str) {
        super(context, null, 0);
        Rect rect;
        this.f = context;
        this.e = f;
        this.c = i;
        this.d = i2;
        Paint paint = new Paint();
        this.h = paint;
        paint.setAntiAlias(true);
        this.h.setStrokeWidth(1.0f);
        this.h.setTextAlign(Paint.Align.CENTER);
        this.h.setTextSize(this.e);
        this.h.getTextBounds(str, 0, str.length(), new Rect());
        this.i = b.z(this.f, 4.0f) + rect.width();
        float z = b.z(this.f, 36.0f);
        if (this.i < z) {
            this.i = z;
        }
        this.k = rect.height();
        this.j = this.i * 1.2f;
        this.g = new Path();
        float f2 = this.i;
        this.g.arcTo(new RectF(0.0f, 0.0f, f2, f2), 135.0f, 270.0f);
        this.g.lineTo(this.i / 2.0f, this.j);
        this.g.close();
    }

    @Override
    public void onDraw(Canvas canvas) {
        this.h.setColor(this.d);
        canvas.drawPath(this.g, this.h);
        this.h.setColor(this.c);
        canvas.drawText(this.l, this.i / 2.0f, (this.k / 4.0f) + (this.j / 2.0f), this.h);
    }

    @Override
    public void onMeasure(int i, int i2) {
        setMeasuredDimension((int) this.i, (int) this.j);
    }

    public void setProgress(String str) {
        this.l = str;
        invalidate();
    }
}
