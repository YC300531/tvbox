package com.github.tvbox.osc.player;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.text.TextUtils;
import androidx.base.u5.h;
import androidx.media3.common.text.Cue;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.audio.SilenceSkippingAudioProcessor;
import androidx.media3.exoplayer.upstream.CmcdConfiguration;
import androidx.media3.ui.SubtitleView;
import com.github.tvbox.osc.bean.TrackInfo;
import com.github.tvbox.osc.bean.TrackInfoBean;
import com.github.tvbox.osc.ui.activity.LivePlayActivity;
import java.util.ArrayList;
import java.util.Map;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;
import tv.danmaku.ijk.media.player.misc.IjkTrackInfo;
import xyz.doikki.videoplayer.ijk.IjkPlayer;
import xyz.doikki.videoplayer.player.BaseVideoView;
public class IjkmPlayer extends IjkPlayer {
    public int g;
    public String h;

    public IjkmPlayer(Context context, int i, String str) {
        super(context);
        this.g = i;
        this.h = str;
    }

    private void setDataSourceHeader(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        String str = map.get("User-Agent");
        if (!TextUtils.isEmpty(str)) {
            this.d.setOption(1, "user_agent", str);
            map.remove("User-Agent");
        }
        if (map.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey());
                sb.append(":");
                if (!TextUtils.isEmpty(entry.getValue())) {
                    sb.append(entry.getValue());
                }
                sb.append("\r\n");
                this.d.setOption(1, "headers", sb.toString());
            }
        }
    }

    @Override
    public void g(String str, Map<String, String> map) {
        setDataSourceHeader(map);
        super.g(str, map);
    }

    public TrackInfo getTrackInfo() {
        TrackInfoBean trackInfoBean;
        IjkTrackInfo[] trackInfo = this.d.getTrackInfo();
        if (trackInfo == null) {
            return null;
        }
        TrackInfo trackInfo2 = new TrackInfo();
        int selectedTrack = this.d.getSelectedTrack(3);
        int selectedTrack2 = this.d.getSelectedTrack(2);
        int i = 1;
        int selectedTrack3 = this.d.getSelectedTrack(1);
        int length = trackInfo.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            IjkTrackInfo ijkTrackInfo = trackInfo[i2];
            if (ijkTrackInfo.getTrackType() == i) {
                String infoInline = ijkTrackInfo.getInfoInline();
                TrackInfoBean trackInfoBean2 = new TrackInfoBean();
                trackInfoBean2.type = "VIDEO";
                trackInfoBean2.name = infoInline;
                trackInfoBean2.language = ijkTrackInfo.getLanguage();
                trackInfoBean2.trackId = i3;
                trackInfoBean2.videoSelected = i3 == selectedTrack3;
                trackInfo2.addTrackList(trackInfoBean2);
            } else {
                if (ijkTrackInfo.getTrackType() == 2) {
                    String infoInline2 = ijkTrackInfo.getInfoInline();
                    trackInfoBean = new TrackInfoBean();
                    trackInfoBean.type = "AUDIO";
                    trackInfoBean.name = infoInline2;
                    trackInfoBean.language = ijkTrackInfo.getLanguage();
                    trackInfoBean.trackId = i3;
                    trackInfoBean.audioSelected = i3 == selectedTrack2;
                } else if (ijkTrackInfo.getTrackType() == 3) {
                    String infoInline3 = ijkTrackInfo.getInfoInline();
                    trackInfoBean = new TrackInfoBean();
                    trackInfoBean.type = "TEXT";
                    trackInfoBean.name = infoInline3;
                    trackInfoBean.language = ijkTrackInfo.getLanguage();
                    trackInfoBean.trackId = i3;
                    trackInfoBean.subSelected = i3 == selectedTrack;
                }
                trackInfo2.addTrackList(trackInfoBean);
            }
            i3++;
            i2++;
            i = 1;
        }
        return trackInfo2;
    }

    @Override
    public void j() {
        this.d.setOption(4, "subtitle", 1L);
        this.d.setOption(4, "framedrop", 1L);
        this.d.setOption(4, "overlay-format", 842225234L);
        this.d.setOption(4, "packet-buffering", 0L);
        this.d.setOption(4, "soundtouch", 1L);
        this.d.setOption(4, "start-on-prepared", 1L);
        this.d.setOption(4, "opensles", 0L);
        this.d.setOption(4, "enable-accurate-seek", 0L);
        this.d.setOption(4, "reconnect", 1L);
        this.d.setOption(2, "skip_loop_filter", 0L);
        this.d.setOption(1, "dns_cache_clear", 1L);
        this.d.setOption(1, "dns_cache_timeout", -1L);
        this.d.setOption(1, "safe", 0L);
        this.d.setOption(1, "http-detect-range-support", 0L);
        this.d.setOption(1, "delay-optimization", 1L);
        this.d.setOption(1, "cache-buffer-duration", 20000L);
        this.d.setOption(1, "fflags", "fastseek");
        this.d.setOption(1, "analyzeduration", SilenceSkippingAudioProcessor.DEFAULT_MAX_SILENCE_TO_KEEP_DURATION_US);
        this.d.setOption(4, "mediacodec", this.g);
        this.d.setOption(4, "mediacodec-hevc", this.g);
        this.d.setOption(4, "mediacodec-avc", this.g);
        this.d.setOption(4, "mediacodec-all-videos", this.g);
        this.d.setOption(4, "mediacodec-auto-rotate", this.g);
        this.d.setOption(4, "mediacodec-handle-resolution-change", this.g);
        this.d.setOption(4, "enable-accurate-seek", 1L);
        if (!this.h.contains("rtsp") && !this.h.contains("udp") && !this.h.contains(CmcdConfiguration.KEY_MAXIMUM_REQUESTED_BITRATE)) {
            this.d.setOption(1, "probsize", 409600L);
            return;
        }
        this.d.setOption(1, "infbuf", 1L);
        this.d.setOption(1, "rtsp_transport", "tcp");
        this.d.setOption(1, "rtsp_flags", "prefer_tcp");
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        super.onPrepared(iMediaPlayer);
        ((BaseVideoView) this.c).B = getTrackInfo();
    }

    @Override
    @UnstableApi
    public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {
        SubtitleView subtitleView;
        int i;
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Cue.Builder().setText(ijkTimedText.getText().replaceAll("\\{\\\\.*?\\}", "")).build());
        if (TextUtils.isEmpty(ijkTimedText.getText())) {
            subtitleView = LivePlayActivity.h0;
            i = 8;
        } else {
            LivePlayActivity.h0.setCues(arrayList);
            subtitleView = LivePlayActivity.h0;
            i = 0;
        }
        subtitleView.setVisibility(i);
    }

    @Override
    public void setDataSource(AssetFileDescriptor assetFileDescriptor) {
        try {
            this.d.setDataSource(new h(assetFileDescriptor));
        } catch (Exception unused) {
            ((BaseVideoView) this.c).h();
        }
    }

    public void setOnTimedTextListener(IMediaPlayer.OnTimedTextListener onTimedTextListener) {
        this.d.setOnTimedTextListener(onTimedTextListener);
    }

    public void setTrack(int i) {
        this.d.deselectTrack(i);
        this.d.selectTrack(i);
    }
}
