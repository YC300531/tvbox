package com.github.tvbox.osc.player;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.base.a.a;
import androidx.base.n3.f;
import androidx.base.r3.m2;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.Player;
import androidx.media3.common.TrackGroup;
import androidx.media3.common.Tracks;
import androidx.media3.common.VideoSize;
import androidx.media3.common.text.Cue;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.source.TrackGroupArray;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import androidx.media3.exoplayer.trackselection.MappingTrackSelector;
import androidx.media3.ui.SubtitleView;
import com.github.tvbox.osc.bean.TrackInfo;
import com.github.tvbox.osc.bean.TrackInfoBean;
import com.github.tvbox.osc.ui.activity.LivePlayActivity;
import java.util.List;
import xyz.doikki.videoplayer.exo.ExoMediaPlayer;
import xyz.doikki.videoplayer.player.BaseVideoView;
public class EXOmPlayer extends ExoMediaPlayer {
    public String q;
    public String r;
    public String s;

    public EXOmPlayer(Context context) {
        super(context);
        this.q = "";
        this.r = "";
        this.s = "";
    }

    @SuppressLint({"UnsafeOptInUsageError"})
    private void getExoSelectedTrack() {
        this.q = "";
        this.r = "";
        this.s = "";
        m2<Tracks.Group> it = this.e.getCurrentTracks().getGroups().iterator();
        while (it.hasNext()) {
            Tracks.Group next = it.next();
            if (next.isSelected()) {
                for (int i = 0; i < next.length; i++) {
                    if (next.isTrackSelected(i)) {
                        Format trackFormat = next.getTrackFormat(i);
                        if (MimeTypes.isVideo(trackFormat.sampleMimeType)) {
                            this.q = trackFormat.id;
                        } else if (MimeTypes.isAudio(trackFormat.sampleMimeType)) {
                            this.r = trackFormat.id;
                        } else if (MimeTypes.isText(trackFormat.sampleMimeType)) {
                            this.s = trackFormat.id;
                        }
                    }
                }
            }
        }
    }

    @SuppressLint({"UnsafeOptInUsageError"})
    public TrackInfo getTrackInfo() {
        TrackInfoBean trackInfoBean;
        TrackInfo trackInfo = new TrackInfo();
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = getTrackSelector().getCurrentMappedTrackInfo();
        if (currentMappedTrackInfo != null) {
            getExoSelectedTrack();
            Format videoFormat = this.e.getVideoFormat();
            if (videoFormat != null) {
                this.q = videoFormat.id;
            }
            Format audioFormat = this.e.getAudioFormat();
            if (audioFormat != null) {
                this.r = audioFormat.id;
            }
            for (int i = 0; i < currentMappedTrackInfo.getRendererCount(); i++) {
                TrackGroupArray trackGroups = currentMappedTrackInfo.getTrackGroups(i);
                for (int i2 = 0; i2 < trackGroups.length; i2++) {
                    TrackGroup trackGroup = trackGroups.get(i2);
                    for (int i3 = 0; i3 < trackGroup.length; i3++) {
                        Format format = trackGroup.getFormat(i3);
                        boolean z = true;
                        if (MimeTypes.isVideo(format.sampleMimeType)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(format.sampleMimeType);
                            sb.append(", ");
                            sb.append(this.h.d(format));
                            sb.append(", ");
                            String k = a.k(sb, (int) format.frameRate, "FPS");
                            trackInfoBean = new TrackInfoBean();
                            trackInfoBean.type = "VIDEO";
                            trackInfoBean.name = k;
                            trackInfoBean.language = "";
                            trackInfoBean.trackId = i3;
                            trackInfoBean.videoSelected = (f.a(this.q) || !this.q.equals(format.id)) ? false : false;
                        } else if (MimeTypes.isAudio(format.sampleMimeType)) {
                            int i4 = format.sampleRate;
                            String str = i4 == -1 ? "-1" : i4 + "Hz";
                            trackInfoBean = new TrackInfoBean();
                            trackInfoBean.type = "AUDIO";
                            trackInfoBean.name = format.sampleMimeType + ", " + this.h.d(format) + ", " + str;
                            trackInfoBean.language = "";
                            trackInfoBean.trackId = i3;
                            trackInfoBean.audioSelected = (f.a(this.r) || !this.r.equals(format.id)) ? false : false;
                        } else if (MimeTypes.isText(format.sampleMimeType)) {
                            trackInfoBean = new TrackInfoBean();
                            trackInfoBean.type = "TEXT";
                            trackInfoBean.name = format.sampleMimeType + ", " + this.h.d(format);
                            trackInfoBean.language = "";
                            trackInfoBean.trackId = i3;
                            trackInfoBean.subSelected = (f.a(this.s) || !this.s.equals(format.id)) ? false : false;
                        }
                        trackInfoBean.trackGroupId = i2;
                        trackInfoBean.renderId = i;
                        trackInfo.addTrackList(trackInfoBean);
                    }
                }
            }
        }
        return trackInfo;
    }

    @SuppressLint({"UnsafeOptInUsageError"})
    public void j(@Nullable TrackInfoBean trackInfoBean, boolean z, boolean z2) {
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = getTrackSelector().getCurrentMappedTrackInfo();
        if (currentMappedTrackInfo == null || trackInfoBean == null) {
            return;
        }
        TrackGroupArray trackGroups = currentMappedTrackInfo.getTrackGroups(trackInfoBean.renderId);
        DefaultTrackSelector.SelectionOverride selectionOverride = new DefaultTrackSelector.SelectionOverride(trackInfoBean.trackGroupId, trackInfoBean.trackId);
        DefaultTrackSelector.Parameters.Builder buildUponParameters = getTrackSelector().buildUponParameters();
        if (z2 && !z) {
            buildUponParameters.setRendererDisabled(trackInfoBean.renderId, true);
            LivePlayActivity.h0.setVisibility(View.GONE);
        } else {
            buildUponParameters.setRendererDisabled(trackInfoBean.renderId, false);
        }
        buildUponParameters.setSelectionOverride(trackInfoBean.renderId, trackGroups, selectionOverride);
        getTrackSelector().setParameters(buildUponParameters);
    }

    @Override
    @UnstableApi
    public void onCues(List<Cue> list) {
        SubtitleView subtitleView;
        int i;
        androidx.base.a0.f.e(this, list);
        if (list.size() > 0) {
            LivePlayActivity.h0.setCues(list);
            subtitleView = LivePlayActivity.h0;
            i = 0;
        } else {
            subtitleView = LivePlayActivity.h0;
            i = 8;
        }
        subtitleView.setVisibility(i);
    }

    @Override
    public void onVideoSizeChanged(@NonNull VideoSize videoSize) {
        super.onVideoSizeChanged(videoSize);
        ((BaseVideoView) this.c).B = getTrackInfo();
    }

    public void setOnTimedTextListener(Player.Listener listener) {
        this.e.addListener(listener);
    }
}
