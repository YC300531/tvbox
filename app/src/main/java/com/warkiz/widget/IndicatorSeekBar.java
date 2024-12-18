package com.warkiz.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.base.a.b;
import androidx.base.q4.c;
import androidx.base.q4.f;
import androidx.base.q4.g;
import androidx.media3.exoplayer.rtsp.SessionDescription;
import com.google.android.material.shadow.ShadowDrawableWrapper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
public class IndicatorSeekBar extends View {
    public boolean A;
    public Drawable A0;
    public boolean B;
    public Bitmap B0;
    public float[] C;
    public int C0;
    public boolean D;
    public boolean D0;
    public boolean E;
    public float E0;
    public int F;
    public int F0;
    public String[] G;
    public boolean G0;
    public float[] H;
    public float[] I;
    public float J;
    public int K;
    public Typeface L;
    public int M;
    public int N;
    public int O;
    public CharSequence[] P;
    public c Q;
    public int R;
    public int S;
    public boolean T;
    public int U;
    public View V;
    public View W;
    public int a0;
    public String b0;
    public Context c;
    public float[] c0;
    public Paint d;
    public int d0;
    public TextPaint e;
    public int e0;
    public f f;
    public int f0;
    public Rect g;
    public float g0;
    public float h;
    public Bitmap h0;
    public float i;
    public Bitmap i0;
    public float j;
    public Drawable j0;
    public float k;
    public int k0;
    public boolean l;
    public boolean l0;
    public g m;
    public boolean m0;
    public int n;
    public int n0;
    public int o;
    public boolean o0;
    public int p;
    public RectF p0;
    public int q;
    public RectF q0;
    public float r;
    public int r0;
    public float s;
    public int s0;
    public boolean t;
    public int t0;
    public float u;
    public int u0;
    public float v;
    public float v0;
    public float w;
    public float w0;
    public boolean x;
    public Bitmap x0;
    public int y;
    public int y0;
    public boolean z;
    public int z0;

    public class a implements Runnable {
        public a() {
        }

        @Override
        public void run() {
            requestLayout();
        }
    }

    public IndicatorSeekBar(Context context) {
        this(context, null);
    }

    public IndicatorSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.j = -1.0f;
        this.k = -1.0f;
        this.y = 1;
        this.c = context;
        k(context, attributeSet);
        l();
    }

    @RequiresApi(api = 21)
    public IndicatorSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.j = -1.0f;
        this.k = -1.0f;
        this.y = 1;
        this.c = context;
        k(context, attributeSet);
        l();
    }

    private float getAmplitude() {
        float f = this.u;
        float f2 = this.v;
        if (f - f2 > 0.0f) {
            return f - f2;
        }
        return 1.0f;
    }

    private int getClosestIndex() {
        float abs = Math.abs(this.u - this.v);
        int i = 0;
        int i2 = 0;
        while (true) {
            float[] fArr = this.C;
            if (i >= fArr.length) {
                return i2;
            }
            float abs2 = Math.abs(fArr[i] - this.w);
            if (abs2 <= abs) {
                i2 = i;
                abs = abs2;
            }
            i++;
        }
    }

    private int getLeftSideTickColor() {
        return this.D ? this.e0 : this.f0;
    }

    private int getLeftSideTickTextsColor() {
        return this.D ? this.N : this.M;
    }

    private int getLeftSideTrackSize() {
        return this.D ? this.r0 : this.s0;
    }

    private int getRightSideTickColor() {
        return this.D ? this.f0 : this.e0;
    }

    private int getRightSideTickTextsColor() {
        return this.D ? this.M : this.N;
    }

    private int getRightSideTrackSize() {
        return this.D ? this.s0 : this.r0;
    }

    private float getThumbCenterX() {
        return (this.D ? this.q0 : this.p0).right;
    }

    private int getThumbPosOnTick() {
        if (this.d0 != 0) {
            return Math.round((getThumbCenterX() - this.n) / this.s);
        }
        return 0;
    }

    private float getThumbPosOnTickFloat() {
        if (this.d0 != 0) {
            return (getThumbCenterX() - this.n) / this.s;
        }
        return 0.0f;
    }

    public void setSeekListener(boolean r8) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.warkiz.widget.IndicatorSeekBar.setSeekListener(boolean):void");
    }

    public final void b() {
        int i = this.d0;
        if (i < 0 || i > 50) {
            StringBuilder d = b.d("the Argument: TICK COUNT must be limited between (0-50), Now is ");
            d.append(this.d0);
            throw new IllegalArgumentException(d.toString());
        } else if (i == 0) {
        } else {
            this.c0 = new float[i];
            if (this.E) {
                this.I = new float[i];
                this.H = new float[i];
            }
            this.C = new float[i];
            int i2 = 0;
            while (true) {
                float[] fArr = this.C;
                if (i2 >= fArr.length) {
                    return;
                }
                float f = this.v;
                float f2 = (this.u - f) * i2;
                int i3 = this.d0;
                fArr[i2] = (f2 / (i3 + (-1) > 0 ? i3 - 1 : 1)) + f;
                i2++;
            }
        }
    }

    public final void c(Canvas canvas) {
        Paint paint;
        int i;
        Bitmap bitmap;
        float width;
        float f;
        Bitmap bitmap2;
        float thumbCenterX = getThumbCenterX();
        if (this.A0 == null) {
            if (this.t) {
                paint = this.d;
                i = this.C0;
            } else {
                paint = this.d;
                i = this.y0;
            }
            paint.setColor(i);
            canvas.drawCircle(thumbCenterX, this.p0.top, this.t ? this.w0 : this.v0, this.d);
            return;
        }
        if (this.x0 == null || this.B0 == null) {
            p();
        }
        if (this.x0 == null || this.B0 == null) {
            throw new IllegalArgumentException("the format of the selector thumb drawable is wrong!");
        }
        this.d.setAlpha(255);
        if (this.t) {
            bitmap = this.B0;
            width = thumbCenterX - (bitmap.getWidth() / 2.0f);
            f = this.p0.top;
            bitmap2 = this.B0;
        } else {
            bitmap = this.x0;
            width = thumbCenterX - (bitmap.getWidth() / 2.0f);
            f = this.p0.top;
            bitmap2 = this.x0;
        }
        canvas.drawBitmap(bitmap, width, f - (bitmap2.getHeight() / 2.0f), this.d);
    }

    public final void d(Canvas canvas) {
        Paint paint;
        int rightSideTickColor;
        float f;
        float f2;
        float f3;
        float f4;
        Bitmap bitmap;
        if (this.d0 != 0) {
            if (this.k0 == 0 && this.j0 == null) {
                return;
            }
            float thumbCenterX = getThumbCenterX();
            for (int i = 0; i < this.c0.length; i++) {
                float thumbPosOnTickFloat = getThumbPosOnTickFloat();
                if ((!this.m0 || thumbCenterX < this.c0[i]) && ((!this.l0 || (i != 0 && i != this.c0.length - 1)) && (i != getThumbPosOnTick() || this.d0 <= 2 || this.B))) {
                    float f5 = i;
                    if (f5 <= thumbPosOnTickFloat) {
                        paint = this.d;
                        rightSideTickColor = getLeftSideTickColor();
                    } else {
                        paint = this.d;
                        rightSideTickColor = getRightSideTickColor();
                    }
                    paint.setColor(rightSideTickColor);
                    if (this.j0 != null) {
                        if (this.i0 == null || this.h0 == null) {
                            r();
                        }
                        Bitmap bitmap2 = this.i0;
                        if (bitmap2 == null || (bitmap = this.h0) == null) {
                            throw new IllegalArgumentException("the format of the selector TickMarks drawable is wrong!");
                        }
                        if (f5 <= thumbPosOnTickFloat) {
                            canvas.drawBitmap(bitmap2, this.c0[i] - (bitmap.getWidth() / 2.0f), this.p0.top - (this.h0.getHeight() / 2.0f), this.d);
                        } else {
                            canvas.drawBitmap(bitmap, this.c0[i] - (bitmap.getWidth() / 2.0f), this.p0.top - (this.h0.getHeight() / 2.0f), this.d);
                        }
                    } else {
                        int i2 = this.k0;
                        if (i2 == 1) {
                            canvas.drawCircle(this.c0[i], this.p0.top, this.g0, this.d);
                        } else {
                            if (i2 == 3) {
                                int z = androidx.base.a5.b.z(this.c, 1.0f);
                                int leftSideTrackSize = thumbCenterX >= this.c0[i] ? getLeftSideTrackSize() : getRightSideTrackSize();
                                float[] fArr = this.c0;
                                float f6 = z;
                                f = fArr[i] - f6;
                                float f7 = this.p0.top;
                                float f8 = leftSideTrackSize / 2.0f;
                                f2 = f7 - f8;
                                f3 = f7 + f8;
                                f4 = fArr[i] + f6;
                            } else if (i2 == 2) {
                                float[] fArr2 = this.c0;
                                float f9 = this.n0 / 2.0f;
                                f = fArr2[i] - f9;
                                float f10 = this.p0.top;
                                f2 = f10 - f9;
                                f3 = f9 + f10;
                                f4 = fArr2[i] + f9;
                            }
                            canvas.drawRect(f, f2, f4, f3, this.d);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        ViewParent parent = getParent();
        if (parent == null) {
            return super.dispatchTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            parent.requestDisallowInterceptTouchEvent(true);
        } else if (action == 1 || action == 3) {
            parent.requestDisallowInterceptTouchEvent(false);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final void e(Canvas canvas) {
        TextPaint textPaint;
        int rightSideTickTextsColor;
        if (this.G == null) {
            return;
        }
        float thumbPosOnTickFloat = getThumbPosOnTickFloat();
        for (int i = 0; i < this.G.length; i++) {
            if (i == getThumbPosOnTick() && i == thumbPosOnTickFloat) {
                textPaint = this.e;
                rightSideTickTextsColor = this.O;
            } else if (i < thumbPosOnTickFloat) {
                textPaint = this.e;
                rightSideTickTextsColor = getLeftSideTickTextsColor();
            } else {
                textPaint = this.e;
                rightSideTickTextsColor = getRightSideTickTextsColor();
            }
            textPaint.setColor(rightSideTickTextsColor);
            int length = this.D ? (this.G.length - i) - 1 : i;
            String[] strArr = this.G;
            if (i == 0) {
                canvas.drawText(strArr[length], (this.H[length] / 2.0f) + this.I[i], this.J, this.e);
            } else if (i == strArr.length - 1) {
                canvas.drawText(strArr[length], this.I[i] - (this.H[length] / 2.0f), this.J, this.e);
            } else {
                canvas.drawText(strArr[length], this.I[i], this.J, this.e);
            }
        }
    }

    public final void f(Canvas canvas) {
        this.d.setColor(this.u0);
        this.d.setStrokeWidth(this.s0);
        RectF rectF = this.p0;
        canvas.drawLine(rectF.left, rectF.top, rectF.right, rectF.bottom, this.d);
        this.d.setColor(this.t0);
        this.d.setStrokeWidth(this.r0);
        RectF rectF2 = this.q0;
        canvas.drawLine(rectF2.left, rectF2.top, rectF2.right, rectF2.bottom, this.d);
    }

    public final Bitmap g(Drawable drawable, boolean z) {
        int intrinsicHeight;
        if (drawable == null) {
            return null;
        }
        int z2 = androidx.base.a5.b.z(this.c, 30.0f);
        if (drawable.getIntrinsicWidth() > z2) {
            int i = z ? this.z0 : this.n0;
            intrinsicHeight = h(drawable, i);
            if (i > z2) {
                intrinsicHeight = h(drawable, z2);
            } else {
                z2 = i;
            }
        } else {
            z2 = drawable.getIntrinsicWidth();
            intrinsicHeight = drawable.getIntrinsicHeight();
        }
        Bitmap createBitmap = Bitmap.createBitmap(z2, intrinsicHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public c getIndicator() {
        return this.Q;
    }

    public View getIndicatorContentView() {
        return this.V;
    }

    public String getIndicatorTextString() {
        String[] strArr;
        String str = this.b0;
        if (str == null || !str.contains("${TICK_TEXT}")) {
            String str2 = this.b0;
            if (str2 != null && str2.contains("${PROGRESS}")) {
                return this.b0.replace("${PROGRESS}", i(this.w));
            }
        } else if (this.d0 > 2 && (strArr = this.G) != null) {
            return this.b0.replace("${TICK_TEXT}", strArr[getThumbPosOnTick()]);
        }
        return i(this.w);
    }

    public float getMax() {
        return this.u;
    }

    public float getMin() {
        return this.v;
    }

    public f getOnSeekChangeListener() {
        return this.f;
    }

    public int getProgress() {
        return Math.round(this.w);
    }

    public synchronized float getProgressFloat() {
        return BigDecimal.valueOf(this.w).setScale(this.y, 4).floatValue();
    }

    public int getTickCount() {
        return this.d0;
    }

    public final int h(Drawable drawable, int i) {
        return Math.round(((i * 1.0f) * drawable.getIntrinsicHeight()) / drawable.getIntrinsicWidth());
    }

    public final String i(float f) {
        String bigDecimal;
        char[] cArr;
        if (this.x) {
            double d = f;
            int i = this.y;
            char[][] cArr2 = androidx.base.q4.b.a;
            int abs = Math.abs(i);
            double pow = (Math.pow(10.0d, abs) * Math.abs(d)) + 0.5d;
            if (pow > 9.99999999999999E14d || abs > 16) {
                bigDecimal = new BigDecimal(Double.toString(d)).setScale(Math.abs(abs), RoundingMode.HALF_UP).toString();
                if (abs != 0) {
                    int length = bigDecimal.length();
                    do {
                        length--;
                        if (length < 0) {
                            break;
                        }
                    } while (bigDecimal.charAt(length) == '0');
                    String substring = bigDecimal.substring(0, length + 1);
                    return substring.charAt(substring.length() + (-1)) == '.' ? substring.substring(0, substring.length() - 1) : substring;
                }
            } else {
                long nextUp = (long) Math.nextUp(pow);
                if (nextUp < 1) {
                    return SessionDescription.SUPPORTED_SDP_VERSION;
                }
                char[] charArray = Long.toString(nextUp).toCharArray();
                if (charArray.length > abs) {
                    int length2 = charArray.length - 1;
                    int length3 = charArray.length - abs;
                    while (length2 >= length3 && charArray[length2] == '0') {
                        length2--;
                    }
                    if (length2 >= length3) {
                        cArr = new char[length2 + 2];
                        System.arraycopy(charArray, 0, cArr, 0, length3);
                        cArr[length3] = '.';
                        System.arraycopy(charArray, length3, cArr, length3 + 1, (length2 - length3) + 1);
                    } else {
                        cArr = new char[length3];
                        System.arraycopy(charArray, 0, cArr, 0, length3);
                    }
                } else {
                    int length4 = charArray.length;
                    do {
                        length4--;
                        if (length4 < 0) {
                            break;
                        }
                    } while (charArray[length4] == '0');
                    char[] cArr3 = androidx.base.q4.b.a[abs - charArray.length];
                    char[] copyOf = Arrays.copyOf(cArr3, cArr3.length + length4 + 1);
                    System.arraycopy(charArray, 0, copyOf, cArr3.length, length4 + 1);
                    cArr = copyOf;
                }
                if (Math.signum(d) <= ShadowDrawableWrapper.COS_45) {
                    StringBuilder d2 = b.d("-");
                    d2.append(new String(cArr));
                    return d2.toString();
                }
                bigDecimal = new String(cArr);
            }
            return bigDecimal;
        }
        return String.valueOf(Math.round(f));
    }

    public final String j(int i) {
        CharSequence[] charSequenceArr = this.P;
        return charSequenceArr == null ? i(this.C[i]) : i < charSequenceArr.length ? String.valueOf(charSequenceArr[i]) : "";
    }

    public final void k(android.content.Context r9, android.util.AttributeSet r10) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.warkiz.widget.IndicatorSeekBar.k(android.content.Context, android.util.AttributeSet):void");
    }

    public final void l() {
        float min;
        m();
        int i = this.r0;
        int i2 = this.s0;
        if (i > i2) {
            this.r0 = i2;
        }
        if (this.A0 == null) {
            float f = this.z0 / 2.0f;
            this.v0 = f;
            min = f * 1.2f;
        } else {
            min = Math.min(androidx.base.a5.b.z(this.c, 30.0f), this.z0) / 2.0f;
            this.v0 = min;
        }
        this.w0 = min;
        this.g0 = (this.j0 == null ? this.n0 : Math.min(androidx.base.a5.b.z(this.c, 30.0f), this.n0)) / 2.0f;
        this.h = Math.max(this.w0, this.g0) * 2.0f;
        if (this.d == null) {
            this.d = new Paint();
        }
        if (this.o0) {
            this.d.setStrokeCap(Paint.Cap.ROUND);
        }
        this.d.setAntiAlias(true);
        int i3 = this.r0;
        if (i3 > this.s0) {
            this.s0 = i3;
        }
        if (u()) {
            if (this.e == null) {
                TextPaint textPaint = new TextPaint();
                this.e = textPaint;
                textPaint.setAntiAlias(true);
                this.e.setTextAlign(Paint.Align.CENTER);
                this.e.setTextSize(this.K);
            }
            if (this.g == null) {
                this.g = new Rect();
            }
            this.e.setTypeface(this.L);
            this.e.getTextBounds("j", 0, 1, this.g);
            this.F = this.g.height() + ((int) ((this.c.getResources().getDisplayMetrics().density * 3.0f) + 0.5f));
        }
        this.i = this.w;
        b();
        this.p0 = new RectF();
        this.q0 = new RectF();
        if (!this.l) {
            int z = androidx.base.a5.b.z(this.c, 16.0f);
            if (getPaddingLeft() == 0) {
                setPadding(z, getPaddingTop(), getPaddingRight(), getPaddingBottom());
            }
            if (getPaddingRight() == 0) {
                setPadding(getPaddingLeft(), getPaddingTop(), z, getPaddingBottom());
            }
        }
        int i4 = this.a0;
        if (i4 != 0 && this.Q == null) {
            c cVar = new c(this.c, this, this.R, i4, this.U, this.S, this.V, this.W);
            this.Q = cVar;
            this.V = cVar.l;
        }
    }

    public final void m() {
        float f = this.u;
        float f2 = this.v;
        if (f < f2) {
            throw new IllegalArgumentException("the Argument: MAX's value must be larger than MIN's.");
        }
        if (this.w < f2) {
            this.w = f2;
        }
        if (this.w > f) {
            this.w = f;
        }
    }

    public final void n() {
        int i;
        this.p = getMeasuredWidth();
        this.n = getPaddingStart();
        this.o = getPaddingEnd();
        this.q = getPaddingTop();
        float f = (this.p - this.n) - this.o;
        this.r = f;
        this.s = f / (this.d0 + (-1) > 0 ? i - 1 : 1);
    }

    public final void o() {
        int i = this.d0;
        if (i == 0) {
            return;
        }
        if (this.E) {
            this.G = new String[i];
        }
        for (int i2 = 0; i2 < this.c0.length; i2++) {
            if (this.E) {
                this.G[i2] = j(i2);
                TextPaint textPaint = this.e;
                String[] strArr = this.G;
                textPaint.getTextBounds(strArr[i2], 0, strArr[i2].length(), this.g);
                this.H[i2] = this.g.width();
                this.I[i2] = (this.s * i2) + this.n;
            }
            this.c0[i2] = (this.s * i2) + this.n;
        }
    }

    @Override
    public synchronized void onDraw(Canvas canvas) {
        f(canvas);
        d(canvas);
        e(canvas);
        c(canvas);
        if (this.D0 && (!this.E || this.d0 <= 2)) {
            this.e.setColor(this.F0);
            canvas.drawText(i(this.w), getThumbCenterX(), this.E0, this.e);
        }
    }

    @Override
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(View.resolveSize(androidx.base.a5.b.z(this.c, 170.0f), i), Math.round(this.h + getPaddingTop() + getPaddingBottom()) + this.F);
        n();
        w();
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Bundle)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        setProgress(bundle.getFloat("isb_progress"));
        super.onRestoreInstanceState(bundle.getParcelable("isb_instance_state"));
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("isb_instance_state", super.onSaveInstanceState());
        bundle.putFloat("isb_progress", this.w);
        return bundle;
    }

    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        post(new a());
    }

    @Override
    public boolean onTouchEvent(android.view.MotionEvent r11) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return true;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.warkiz.widget.IndicatorSeekBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void p() {
        Drawable drawable = this.A0;
        if (drawable == null) {
            return;
        }
        if (drawable instanceof StateListDrawable) {
            try {
                StateListDrawable stateListDrawable = (StateListDrawable) drawable;
                Class<?> cls = stateListDrawable.getClass();
                int intValue = ((Integer) cls.getMethod("getStateCount", new Class[0]).invoke(stateListDrawable, new Object[0])).intValue();
                if (intValue != 2) {
                    throw new IllegalArgumentException("the format of the selector thumb drawable is wrong!");
                }
                Class<?> cls2 = Integer.TYPE;
                Method method = cls.getMethod("getStateSet", cls2);
                Method method2 = cls.getMethod("getStateDrawable", cls2);
                for (int i = 0; i < intValue; i++) {
                    int[] iArr = (int[]) method.invoke(stateListDrawable, Integer.valueOf(i));
                    if (iArr.length <= 0) {
                        this.x0 = g((Drawable) method2.invoke(stateListDrawable, Integer.valueOf(i)), true);
                    } else if (iArr[0] != 16842919) {
                        throw new IllegalArgumentException("the state of the selector thumb drawable is wrong!");
                    } else {
                        this.B0 = g((Drawable) method2.invoke(stateListDrawable, Integer.valueOf(i)), true);
                    }
                }
                return;
            } catch (Exception unused) {
                drawable = this.A0;
            }
        }
        Bitmap g = g(drawable, true);
        this.x0 = g;
        this.B0 = g;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public final void q(ColorStateList colorStateList, int i) {
        Field[] declaredFields;
        if (colorStateList == null) {
            this.y0 = i;
            this.C0 = i;
            return;
        }
        try {
            int[][] iArr = null;
            int[] iArr2 = null;
            for (Field field : colorStateList.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if ("mStateSpecs".equals(field.getName())) {
                    iArr = (int[][]) field.get(colorStateList);
                }
                if ("mColors".equals(field.getName())) {
                    iArr2 = (int[]) field.get(colorStateList);
                }
            }
            if (iArr == null || iArr2 == null) {
                return;
            }
            if (iArr.length == 1) {
                int i2 = iArr2[0];
                this.y0 = i2;
                this.C0 = i2;
            } else if (iArr.length != 2) {
                throw new IllegalArgumentException("the selector color file you set for the argument: isb_thumb_color is in wrong format.");
            } else {
                for (int i3 = 0; i3 < iArr.length; i3++) {
                    int[] iArr3 = iArr[i3];
                    if (iArr3.length == 0) {
                        this.C0 = iArr2[i3];
                    } else if (iArr3[0] != 16842919) {
                        throw new IllegalArgumentException("the selector color file you set for the argument: isb_thumb_color is in wrong format.");
                    } else {
                        this.y0 = iArr2[i3];
                    }
                }
            }
        } catch (Exception unused) {
            throw new RuntimeException("Something wrong happened when parseing thumb selector color.");
        }
    }

    public final void r() {
        Drawable drawable = this.j0;
        if (drawable instanceof StateListDrawable) {
            StateListDrawable stateListDrawable = (StateListDrawable) drawable;
            try {
                Class<?> cls = stateListDrawable.getClass();
                int intValue = ((Integer) cls.getMethod("getStateCount", new Class[0]).invoke(stateListDrawable, new Object[0])).intValue();
                if (intValue != 2) {
                    throw new IllegalArgumentException("the format of the selector TickMarks drawable is wrong!");
                }
                Class<?> cls2 = Integer.TYPE;
                Method method = cls.getMethod("getStateSet", cls2);
                Method method2 = cls.getMethod("getStateDrawable", cls2);
                for (int i = 0; i < intValue; i++) {
                    int[] iArr = (int[]) method.invoke(stateListDrawable, Integer.valueOf(i));
                    if (iArr.length <= 0) {
                        this.h0 = g((Drawable) method2.invoke(stateListDrawable, Integer.valueOf(i)), false);
                    } else if (iArr[0] != 16842913) {
                        throw new IllegalArgumentException("the state of the selector TickMarks drawable is wrong!");
                    } else {
                        this.i0 = g((Drawable) method2.invoke(stateListDrawable, Integer.valueOf(i)), false);
                    }
                }
                return;
            } catch (Exception unused) {
                drawable = this.j0;
            }
        }
        Bitmap g = g(drawable, false);
        this.h0 = g;
        this.i0 = g;
    }

    public final void s(ColorStateList colorStateList, int i) {
        Field[] declaredFields;
        if (colorStateList == null) {
            this.f0 = i;
            this.e0 = i;
            return;
        }
        try {
            int[][] iArr = null;
            int[] iArr2 = null;
            for (Field field : colorStateList.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if ("mStateSpecs".equals(field.getName())) {
                    iArr = (int[][]) field.get(colorStateList);
                }
                if ("mColors".equals(field.getName())) {
                    iArr2 = (int[]) field.get(colorStateList);
                }
            }
            if (iArr == null || iArr2 == null) {
                return;
            }
            if (iArr.length == 1) {
                int i2 = iArr2[0];
                this.f0 = i2;
                this.e0 = i2;
            } else if (iArr.length != 2) {
                throw new IllegalArgumentException("the selector color file you set for the argument: isb_tick_marks_color is in wrong format.");
            } else {
                for (int i3 = 0; i3 < iArr.length; i3++) {
                    int[] iArr3 = iArr[i3];
                    if (iArr3.length == 0) {
                        this.e0 = iArr2[i3];
                    } else if (iArr3[0] != 16842913) {
                        throw new IllegalArgumentException("the selector color file you set for the argument: isb_tick_marks_color is in wrong format.");
                    } else {
                        this.f0 = iArr2[i3];
                    }
                }
            }
        } catch (Exception e) {
            StringBuilder d = b.d("Something wrong happened when parsing thumb selector color.");
            d.append(e.getMessage());
            throw new RuntimeException(d.toString());
        }
    }

    public void setDecimalScale(int i) {
        this.y = i;
    }

    @Override
    public void setEnabled(boolean z) {
        float f;
        if (z == isEnabled()) {
            return;
        }
        super.setEnabled(z);
        if (isEnabled()) {
            f = 1.0f;
            setAlpha(1.0f);
            if (!this.T) {
                return;
            }
        } else {
            f = 0.3f;
            setAlpha(0.3f);
            if (!this.T) {
                return;
            }
        }
        this.V.setAlpha(f);
    }

    public void setIndicatorStayAlways(boolean z) {
        this.T = z;
    }

    public void setIndicatorTextFormat(String str) {
        this.b0 = str;
        o();
        y();
    }

    public synchronized void setMax(float f) {
        this.u = Math.max(this.v, f);
        m();
        b();
        w();
        invalidate();
        y();
    }

    public synchronized void setMin(float f) {
        this.v = Math.min(this.u, f);
        m();
        b();
        w();
        invalidate();
        y();
    }

    public void setOnSeekChangeListener(@NonNull f fVar) {
        this.f = fVar;
    }

    public synchronized void setProgress(float f) {
        this.i = this.w;
        float f2 = this.v;
        if (f >= f2) {
            f2 = this.u;
            if (f > f2) {
            }
            this.w = f;
            if (!this.B && this.d0 > 2) {
                this.w = this.C[getClosestIndex()];
            }
            setSeekListener(false);
            x(this.w);
            postInvalidate();
            y();
        }
        f = f2;
        this.w = f;
        if (!this.B) {
            this.w = this.C[getClosestIndex()];
        }
        setSeekListener(false);
        x(this.w);
        postInvalidate();
        y();
    }

    public void setR2L(boolean z) {
        this.D = z;
        requestLayout();
        invalidate();
        y();
    }

    public void setThumbAdjustAuto(boolean z) {
        this.G0 = z;
    }

    public void setThumbDrawable(Drawable drawable) {
        if (drawable == null) {
            this.A0 = null;
            this.x0 = null;
            this.B0 = null;
        } else {
            this.A0 = drawable;
            float min = Math.min(androidx.base.a5.b.z(this.c, 30.0f), this.z0) / 2.0f;
            this.v0 = min;
            this.w0 = min;
            this.h = Math.max(min, this.g0) * 2.0f;
            p();
        }
        requestLayout();
        invalidate();
    }

    public synchronized void setTickCount(int i) {
        int i2 = this.d0;
        if (i2 < 0 || i2 > 50) {
            throw new IllegalArgumentException("the Argument: TICK COUNT must be limited between (0-50), Now is " + this.d0);
        }
        this.d0 = i;
        b();
        o();
        n();
        w();
        invalidate();
        y();
    }

    public void setTickMarksDrawable(Drawable drawable) {
        if (drawable == null) {
            this.j0 = null;
            this.h0 = null;
            this.i0 = null;
        } else {
            this.j0 = drawable;
            float min = Math.min(androidx.base.a5.b.z(this.c, 30.0f), this.n0) / 2.0f;
            this.g0 = min;
            this.h = Math.max(this.w0, min) * 2.0f;
            r();
        }
        invalidate();
    }

    public void setUserSeekAble(boolean z) {
        this.z = z;
    }

    public final void t(ColorStateList colorStateList, int i) {
        Field[] declaredFields;
        if (colorStateList == null) {
            this.N = i;
            this.M = i;
            this.O = i;
            return;
        }
        try {
            int[][] iArr = null;
            int[] iArr2 = null;
            for (Field field : colorStateList.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if ("mStateSpecs".equals(field.getName())) {
                    iArr = (int[][]) field.get(colorStateList);
                }
                if ("mColors".equals(field.getName())) {
                    iArr2 = (int[]) field.get(colorStateList);
                }
            }
            if (iArr == null || iArr2 == null) {
                return;
            }
            if (iArr.length == 1) {
                int i2 = iArr2[0];
                this.N = i2;
                this.M = i2;
                this.O = i2;
            } else if (iArr.length != 3) {
                throw new IllegalArgumentException("the selector color file you set for the argument: isb_tick_texts_color is in wrong format.");
            } else {
                for (int i3 = 0; i3 < iArr.length; i3++) {
                    int[] iArr3 = iArr[i3];
                    if (iArr3.length == 0) {
                        this.N = iArr2[i3];
                    } else {
                        int i4 = iArr3[0];
                        if (i4 == 16842913) {
                            this.M = iArr2[i3];
                        } else if (i4 != 16843623) {
                            throw new IllegalArgumentException("the selector color file you set for the argument: isb_tick_texts_color is in wrong format.");
                        } else {
                            this.O = iArr2[i3];
                        }
                    }
                }
            }
        } catch (Exception unused) {
            throw new RuntimeException("Something wrong happened when parseing thumb selector color.");
        }
    }

    public final boolean u() {
        return this.D0 || (this.d0 != 0 && this.E);
    }

    public final void v(android.view.MotionEvent r11) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.warkiz.widget.IndicatorSeekBar.v(android.view.MotionEvent):void");
    }

    public final void w() {
        RectF rectF;
        float f;
        RectF rectF2;
        if (this.D) {
            RectF rectF3 = this.q0;
            float f2 = this.n;
            rectF3.left = f2;
            rectF3.top = this.q + this.w0;
            rectF3.right = ((1.0f - ((this.w - this.v) / getAmplitude())) * this.r) + f2;
            rectF = this.q0;
            f = rectF.top;
            rectF.bottom = f;
            rectF2 = this.p0;
        } else {
            RectF rectF4 = this.p0;
            rectF4.left = this.n;
            rectF4.top = this.q + this.w0;
            rectF4.right = (((this.w - this.v) * this.r) / getAmplitude()) + this.n;
            rectF = this.p0;
            f = rectF.top;
            rectF.bottom = f;
            rectF2 = this.q0;
        }
        rectF2.left = rectF.right;
        rectF2.top = f;
        rectF2.right = this.p - this.o;
        rectF2.bottom = f;
        if (u()) {
            this.e.getTextBounds("j", 0, 1, this.g);
            float round = this.q + this.h + Math.round(this.g.height() - this.e.descent()) + androidx.base.a5.b.z(this.c, 3.0f);
            this.J = round;
            this.E0 = round;
        }
        if (this.c0 == null) {
            return;
        }
        o();
        if (this.d0 > 2) {
            float f3 = this.C[getClosestIndex()];
            this.w = f3;
            this.i = f3;
        }
        x(this.w);
    }

    public final void x(float f) {
        RectF rectF;
        RectF rectF2;
        if (this.D) {
            this.q0.right = ((1.0f - ((f - this.v) / getAmplitude())) * this.r) + this.n;
            rectF = this.p0;
            rectF2 = this.q0;
        } else {
            this.p0.right = (((f - this.v) * this.r) / getAmplitude()) + this.n;
            rectF = this.q0;
            rectF2 = this.p0;
        }
        rectF.left = rectF2.right;
    }

    public final void y() {
        c cVar;
        int thumbCenterX;
        int i;
        if (!this.T || (cVar = this.Q) == null) {
            return;
        }
        String indicatorTextString = getIndicatorTextString();
        View view = cVar.l;
        if (view instanceof CircleBubbleView) {
            ((CircleBubbleView) view).setProgress(indicatorTextString);
        } else {
            TextView textView = cVar.d;
            if (textView != null) {
                textView.setText(indicatorTextString);
            }
        }
        this.V.measure(0, 0);
        int measuredWidth = this.V.getMeasuredWidth();
        float thumbCenterX2 = getThumbCenterX();
        if (this.k == -1.0f) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) this.c.getSystemService(Context.WINDOW_SERVICE);
            if (windowManager != null) {
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                this.k = displayMetrics.widthPixels;
            }
        }
        float f = measuredWidth / 2;
        int i2 = this.p;
        if (f + thumbCenterX2 > i2) {
            int i3 = i2 - measuredWidth;
            i = (int) ((thumbCenterX2 - i3) - f);
            thumbCenterX = i3;
        } else if (thumbCenterX2 - f < 0.0f) {
            i = -((int) (f - thumbCenterX2));
            thumbCenterX = 0;
        } else {
            thumbCenterX = (int) (getThumbCenterX() - f);
            i = 0;
        }
        c cVar2 = this.Q;
        cVar2.d(cVar2.l, thumbCenterX, -1, -1, -1);
        c cVar3 = this.Q;
        cVar3.d(cVar3.c, i, -1, -1, -1);
    }
}
