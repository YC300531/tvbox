package com.github.tvbox.osc.bean;

import java.util.ArrayList;
import java.util.List;
public class TrackInfo {
    private final List<TrackInfoBean> trackList = new ArrayList();

    public void addTrackList(TrackInfoBean trackInfoBean) {
        this.trackList.add(trackInfoBean);
    }

    public int getExoAudioSelected(List<TrackInfoBean> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).audioSelected) {
                return i;
            }
        }
        return -1;
    }

    public int getExoSubSelected(List<TrackInfoBean> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).subSelected) {
                return i;
            }
        }
        return -1;
    }

    public int getExoVideoSelected(List<TrackInfoBean> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).videoSelected) {
                return i;
            }
        }
        return -1;
    }

    public int getIjkAudioSelected(List<TrackInfoBean> list, boolean z) {
        int i = 0;
        for (TrackInfoBean trackInfoBean : list) {
            if (trackInfoBean.audioSelected) {
                return z ? trackInfoBean.trackId : i;
            }
            i++;
        }
        return -1;
    }

    public int getIjkSubSelected(List<TrackInfoBean> list, boolean z) {
        int i = 0;
        for (TrackInfoBean trackInfoBean : list) {
            if (trackInfoBean.subSelected) {
                return z ? trackInfoBean.trackId : i;
            }
            i++;
        }
        return -1;
    }

    public int getIjkVideoSelected(List<TrackInfoBean> list, boolean z) {
        int i = 0;
        for (TrackInfoBean trackInfoBean : list) {
            if (trackInfoBean.videoSelected) {
                return z ? trackInfoBean.trackId : i;
            }
            i++;
        }
        return -1;
    }

    public List<TrackInfoBean> getTrackList() {
        return this.trackList;
    }
}
