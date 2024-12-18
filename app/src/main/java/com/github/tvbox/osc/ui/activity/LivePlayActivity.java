package com.github.tvbox.osc.ui.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.base.e3.a;
import androidx.base.k3.b0;
import androidx.base.k3.d0;
import androidx.base.k3.i0;
import androidx.base.k3.j0;
import androidx.base.k3.m0;
import androidx.base.k3.u;
import androidx.base.u5.o;
import androidx.core.view.ViewCompat;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.CaptionStyleCompat;
import androidx.media3.ui.SubtitleView;
import com.east.android.zlive.ZLives;
import com.forcetech.service.PxpUtil;
import com.github.tvbox.osc.bean.LiveChannelGroup;
import com.github.tvbox.osc.bean.LiveChannelItem;
import com.github.tvbox.osc.bean.LiveSettingGroup;
import com.github.tvbox.osc.player.controller.LiveController;
import com.nagasoft.player.UrlChanged;
import com.nagasoft.player.VJPlayer;
import com.orhanobut.hawk.Hawk;
import com.palayer.ku9.R;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import xyz.doikki.videoplayer.player.VideoView;
public class LivePlayActivity extends com.github.tvbox.osc.base.a implements UrlChanged {
    public static String c0 = "";
    public static LiveChannelItem d0;
    public static androidx.base.k3.c e0;
    public static i0 f0;
    public static d0 g0;
    public static SubtitleView h0;
    public TextView A;
    public LinearLayout B;
    public View C;
    public ProgressBar D;
    public TextView F;
    public TextView G;
    public TextView H;
    public TextView I;
    public boolean J;
    public androidx.base.e3.a M;
    public boolean Q;
    public boolean S;
    public VJPlayer U;
    public ZLives V;
    public VideoView e;
    public LiveController f;
    public TextView j;
    public LinearLayout m;
    public LinearLayout n;
    public ImageView o;
    public TextView p;
    public TextView q;
    public TextView r;
    public TextView s;
    public TextView t;
    public TextView u;
    public TextView v;
    public TextView w;
    public TextView x;
    public TextView z;
    public final List<LiveChannelGroup> g = new ArrayList();
    public final List<LiveSettingGroup> h = new ArrayList();
    public int i = 0;
    public final androidx.base.e3.c k = new androidx.base.e3.c();
    public int l = 0;
    public String y = "";
    public Map<String, String> E = new HashMap();
    public Set<String> K = new HashSet();
    @SuppressLint({"HandlerLeak"})
    public final Handler L = new f();
    public final Runnable N = new g();
    public long O = 0;
    public final Runnable P = new h();
    public final Runnable R = new i();
    public HashMap<String, Integer> T = new HashMap<>();
    public final Runnable W = new a();
    public boolean X = false;
    public final Runnable Y = new b();
    public final Runnable Z = new c();
    public List<LiveChannelGroup> a0 = new ArrayList();
    public final Runnable b0 = new e();

    public class a implements Runnable {
        public a() {
        }

        @Override
        public void run() {
            if (m.getVisibility() == 0) {
                m.setVisibility(View.GONE);
                m.startAnimation(AnimationUtils.loadAnimation(LivePlayActivity.this, R.anim.bottom_group_slide_out_bottom));
            }
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override
        public void run() {
            if (LivePlayActivity.e0.v == null) {
                return;
            }
            if (androidx.base.j3.i.g) {
                LivePlayActivity livePlayActivity = LivePlayActivity.this;
                livePlayActivity.L.post(livePlayActivity.Z);
                androidx.base.m3.j.a("回看失败，已切换为直播", androidx.base.m3.j.c);
                return;
            }
            l++;
            int sourceNum = LivePlayActivity.e0.v.getSourceNum();
            LivePlayActivity livePlayActivity2 = LivePlayActivity.this;
            if (sourceNum != livePlayActivity2.l) {
                livePlayActivity2.u();
                return;
            }
            livePlayActivity2.l = 0;
            Integer[] j = livePlayActivity2.j(1);
            s(j[0].intValue(), j[1].intValue(), false);
        }
    }

    public class c implements Runnable {
        public c() {
        }

        @Override
        public void run() {
            LivePlayActivity livePlayActivity = LivePlayActivity.this;
            String str = LivePlayActivity.c0;
            livePlayActivity.y();
        }
    }

    public class d implements Runnable {
        public d() {
        }

        @Override
        public void run() {
            m();
        }
    }

    public class e implements Runnable {
        public long c = TrafficStats.getTotalRxBytes();

        public e() {
        }

        @Override
        @SuppressLint({"SetTextI18n"})
        public void run() {
            String sb;
            if (e == null) {
                return;
            }
            long totalRxBytes = TrafficStats.getTotalRxBytes();
            this.c = totalRxBytes;
            double d = totalRxBytes - this.c;
            if (d < 1024.0d) {
                sb = "0.00MB/s";
            } else {
                StringBuilder sb2 = new StringBuilder();
                Locale locale = Locale.CHINESE;
                Double.isNaN(d);
                sb2.append(String.format(locale, "%.2f", Double.valueOf((d / 1024.0d) / 1024.0d)));
                sb2.append("MB/s");
                sb = sb2.toString();
            }
            A.setText(sb);
            L.postDelayed(this, 1000L);
        }
    }

    public class f extends Handler {
        public f() {
        }

        @Override
        public void handleMessage(@NonNull Message message) {
            int i = message.what;
            if (i == 15 || i == 28) {
                e.start();
            }
        }
    }

    public class g implements Runnable {
        public g() {
        }

        @Override
        public void run() {
            float f;
            LivePlayActivity livePlayActivity = LivePlayActivity.this;
            String str = LivePlayActivity.c0;
            Objects.requireNonNull(livePlayActivity);
            boolean booleanValue = ((Boolean) Hawk.get("Hide_bottom_logo", Boolean.TRUE)).booleanValue();
            ViewGroup.LayoutParams layoutParams = livePlayActivity.n.getLayoutParams();
            DisplayMetrics displayMetrics = livePlayActivity.getResources().getDisplayMetrics();
            if (booleanValue) {
                livePlayActivity.o.setVisibility(View.VISIBLE);
                f = 690.0f;
            } else {
                livePlayActivity.o.setVisibility(View.GONE);
                f = 580.0f;
            }
            layoutParams.width = (int) ((displayMetrics.density * f) + 0.5f);
            livePlayActivity.n.setLayoutParams(layoutParams);
            if (m.getVisibility() == 8) {
                m.setVisibility(View.VISIBLE);
                m.startAnimation(AnimationUtils.loadAnimation(LivePlayActivity.this, R.anim.bottom_group_slide_in_bottom));
                LivePlayActivity livePlayActivity2 = LivePlayActivity.this;
                livePlayActivity2.L.post(new androidx.base.i3.b(livePlayActivity2));
                o(LivePlayActivity.d0, 2);
            }
        }
    }

    public class h implements Runnable {
        public h() {
        }

        @Override
        public void run() {
            int i;
            j.setVisibility(View.GONE);
            j.setText("");
            int i2 = (((Boolean) Hawk.get(" hide_favor", Boolean.FALSE)).booleanValue() || ((ArrayList) androidx.base.a3.a.a().c).size() <= 0) ? 0 : 1;
            int i3 = 1;
            int i4 = 0;
            while (true) {
                if (i2 >= g.size()) {
                    i2 = 0;
                    i = 0;
                    break;
                }
                int size = LivePlayActivity.e0.f(i2).size();
                int i5 = (i3 + size) - 1;
                i4 += size;
                int i6 = i;
                if (i6 >= i3 && i6 <= i5) {
                    i = (i6 - i3) + 1;
                    break;
                } else {
                    i3 = i5 + 1;
                    i2++;
                }
            }
            int i7 = i - 1;
            LivePlayActivity livePlayActivity = LivePlayActivity.this;
            int i8 = livePlayActivity.i;
            if (i8 <= 0 || i8 > i4) {
                androidx.base.m3.j.a("无此节目号，请重新选择！", androidx.base.m3.j.b);
            } else if (i2 < livePlayActivity.g.size() && i7 < g.get(i2).getLiveChannels().size()) {
                s(i2, i7, false);
            }
            i = 0;
        }
    }

    public class i implements Runnable {
        public i() {
        }

        @Override
        public void run() {
            L.postDelayed(this, 1000L);
            VideoView videoView = e;
            int i = 0;
            if ((true ^ d0.r) && (videoView != null)) {
                int currentPosition = ((int) videoView.getCurrentPosition()) / 1000;
                if (e.isPlaying()) {
                    androidx.base.k3.c.O++;
                    androidx.base.k3.c.P++;
                }
                if (androidx.base.j3.i.g) {
                    LivePlayActivity.g0.g.setText(androidx.base.a5.b.q(androidx.base.k3.c.P + androidx.base.k3.c.M));
                    LivePlayActivity.g0.h.setProgress((float) androidx.base.k3.c.P);
                } else if (!LivePlayActivity.e0.v.getCanShiYi() || !((Boolean) Hawk.get("Time_shift_on", Boolean.TRUE)).booleanValue()) {
                    LivePlayActivity.g0.g.setText(androidx.base.k6.c.i(currentPosition * 1000));
                    LivePlayActivity.g0.h.setProgress(currentPosition);
                } else {
                    androidx.base.k3.c.N++;
                    LivePlayActivity.g0.g.setText(androidx.base.a5.b.q(androidx.base.k3.c.O + androidx.base.k3.c.M));
                    LivePlayActivity.g0.i.setText(androidx.base.a5.b.q(androidx.base.k3.c.N));
                    LivePlayActivity.g0.h.setProgress((float) androidx.base.k3.c.O);
                    TextView textView = d0.v;
                    androidx.base.j3.i iVar = LivePlayActivity.e0.r;
                    Objects.requireNonNull(iVar);
                    Date date = new Date((androidx.base.k3.c.O + androidx.base.k3.c.M) * 1000);
                    LivePlayActivity livePlayActivity = iVar.d;
                    LiveChannelItem liveChannelItem = LivePlayActivity.d0;
                    Objects.requireNonNull(livePlayActivity);
                    if (liveChannelItem != null) {
                        androidx.base.h3.a.a(new k(liveChannelItem, 3, date));
                    }
                    ArrayList<androidx.base.m3.e> seekbarEpgList = LivePlayActivity.d0.getSeekbarEpgList();
                    String str = "精彩节目";
                    if (seekbarEpgList != null) {
                        while (true) {
                            if (i >= seekbarEpgList.size()) {
                                break;
                            }
                            androidx.base.m3.e eVar = seekbarEpgList.get(i);
                            String str2 = eVar.f;
                            String str3 = eVar.g;
                            String t0 = androidx.base.a5.b.t0("HH:mm", date);
                            if (t0.compareTo(str2) >= 0 && t0.compareTo(str3) <= 0) {
                                str = eVar.c;
                                break;
                            }
                            i++;
                        }
                    }
                    textView.setText(str);
                    d0.u.setText(new SimpleDateFormat("MM月dd日 EE", Locale.CHINESE).format(new Date((androidx.base.k3.c.O + androidx.base.k3.c.M) * 1000)));
                }
            }
        }
    }

    public class j implements Runnable {
        public final String c;

        public j(String str) {
            this.c = str;
        }

        @Override
        public void run() {
            F(this.c);
            b0.e = this.c;
            B();
        }
    }

    public class k implements Runnable {
        public LiveChannelItem c;
        public int d;
        public Date e;

        public k(LiveChannelItem liveChannelItem, int i, Date date) {
            this.c = liveChannelItem;
            this.d = i;
            this.e = date;
        }

        @Override
        public void run() {
            
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.github.tvbox.osc.ui.activity.LivePlayActivity.k.run():void");
        }
    }

    public class l implements Runnable {
        public LiveChannelItem c;

        public l(LiveChannelItem liveChannelItem) {
            this.c = liveChannelItem;
        }

        @Override
        public void run() {
            int channelIndex = this.c.getChannelIndex();
            String str = this.c.getChannelNum() + "_" + this.c.getChannelName();
            if (channelIndex == -1 || K.contains(str)) {
                return;
            }
            LivePlayActivity.e0.p.notifyItemChanged(channelIndex, 1);
            K.add(str);
        }
    }

    public static void g(LivePlayActivity livePlayActivity) {
        Objects.requireNonNull(livePlayActivity);
        g0.a();
    }

    public void A(String str) {
        String str2 = (String) Hawk.get("useragent_url", "");
        String str3 = (String) Hawk.get("Referer_url", "");
        if (this.S) {
            this.S = false;
        } else {
            this.E.put("User-Agent", str2);
            this.E.put("Referer", str3);
        }
        VideoView videoView = this.e;
        Map<String, String> map = this.E;
        videoView.n = null;
        videoView.l = str;
        videoView.m = map;
    }

    public void B() {
        this.p.setVisibility((androidx.base.j3.i.g || e0.v.getCanShiYi()) && ((Boolean) Hawk.get(" Playback_ID", Boolean.FALSE)).booleanValue() && !c0.contains("直播") ? View.VISIBLE : View.GONE);
        this.p.setText(c0);
    }

    public final void C() {
        this.L.post(this.N);
        this.B.setVisibility(View.VISIBLE);
        this.D.setVisibility(View.GONE);
        this.L.removeCallbacks(this.W);
    }

    public void D() {
        if (!((Boolean) Hawk.get("live_show_net_speed", Boolean.FALSE)).booleanValue()) {
            this.A.setVisibility(View.GONE);
            this.L.removeCallbacks(this.b0);
            return;
        }
        this.A.setVisibility(View.VISIBLE);
        this.L.removeCallbacks(this.b0);
        this.L.post(this.b0);
    }

    public void E() {
        TextView textView;
        int i2 = 0;
        if (!((Boolean) Hawk.get("live_show_time", Boolean.FALSE)).booleanValue()) {
            PopupWindow popupWindow = e0.n;
            if (popupWindow == null || !popupWindow.isShowing()) {
                textView = this.z;
                i2 = 8;
                textView.setVisibility(i2);
            }
        }
        textView = this.z;
        textView.setVisibility(i2);
    }

    public void F(String str) {
        VideoView videoView = this.e;
        if (videoView == null) {
            return;
        }
        videoView.m();
        VJPlayer vJPlayer = this.U;
        if (vJPlayer != null) {
            vJPlayer.stop();
            this.U._release();
            this.U = null;
        }
        String scheme = PxpUtil.scheme(str);
        Objects.requireNonNull(scheme);
        char c2 = 65535;
        switch (scheme.hashCode()) {
            case 109294:
                if (scheme.equals("p2p")) {
                    c2 = 0;
                    break;
                }
                break;
            case 109325:
                if (scheme.equals("p3p")) {
                    c2 = 1;
                    break;
                }
                break;
            case 109356:
                if (scheme.equals("p4p")) {
                    c2 = 2;
                    break;
                }
                break;
            case 109387:
                if (scheme.equals("p5p")) {
                    c2 = 3;
                    break;
                }
                break;
            case 109418:
                if (scheme.equals("p6p")) {
                    c2 = 4;
                    break;
                }
                break;
            case 109449:
                if (scheme.equals("p7p")) {
                    c2 = 5;
                    break;
                }
                break;
            case 109480:
                if (scheme.equals("p8p")) {
                    c2 = 6;
                    break;
                }
                break;
            case 109511:
                if (scheme.equals("p9p")) {
                    c2 = 7;
                    break;
                }
                break;
            case 3351838:
                if (scheme.equals("mitv")) {
                    c2 = '\b';
                    break;
                }
                break;
            case 3620698:
                if (scheme.equals("vjms")) {
                    c2 = '\t';
                    break;
                }
                break;
            case 115991654:
                if (scheme.equals("zlive")) {
                    c2 = '\n';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
                androidx.base.e3.a aVar = new androidx.base.e3.a(this);
                this.M = aVar;
                androidx.base.h3.a.b(new a.b(str));
                return;
            case '\t':
                C();
                VJPlayer vJPlayer2 = new VJPlayer(this);
                this.U = vJPlayer2;
                vJPlayer2.setURL(str);
                this.U.start();
                return;
            case '\n':
                if (this.V == null) {
                    this.V = new ZLives();
                }
                String fetch = this.V.fetch(str);
                z(fetch);
                b0.e = fetch;
                return;
            default:
                String str2 = androidx.base.e3.b.r;
                if (str.contains("/ku9/js/")) {
                    C();
                    new androidx.base.e3.b(this, str);
                    return;
                }
                A(str);
                this.e.start();
                return;
        }
    }

    @Override
    public int c() {
        return R.layout.activity_live_play;
    }

    @Override
    public boolean dispatchKeyEvent(android.view.KeyEvent r12) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return true;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.github.tvbox.osc.ui.activity.LivePlayActivity.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    public final void h() {
        Hawk.delete("videoSelected");
        Hawk.delete("audioSelected");
        Hawk.delete("subSelected");
        Hawk.delete("subSwitch");
        Hawk.delete("lastSubSelected");
        h0.setVisibility(View.GONE);
        androidx.base.e3.b.s = "";
        androidx.base.e3.b.r = "";
        b0.e = "";
    }

    public void i() {
        String str = (String) Hawk.get("epg_url", "");
        if ((str.isEmpty() || TextUtils.isEmpty(str)) && ((Integer) Hawk.get("epgtype_select", 0)).intValue() != 2) {
            return;
        }
        new androidx.base.m3.f(this, str, false);
    }

    @Override
    @UnstableApi
    public void init() {
        e(false);
        new m0(this);
        this.y = (String) Hawk.get("epg_url", "");
        androidx.base.g3.a a2 = androidx.base.g3.a.a();
        if (a2.a == null) {
            do {
                androidx.base.g3.f fVar = new androidx.base.g3.f(androidx.base.g3.f.q, androidx.base.g3.a.d);
                a2.a = fVar;
                fVar.n = new androidx.base.a5.b(a2);
                try {
                    fVar.h(5000, true);
                    IjkMediaPlayer.setDotPort(((Integer) Hawk.get("doh_url", 0)).intValue() > 0, androidx.base.g3.f.q);
                    break;
                } catch (IOException unused) {
                    androidx.base.g3.f.q++;
                    a2.a.i();
                    if (androidx.base.g3.f.q >= 9999) {
                    }
                }
            } while (androidx.base.g3.f.q >= 9999);
        }
        this.e = (VideoView) findViewById(R.id.mVideoView);
        this.j = (TextView) findViewById(R.id.tv_selected_channel);
        this.z = (TextView) findViewById(R.id.tvTime);
        this.A = (TextView) findViewById(R.id.tvNetSpeed);
        h0 = (SubtitleView) findViewById(R.id.mSubtitleView);
        TextView textView = (TextView) findViewById(R.id.mTvSeekBack);
        this.p = textView;
        textView.setVisibility(View.GONE);
        SubtitleView subtitleView = h0;
        if (subtitleView != null) {
            subtitleView.setUserDefaultTextSize();
            h0.setApplyEmbeddedFontSizes(false);
            h0.setStyle(new CaptionStyleCompat(-1, 0, 0, 1, ViewCompat.MEASURED_STATE_MASK, null));
        }
        this.m = (LinearLayout) findViewById(R.id.tvBottomLayout);
        this.n = (LinearLayout) findViewById(R.id.BottomGroup);
        this.s = (TextView) findViewById(R.id.tv_channel_name);
        this.t = (TextView) findViewById(R.id.tv_channel_number);
        this.o = (ImageView) findViewById(R.id.tv_logo);
        this.v = (TextView) findViewById(R.id.tv_current_program_time);
        this.u = (TextView) findViewById(R.id.tv_current_program_name);
        this.x = (TextView) findViewById(R.id.tv_next_program_time);
        this.w = (TextView) findViewById(R.id.tv_next_program_name);
        this.q = (TextView) findViewById(R.id.tv_size);
        this.r = (TextView) findViewById(R.id.tv_source);
        this.G = (TextView) findViewById(R.id.tv_fps);
        this.F = (TextView) findViewById(R.id.tv_audio_track);
        this.H = (TextView) findViewById(R.id.tv_can_shiyi);
        this.I = (TextView) findViewById(R.id.tv_is_ipv6);
        this.B = (LinearLayout) findViewById(R.id.ll_line2);
        this.C = findViewById(R.id.bottom_buffer_drawable);
        this.D = (ProgressBar) findViewById(R.id.time_progressbar);
        e0 = new androidx.base.k3.c(this);
        f0 = new i0(this);
        g0 = new d0(this);
        LiveController liveController = new LiveController(this);
        this.f = liveController;
        liveController.setListener(new androidx.base.i3.a(this));
        this.f.setCanChangePosition(false);
        this.f.setEnableInNormal(true);
        this.f.setGestureEnabled(true);
        this.f.setDoubleTapTogglePlayEnabled(false);
        this.e.setVideoController(this.f);
        this.e.setProgressManager(null);
        l(false);
        try {
            p("http://127.0.0.1:9978/proxy?do=live&type=txt&ext=" + Base64.encodeToString(((String) Hawk.get("live_url", "")).getBytes(StandardCharsets.UTF_8), 10));
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (((Integer) Hawk.get("epgtype_select", 0)).intValue() != 2) {
            return;
        }
        Date date = new Date();
        if (!androidx.base.a5.b.Y((Date) Hawk.get("xmltvepg_update_time", new Date()), date)) {
            Hawk.put("xmltvepg_is_update", Boolean.FALSE);
        }
        int i2 = Calendar.getInstance().get(11);
        if (!((Boolean) Hawk.get("xmltvepg_is_update", Boolean.FALSE)).booleanValue() && i2 >= 7) {
            Boolean bool = Boolean.TRUE;
            Hawk.put("need_download_xmltvepg", bool);
            Hawk.put("xmltvepg_is_update", bool);
        }
        Hawk.put("xmltvepg_update_time", date);
        i();
    }

    public final Integer[] j(int i2) {
        int i3;
        int i4 = androidx.base.k3.c.D;
        int i5 = androidx.base.k3.c.E;
        if (i2 > 0) {
            i3 = i5 + 1;
            if (i3 >= e0.f(i4).size()) {
                if (((Boolean) Hawk.get("live_cross_group", Boolean.FALSE)).booleanValue()) {
                    while (true) {
                        i4++;
                        if (i4 >= this.g.size()) {
                            i4 = !((Boolean) Hawk.get(" hide_favor", Boolean.FALSE)).booleanValue();
                        }
                        if (this.g.get(i4).getGroupPassword().isEmpty() && i4 != androidx.base.k3.c.D) {
                            break;
                        }
                    }
                }
                i3 = 0;
            }
        } else {
            i3 = i5 - 1;
            if (i3 < 0) {
                if (((Boolean) Hawk.get("live_cross_group", Boolean.FALSE)).booleanValue()) {
                    while (true) {
                        i4--;
                        if (!((Boolean) Hawk.get(" hide_favor", Boolean.FALSE)).booleanValue() ? i4 == 0 : i4 == -1) {
                            i4 = this.g.size() - 1;
                        }
                        if (this.g.get(i4).getGroupPassword().isEmpty() && i4 != -1) {
                            break;
                        }
                    }
                }
                i3 = e0.f(i4).size() - 1;
            }
        }
        return new Integer[]{Integer.valueOf(i4), Integer.valueOf(i3)};
    }

    @android.annotation.SuppressLint({"CheckResult"})
    public void k(java.lang.String r7, android.widget.ImageView r8) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.github.tvbox.osc.ui.activity.LivePlayActivity.k(java.lang.String, android.widget.ImageView):void");
    }

    public void l(boolean z) {
        List list;
        if (z) {
            LoginActivity.i.b(true);
            list = (List) androidx.base.a3.a.a().a;
        } else {
            list = (List) androidx.base.a3.a.a().a;
            if (list.isEmpty()) {
                androidx.base.m3.j.a("频道列表为空", androidx.base.m3.j.b);
                return;
            }
        }
        if (list.size() == 1 && ((LiveChannelGroup) list.get(0)).getGroupName().startsWith("http://127.0.0.1")) {
            p(((LiveChannelGroup) list.get(0)).getGroupName());
        }
        this.g.clear();
        this.g.addAll(list);
        this.a0.clear();
        this.a0.addAll((List) androidx.base.a3.a.a().b);
        m();
    }

    public void m() {
        this.K.clear();
        int intValue = ((Integer) Hawk.get("last_live_group_index", Integer.valueOf(!((Boolean) Hawk.get(" hide_favor", Boolean.FALSE)).booleanValue()))).intValue();
        int intValue2 = ((Integer) Hawk.get("last_live_channel_index", 0)).intValue();
        while (intValue > this.g.size() - 1) {
            intValue--;
        }
        if (intValue == 0 && !((Boolean) Hawk.get(" hide_favor", Boolean.FALSE)).booleanValue() && ((ArrayList) androidx.base.a3.a.a().c).size() == 0 && this.g.size() > 1) {
            intValue = 1;
        }
        while (intValue2 > this.g.get(intValue).getLiveChannels().size() - 1) {
            intValue2--;
        }
        if (e0.f(intValue).size() != 0) {
            androidx.base.e3.c cVar = this.k;
            VideoView videoView = this.e;
            String url = e0.f(intValue).get(intValue2).getUrl();
            Objects.requireNonNull(cVar);
            try {
                androidx.base.e3.c.b = (HashMap) Hawk.get("playerHashMap", new HashMap());
                boolean booleanValue = ((Boolean) Hawk.get("pl_memory_set_select", Boolean.FALSE)).booleanValue();
                JSONObject jSONObject = new JSONObject();
                cVar.a = jSONObject;
                jSONObject.put("pl", Hawk.get("play_type", 1));
                if (booleanValue && androidx.base.e3.c.b.containsKey(url)) {
                    cVar.a = androidx.base.e3.c.b.get(url);
                }
                androidx.base.e3.c.b.put(url, cVar.a);
                Hawk.put("playerHashMap", androidx.base.e3.c.b);
                androidx.base.e3.d.b(videoView, cVar.a, url);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        E();
        D();
        e0.o.a(this.g);
        androidx.base.k3.c cVar2 = e0;
        cVar2.p.a(cVar2.f(intValue));
        e0.j(intValue, intValue2);
        androidx.base.k3.c.D = intValue;
        androidx.base.k3.c.E = intValue2;
        androidx.base.k3.c.F = androidx.base.k3.c.D;
        androidx.base.k3.c.G = androidx.base.k3.c.E;
    }

    public boolean n() {
        return e0.v != null;
    }

    @SuppressLint({"SetTextI18n"})
    public void o(LiveChannelItem liveChannelItem, int i2) {
        if (liveChannelItem == null) {
            return;
        }
        if (this.K.contains(liveChannelItem.getChannelNum() + "_" + liveChannelItem.getChannelName()) && i2 == 0) {
            return;
        }
        if (!((Boolean) Hawk.get(" Close_epg", Boolean.FALSE)).booleanValue()) {
            androidx.base.h3.a.a(new k(liveChannelItem, i2, null));
            return;
        }
        this.v.setText("00:00 - 23:59");
        this.u.setText("精彩节目");
        this.x.setText("00:00 - 23:59");
        this.w.setText("精彩节目");
    }

    @Override
    public void onBackPressed() {
        boolean booleanValue = ((Boolean) Hawk.get("Quick_exit", Boolean.FALSE)).booleanValue();
        if (androidx.base.j3.i.g || "时移".equals(c0)) {
            this.L.post(this.Z);
            c0 = "直播";
        } else if (booleanValue) {
            if (System.currentTimeMillis() - this.O < ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS) {
                super.onBackPressed();
                System.exit(0);
                return;
            }
            this.O = System.currentTimeMillis();
            androidx.base.m3.j.a("再按一次返回键退出", androidx.base.m3.j.c);
        } else {
            new u(this).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        VideoView videoView = this.e;
        if (videoView != null) {
            videoView.m();
            this.e = null;
        }
        ZLives zLives = this.V;
        if (zLives != null) {
            zLives.stop();
            this.V = null;
        }
        androidx.base.g3.f fVar = androidx.base.g3.a.a().a;
        if (fVar != null && fVar.m) {
            fVar.i();
        }
        androidx.base.g3.d dVar = androidx.base.g3.a.a().b;
        if (dVar != null && dVar.m) {
            dVar.i();
        }
        androidx.base.u5.c b2 = androidx.base.u5.c.b();
        synchronized (b2) {
            List<Class<?>> list = b2.b.get(this);
            if (list != null) {
                for (Class<?> cls : list) {
                    CopyOnWriteArrayList<o> copyOnWriteArrayList = b2.a.get(cls);
                    if (copyOnWriteArrayList != null) {
                        int size = copyOnWriteArrayList.size();
                        int i2 = 0;
                        while (i2 < size) {
                            o oVar = copyOnWriteArrayList.get(i2);
                            if (oVar.a == this) {
                                oVar.c = false;
                                copyOnWriteArrayList.remove(i2);
                                i2--;
                                size--;
                            }
                            i2++;
                        }
                    }
                }
                b2.b.remove(this);
            } else {
                b2.p.a(Level.WARNING, "Subscriber to unregister was not registered before: " + getClass());
            }
        }
        androidx.base.e3.a aVar = this.M;
        if (aVar != 0) {
            Objects.requireNonNull(aVar);
            try {
                try {
                    if (!aVar.b.isEmpty()) {
                        aVar.a.unbindService(aVar.c);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } finally {
                aVar.b.clear();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this.e != null) {
            if (f() && isInPictureInPictureMode()) {
                this.e.n();
            } else {
                this.e.pause();
            }
        }
        PopupWindow popupWindow = f0.l;
        if (popupWindow != null && popupWindow.isShowing()) {
            f0.l.dismiss();
        }
        PopupWindow popupWindow2 = e0.n;
        if (popupWindow2 != null && popupWindow2.isShowing()) {
            e0.n.dismiss();
        }
        j0 j0Var = f0.n;
        if (j0Var == null || !j0Var.isShowing()) {
            return;
        }
        f0.n.dismiss();
    }

    @Override
    public void onPictureInPictureModeChanged(boolean z) {
        super.onPictureInPictureModeChanged(z);
        if (f()) {
            if (!isInPictureInPictureMode()) {
                E();
                D();
                this.e.pause();
                this.e.start();
                if (this.Q) {
                    this.e.pause();
                    return;
                }
                return;
            }
            if (this.z.getVisibility() == 0) {
                this.z.setVisibility(View.GONE);
            }
            if (this.A.getVisibility() == 0) {
                this.A.setVisibility(View.GONE);
            }
            VideoView videoView = this.e;
            if (videoView != null) {
                videoView.pause();
                this.e.start();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        VideoView videoView = this.e;
        if (videoView != null) {
            videoView.n();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        this.Q = true;
    }

    @Override
    public void onUrlChanged(String str) {
        this.L.sendEmptyMessage(15);
        A(str);
        b0.e = str;
    }

    @Override
    public void onUserLeaveHint() {
        boolean booleanValue = ((Boolean) Hawk.get("pic_in_pic", Boolean.FALSE)).booleanValue();
        if (f() && booleanValue) {
            enterPictureInPictureMode();
            this.L.post(e0.z);
            this.L.post(this.W);
            this.L.post(f0.q);
        }
    }

    public void p(String str) {
        try {
            String str2 = new String(Base64.decode(Uri.parse(str).getQueryParameter("ext"), 10), StandardCharsets.UTF_8);
            if (str2.equals("")) {
                return;
            }
            new androidx.base.m3.f(this, str2, true);
        } catch (Throwable unused) {
            androidx.base.m3.j.a("频道列表为空", androidx.base.m3.j.b);
        }
    }

    public void q(String str) {
        androidx.base.a5.b.y(str, androidx.base.m3.b.g, "channelDownload.txt");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        androidx.base.m3.k.c(linkedHashMap, str, null);
        androidx.base.a3.a.a().b(androidx.base.m3.k.b(linkedHashMap), 0);
        List list = (List) androidx.base.a3.a.a().a;
        if (list.isEmpty()) {
            androidx.base.m3.j.a("网络列表为空", androidx.base.m3.j.b);
            return;
        }
        this.a0.clear();
        this.a0.addAll((List) androidx.base.a3.a.a().b);
        this.g.clear();
        this.g.addAll(list);
        this.L.post(new d());
    }

    public boolean r(int r6, int r7, boolean r8, boolean r9) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return true;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.github.tvbox.osc.ui.activity.LivePlayActivity.r(int, int, boolean, boolean):boolean");
    }

    public boolean s(int i2, int i3, boolean z) {
        if (!((i2 == androidx.base.k3.c.D && i3 == androidx.base.k3.c.E && !z) || (z && e0.v.getSourceNum() == 1)) || androidx.base.k3.c.L) {
            androidx.base.k3.c.L = false;
            r(i2, i3, z, false);
            return true;
        }
        return true;
    }

    public final void t() {
        if (n()) {
            Integer[] j2 = j(1);
            s(j2[0].intValue(), j2[1].intValue(), false);
            e0.r.e(false);
        }
    }

    public void u() {
        if (this.e != null && n()) {
            e0.v.nextSource();
            s(androidx.base.k3.c.D, androidx.base.k3.c.E, true);
            e0.r.e(false);
        }
    }

    public void v() {
        if (n()) {
            e0.v.preSource();
            s(androidx.base.k3.c.D, androidx.base.k3.c.E, true);
            e0.r.e(false);
        }
    }

    public final void w() {
        if (n()) {
            Integer[] j2 = j(-1);
            s(j2[0].intValue(), j2[1].intValue(), false);
            e0.r.e(false);
        }
    }

    public void x() {
        String packageName = getApplicationContext().getPackageName();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, LoginActivity.class.getName()));
        intent.addFlags(335577088);
        getApplicationContext().startActivity(intent);
        System.exit(0);
        Process.killProcess(Process.myPid());
    }

    public final boolean y() {
        TextView textView;
        String str;
        VideoView videoView = this.e;
        if (videoView == null) {
            return true;
        }
        videoView.m();
        int i2 = androidx.base.k3.c.D;
        int i3 = androidx.base.k3.c.E;
        if (i2 != -1 && i3 != -1) {
            androidx.base.k3.c cVar = e0;
            cVar.v = cVar.f(i2).get(i3);
            Hawk.put("last_live_channel_name", e0.v.getChannelName());
            Hawk.put("last_live_group_index", Integer.valueOf(i2));
            Hawk.put("last_live_channel_index", Integer.valueOf(i3));
            this.T.put(i2 + "-" + i3 + "-" + e0.v.getChannelName(), Integer.valueOf(e0.v.getSourceIndex()));
            Hawk.put("source", this.T);
            e0.r.e(false);
            this.k.a(this.e, e0.v.getUrl());
            LiveChannelItem liveChannelItem = e0.v;
            d0 = liveChannelItem;
            e0.v.setinclude_back(liveChannelItem.getCanShiYi());
            e0.v.setIs_ipv6(e0.v.isIpv6());
            this.L.removeCallbacks(this.R);
            this.L.post(this.R);
            this.s.setText(d0.getChannelName());
            TextView textView2 = this.t;
            StringBuilder d2 = androidx.base.a.b.d("");
            d2.append(d0.getChannelNum());
            textView2.setText(d2.toString());
            LiveChannelItem liveChannelItem2 = d0;
            if (liveChannelItem2 == null || liveChannelItem2.getSourceNum() <= 0) {
                textView = this.r;
                str = "1/1";
            } else {
                textView = this.r;
                StringBuilder d3 = androidx.base.a.b.d("线路");
                d3.append(d0.getSourceIndex() + 1);
                d3.append("/");
                d3.append(d0.getSourceNum());
                str = d3.toString();
            }
            textView.setText(str);
            h();
            this.L.post(new androidx.base.i3.e(this, e0.v.getUrl()));
        }
        return true;
    }

    public void z(String str) {
        this.L.post(new j(str));
    }
}
