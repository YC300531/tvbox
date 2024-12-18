package com.github.tvbox.osc.player.controller;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.base.k6.c;
import java.util.Map;
import java.util.Objects;
import xyz.doikki.videoplayer.controller.BaseVideoController;
public abstract class BaseController extends BaseVideoController implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnTouchListener {
    public static final int V = 0;
    public boolean A;
    public boolean B;
    public boolean C;
    public boolean D;
    public boolean E;
    public int F;
    public Handler G;
    public boolean H;
    public TextView I;
    public ProgressBar J;
    public ProgressBar K;
    public ViewGroup L;
    public TextView M;
    public TextView N;
    public TextView O;
    public LinearLayout P;
    public LinearLayout Q;
    public LinearLayout R;
    public ProgressBar S;
    public ProgressBar T;
    public ProgressBar U;
    public GestureDetector t;
    public AudioManager u;
    public boolean v;
    public int w;
    public int x;
    public boolean y;
    public boolean z;

    public class a implements Handler.Callback {
        public a() {
        }

        @Override
        public boolean handleMessage(@NonNull Message message) {
            LinearLayout linearLayout;
            LinearLayout linearLayout2;
            int i = message.what;
            if (i == 100) {
                I.setVisibility(View.VISIBLE);
                I.setText(message.obj.toString());
            } else if (i != 101) {
                BaseController baseController = BaseController.this;
                switch (i) {
                    case 201:
                        linearLayout = baseController.Q;
                        linearLayout.setVisibility(View.VISIBLE);
                        break;
                    case 202:
                        linearLayout2 = baseController.Q;
                        linearLayout2.setVisibility(View.GONE);
                        break;
                    case 203:
                        linearLayout = baseController.R;
                        linearLayout.setVisibility(View.VISIBLE);
                        break;
                    case 204:
                        linearLayout2 = baseController.R;
                        linearLayout2.setVisibility(View.GONE);
                        break;
                    default:
                        Objects.requireNonNull(baseController);
                        break;
                }
            } else {
                I.setVisibility(View.GONE);
            }
            return false;
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override
        public void run() {
            BaseController baseController = BaseController.this;
            int i = BaseController.V;
            double tcpSpeed = (float) baseController.c.c.getTcpSpeed();
            Double.isNaN(tcpSpeed);
            String format = String.format("%.2f", Double.valueOf((tcpSpeed / 1024.0d) / 1024.0d));
            N.setText(format);
            O.setText(format);
            G.postDelayed(this, 1000L);
        }
    }

    public BaseController(@NonNull Context context) {
        super(context);
        this.v = true;
        this.C = true;
        this.H = true;
        b bVar = new b();
        Handler handler = new Handler(new a());
        this.G = handler;
        handler.post(bVar);
    }

    public BaseController(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.v = true;
        this.C = true;
        this.H = true;
    }

    public BaseController(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.v = true;
        this.C = true;
        this.H = true;
    }

    @Override
    public void g() {
        super.g();
        this.u = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        this.t = new GestureDetector(getContext(), this);
        setOnTouchListener(this);
        this.I = (TextView) findViewWithTag("vod_control_slide_info");
        this.J = (ProgressBar) findViewWithTag("vod_control_loading");
        this.K = (ProgressBar) findViewWithTag("vod_control_loading_hide");
        this.L = (ViewGroup) findViewWithTag("vod_control_pause");
        this.M = (TextView) findViewWithTag("vod_control_pause_t");
        this.N = (TextView) findViewWithTag("play_speed_top");
        this.O = (TextView) findViewWithTag("play_speed_top_hide");
        this.P = (LinearLayout) findViewWithTag("top_container_hide");
        this.Q = (LinearLayout) findViewWithTag("dialog_volume");
        this.R = (LinearLayout) findViewWithTag("dialog_brightness");
        this.S = (ProgressBar) findViewWithTag("progressbar_volume");
        ProgressBar progressBar = (ProgressBar) findViewWithTag("progressbar_brightness");
        this.T = (ProgressBar) findViewWithTag("progressbar_video");
        this.U = (ProgressBar) findViewWithTag("pausebar_video");
        this.J.setVisibility(View.GONE);
        this.K.setVisibility(View.GONE);
        this.P.setVisibility(View.GONE);
    }

    @Override
    public void k(int i) {
        super.k(i);
        if (i != -1 && i != 0 && i != 2) {
            if (i == 3) {
                this.L.setVisibility(View.GONE);
            } else if (i != 4) {
                if (i == 5) {
                    this.J.setVisibility(View.GONE);
                    this.K.setVisibility(View.GONE);
                    this.L.setVisibility(View.GONE);
                    this.P.setVisibility(View.GONE);
                } else if (i != 7) {
                    return;
                }
            }
        }
        this.J.setVisibility(View.GONE);
        this.K.setVisibility(View.GONE);
        this.P.setVisibility(View.GONE);
    }

    @Override
    public void n(int i, int i2) {
        TextView textView = this.M;
        textView.setText(c.h(i2) + " / " + c.h(i));
        double d = (double) i2;
        double d2 = (double) i;
        Double.isNaN(d);
        Double.isNaN(d2);
        int i3 = (int) ((d / d2) * 100.0d);
        this.T.setProgress(i3);
        this.U.setProgress(i3);
    }

    public boolean o() {
        int i;
        return (this.c == null || (i = this.F) == -1 || i == 0 || i == 1 || i == 2 || i == 8 || i == 5) ? false : true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        if (this.H && !this.f && o()) {
            this.c.l();
            return true;
        }
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        if (o() && this.v && !c.f(getContext(), motionEvent)) {
            this.w = this.u.getStreamVolume(3);
            Activity g = c.g(getContext());
            if (g != null) {
                float f = g.getWindow().getAttributes().screenBrightness;
            }
            this.y = true;
            this.z = false;
            this.A = false;
            this.B = false;
        }
        return true;
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (o() && this.v && this.E && !this.f && !c.f(getContext(), motionEvent)) {
            float x = motionEvent.getX() - motionEvent2.getX();
            float y = motionEvent.getY() - motionEvent2.getY();
            if (this.y) {
                boolean z = Math.abs(f) >= Math.abs(f2);
                this.z = z;
                if (!z) {
                    if (motionEvent2.getX() > c.d(getContext(), true) / 2) {
                        this.B = true;
                    } else {
                        this.A = true;
                    }
                }
                if (this.z) {
                    this.z = this.C;
                }
                if (this.z || this.A || this.B) {
                    for (Map.Entry<androidx.base.g6.b, Boolean> entry : this.n.entrySet()) {
                        androidx.base.g6.b key = entry.getKey();
                        if (key instanceof androidx.base.g6.c) {
                            ((androidx.base.g6.c) key).j();
                        }
                    }
                }
                this.y = false;
            }
            if (this.z) {
                int measuredWidth = getMeasuredWidth();
                int duration = (int) this.c.getDuration();
                int currentPosition = (int) this.c.getCurrentPosition();
                int i = (int) ((((-x) / measuredWidth) * 120000.0f) + currentPosition);
                if (i > duration) {
                    i = duration;
                }
                int i2 = i >= 0 ? i : 0;
                for (Map.Entry<androidx.base.g6.b, Boolean> entry2 : this.n.entrySet()) {
                    androidx.base.g6.b key2 = entry2.getKey();
                    if (key2 instanceof androidx.base.g6.c) {
                        ((androidx.base.g6.c) key2).c(i2, currentPosition, duration);
                    }
                }
                this.x = i2;
            } else if (this.A) {
                float streamMaxVolume = this.u.getStreamMaxVolume(3);
                float measuredHeight = this.w + (((y * 2.0f) / getMeasuredHeight()) * streamMaxVolume);
                if (measuredHeight > streamMaxVolume) {
                    measuredHeight = streamMaxVolume;
                }
                if (measuredHeight < 0.0f) {
                    measuredHeight = 0.0f;
                }
                int i3 = (int) ((measuredHeight / streamMaxVolume) * 100.0f);
                this.u.setStreamVolume(3, (int) measuredHeight, 0);
                for (Map.Entry<androidx.base.g6.b, Boolean> entry3 : this.n.entrySet()) {
                    androidx.base.g6.b key3 = entry3.getKey();
                    if (key3 instanceof androidx.base.g6.c) {
                        ((androidx.base.g6.c) key3).h(i3);
                    }
                }
                this.S.setProgress(i3);
                Message obtain = Message.obtain();
                obtain.what = 201;
                obtain.obj = androidx.base.a.b.a("音量 ", i3, "%");
                this.G.sendMessage(obtain);
                this.G.removeMessages(202);
                this.G.sendEmptyMessageDelayed(202, 600L);
            }
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        if (o()) {
            androidx.base.g6.a aVar = this.c;
            if (aVar.isShowing()) {
                aVar.d.hide();
                return true;
            }
            aVar.d.show();
            return true;
        }
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.t.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.t.onTouchEvent(motionEvent)) {
            int action = motionEvent.getAction();
            if (action == 1) {
                p();
                int i = this.x;
                if (i > 0) {
                    this.c.c.seekTo(i);
                    this.x = 0;
                }
            } else if (action == 3) {
                p();
                this.x = 0;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public final void p() {
        for (Map.Entry<androidx.base.g6.b, Boolean> entry : this.n.entrySet()) {
            androidx.base.g6.b key = entry.getKey();
            if (key instanceof androidx.base.g6.c) {
                ((androidx.base.g6.c) key).d();
            }
        }
    }

    public void setCanChangePosition(boolean z) {
        this.C = z;
    }

    public void setDoubleTapTogglePlayEnabled(boolean z) {
        this.H = z;
    }

    public void setEnableInNormal(boolean z) {
        this.D = z;
    }

    public void setGestureEnabled(boolean z) {
        this.v = z;
    }

    @Override
    public void setPlayState(int i) {
        super.setPlayState(i);
        this.F = i;
    }

    @Override
    public void setPlayerState(int i) {
        boolean z;
        super.setPlayerState(i);
        if (i == 10) {
            z = this.D;
        } else if (i != 11) {
            return;
        } else {
            z = true;
        }
        this.E = z;
    }
}
