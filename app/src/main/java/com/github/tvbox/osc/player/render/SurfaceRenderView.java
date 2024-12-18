package com.github.tvbox.osc.player.render;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.base.j6.a;
import androidx.base.j6.b;
import xyz.doikki.videoplayer.player.AbstractPlayer;
public class SurfaceRenderView extends SurfaceView implements a, SurfaceHolder.Callback {
    public b c;
    public AbstractPlayer d;

    public SurfaceRenderView(Context context) {
        super(context);
        this.c = new b();
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(1);
    }

    public SurfaceRenderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c = new b();
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(1);
    }

    public SurfaceRenderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.c = new b();
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(1);
    }

    @Override
    public void a(@NonNull AbstractPlayer abstractPlayer) {
        this.d = abstractPlayer;
    }

    @Override
    public void b(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            return;
        }
        b bVar = this.c;
        bVar.a = i;
        bVar.b = i2;
        requestLayout();
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onMeasure(int i, int i2) {
        int[] a = this.c.a(i, i2);
        setMeasuredDimension(a[0], a[1]);
    }

    @Override
    public void release() {
    }

    @Override
    public void setScaleType(int i) {
        this.c.c = i;
        requestLayout();
    }

    @Override
    public void setVideoRotation(int i) {
        this.c.d = i;
        setRotation(i);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        AbstractPlayer abstractPlayer = this.d;
        if (abstractPlayer != null) {
            abstractPlayer.setDisplay(surfaceHolder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        AbstractPlayer abstractPlayer = this.d;
        if (abstractPlayer != null) {
            abstractPlayer.setDisplay(null);
        }
    }
}
