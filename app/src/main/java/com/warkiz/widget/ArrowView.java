package com.warkiz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import androidx.base.a5.b;
public class ArrowView extends View {
    public final int c;
    public final int d;
    public final Path e;
    public final Paint f;

    public ArrowView(Context context) {
        this(context, null);
    }

    public ArrowView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ArrowView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int z = b.z(context, 12.0f);
        this.c = z;
        int z2 = b.z(context, 7.0f);
        this.d = z2;
        Path path = new Path();
        this.e = path;
        path.moveTo(0.0f, 0.0f);
        path.lineTo(z, 0.0f);
        path.lineTo(z / 2.0f, z2);
        path.close();
        Paint paint = new Paint();
        this.f = paint;
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1.0f);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawPath(this.e, this.f);
    }

    @Override
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(this.c, this.d);
    }

    public void setColor(int i) {
        this.f.setColor(i);
        invalidate();
    }
}
