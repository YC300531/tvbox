package com.github.tvbox.osc.player.controller;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.base.a5.b;
import androidx.base.j3.i;
import androidx.base.k3.i0;
import androidx.base.k6.c;
import androidx.media3.common.C;
import androidx.media3.exoplayer.dash.DashMediaSource;
import com.github.tvbox.osc.ui.activity.LivePlayActivity;
import com.orhanobut.hawk.Hawk;
import com.palayer.ku9.R;
import java.util.Objects;
public class LiveController extends BaseController {
    public int W;
    public int a0;
    public int b0;
    public a c0;

    public interface a {
    }

    public LiveController(Context context) {
        super(context);
        this.W = 100;
        this.a0 = 10;
        this.c0 = null;
        this.b0 = c.d(getContext(), true) / 2;
    }

    @Override
    public void g() {
        super.g();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading);
    }

    @Override
    public int getLayoutId() {
        return R.layout.player_live_control_view;
    }

    @Override
    public void k(int i) {
        Handler handler;
        Runnable runnable;
        TextView textView;
        String str;
        super.k(i);
        androidx.base.i3.a aVar = (androidx.base.i3.a) this.c0;
        Objects.requireNonNull(aVar);
        long j = DashMediaSource.DEFAULT_FALLBACK_TARGET_LIVE_OFFSET_MS;
        if (i != -1) {
            if (i != 1) {
                if (i == 2) {
                    if (i.g || (LivePlayActivity.e0.v.getCanShiYi() && ((Boolean) Hawk.get("Time_shift_on", Boolean.TRUE)).booleanValue())) {
                        LivePlayActivity.g0.h.setMin(0.0f);
                        LivePlayActivity.g0.h.setMax((float) (androidx.base.k3.c.N - androidx.base.k3.c.M));
                        LivePlayActivity.g0.i.setText(b.q(androidx.base.k3.c.N));
                        return;
                    }
                    int duration = ((int) aVar.a.e.getDuration()) / 1000;
                    if (duration > 0) {
                        LivePlayActivity.g(aVar.a);
                        LivePlayActivity.g0.h.setMin(0.0f);
                        LivePlayActivity.g0.h.setMax(duration);
                        LivePlayActivity.g0.i.setText(c.i(duration * 1000));
                        return;
                    }
                    return;
                }
                if (i != 3) {
                    if (i != 5) {
                        if (i != 6) {
                            if (i != 7) {
                                return;
                            }
                        }
                    }
                }
                aVar.a.e.getmPlayerContainer().removeView(aVar.a.e.getCurrentRenderView());
                if (aVar.a.e.getVideoSize().length >= 2) {
                    String str2 = aVar.a.e.getVideoSize()[0] + "x" + aVar.a.e.getVideoSize()[1];
                    if ("0x0".equals(str2)) {
                        aVar.a.q.setVisibility(View.GONE);
                    } else {
                        aVar.a.q.setText(str2);
                        aVar.a.q.setVisibility(View.VISIBLE);
                    }
                }
                if (aVar.a.e.getVideoFps() != 0) {
                    aVar.a.G.setText(aVar.a.e.getVideoFps() + "FPS");
                    aVar.a.G.setVisibility(View.VISIBLE);
                } else {
                    aVar.a.G.setVisibility(View.GONE);
                }
                if (aVar.a.e.getAudioTrack() != null) {
                    LivePlayActivity livePlayActivity = aVar.a;
                    livePlayActivity.F.setText(livePlayActivity.e.getAudioTrack());
                    aVar.a.F.setVisibility(View.VISIBLE);
                } else {
                    aVar.a.F.setVisibility(View.GONE);
                }
                if (LivePlayActivity.e0.v.include_back) {
                    aVar.a.H.setText("可时移");
                    aVar.a.H.setVisibility(View.VISIBLE);
                } else {
                    aVar.a.H.setVisibility(View.GONE);
                }
                if (LivePlayActivity.e0.v.is_ipv6) {
                    textView = aVar.a.I;
                    str = "IPV6";
                } else {
                    textView = aVar.a.I;
                    str = "IPV4";
                }
                textView.setText(str);
                aVar.a.I.setVisibility(View.VISIBLE);
                aVar.a.B.setVisibility(View.GONE);
                aVar.a.D.setVisibility(View.VISIBLE);
                LivePlayActivity livePlayActivity2 = aVar.a;
                livePlayActivity2.L.removeCallbacks(livePlayActivity2.W);
                LivePlayActivity livePlayActivity3 = aVar.a;
                livePlayActivity3.L.postDelayed(livePlayActivity3.W, 5000L);
                LivePlayActivity livePlayActivity4 = aVar.a;
                livePlayActivity4.J = true;
                livePlayActivity4.l = 0;
                livePlayActivity4.L.removeCallbacks(livePlayActivity4.Y);
                LivePlayActivity livePlayActivity5 = aVar.a;
                livePlayActivity5.L.removeCallbacks(livePlayActivity5.Z);
                return;
            }
            if (!LivePlayActivity.g0.f.isShowing()) {
                aVar.a.C();
            }
            LivePlayActivity livePlayActivity6 = aVar.a;
            livePlayActivity6.J = false;
            livePlayActivity6.H.setVisibility(View.GONE);
            aVar.a.F.setVisibility(View.GONE);
            aVar.a.G.setVisibility(View.GONE);
            aVar.a.q.setVisibility(View.GONE);
            aVar.a.I.setVisibility(View.GONE);
            LivePlayActivity livePlayActivity7 = aVar.a;
            livePlayActivity7.L.removeCallbacks(livePlayActivity7.Y);
            LivePlayActivity livePlayActivity8 = aVar.a;
            livePlayActivity8.L.removeCallbacks(livePlayActivity8.Z);
            if (((Integer) Hawk.get("live_connect_timeout", 2)).intValue() != 0) {
                LivePlayActivity livePlayActivity9 = aVar.a;
                handler = livePlayActivity9.L;
                runnable = livePlayActivity9.Y;
                j = ((Integer) Hawk.get("live_connect_timeout", 2)).intValue() * 5000;
                handler.postDelayed(runnable, j);
            }
            LivePlayActivity livePlayActivity10 = aVar.a;
            handler = livePlayActivity10.L;
            runnable = livePlayActivity10.Z;
            handler.postDelayed(runnable, j);
        }
        LivePlayActivity livePlayActivity11 = aVar.a;
        livePlayActivity11.L.removeCallbacks(livePlayActivity11.Y);
        LivePlayActivity livePlayActivity12 = aVar.a;
        livePlayActivity12.L.removeCallbacks(livePlayActivity12.Z);
        if (((Integer) Hawk.get("live_connect_timeout", 2)).intValue() != 0) {
            LivePlayActivity livePlayActivity13 = aVar.a;
            handler = livePlayActivity13.L;
            runnable = livePlayActivity13.Y;
            j = C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS;
            handler.postDelayed(runnable, j);
        }
        LivePlayActivity livePlayActivity102 = aVar.a;
        handler = livePlayActivity102.L;
        runnable = livePlayActivity102.Z;
        handler.postDelayed(runnable, j);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        a aVar;
        int i;
        if (motionEvent.getX() - motionEvent2.getX() <= this.W || Math.abs(f) <= this.a0) {
            if (motionEvent2.getX() - motionEvent.getX() > this.W && Math.abs(f) > this.a0) {
                aVar = this.c0;
                i = 1;
            } else if (motionEvent.getY() - motionEvent2.getY() <= this.W || Math.abs(f2) <= this.a0) {
                if (motionEvent2.getY() - motionEvent.getY() > this.W && Math.abs(f2) > this.a0 && motionEvent2.getX() > this.b0) {
                    aVar = this.c0;
                    i = 3;
                }
            } else if (motionEvent2.getX() > this.b0) {
                aVar = this.c0;
                i = 2;
            }
            ((androidx.base.i3.a) aVar).a(i);
        } else {
            ((androidx.base.i3.a) this.c0).a(0);
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        ((androidx.base.i3.a) this.c0).a.X = true;
        LivePlayActivity.f0.c();
        i0.u = -1;
    }

    @Override
    public boolean onSingleTapUp(android.view.MotionEvent r8) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return true;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.github.tvbox.osc.player.controller.LiveController.onSingleTapUp(android.view.MotionEvent):boolean");
    }

    public void setListener(a aVar) {
        this.c0 = aVar;
    }
}
