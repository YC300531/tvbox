package com.github.tvbox.osc.bean;

import androidx.base.m3.e;
import java.util.ArrayList;
import java.util.regex.Pattern;
public class LiveChannelItem {
    private String ChannelEpg;
    private ArrayList<e> channelEpgList;
    private int channelGroupIndex;
    private String channelGroupName;
    private int channelIndex;
    private String channelName;
    private int channelNum;
    private ArrayList<String> channelSourceNames;
    private ArrayList<String> channelUrls;
    private boolean isFavor;
    private ArrayList<e> seekbarEpgList;
    public int sourceIndex = 0;
    public int sourceNum = 0;
    public boolean include_back = false;
    public boolean is_ipv6 = false;

    public boolean getCanShiYi() {
        while (this.sourceIndex > this.channelUrls.size() - 1) {
            this.sourceIndex--;
        }
        String str = this.channelUrls.get(this.sourceIndex);
        return str.contains("/PLTV") || str.contains("/TVOD") || str.contains("/gitv_live") || str.contains("ysten-businessmobile") || str.contains("ysten-business") || str.contains("itv.cmvideo.cn") || str.contains("/live/program/live/") || str.contains("userid=gf001");
    }

    public String getChannelEpg() {
        return this.ChannelEpg;
    }

    public ArrayList<e> getChannelEpgList() {
        ArrayList<e> arrayList = this.channelEpgList;
        return arrayList != null ? arrayList : new ArrayList<>();
    }

    public int getChannelGroupIndex() {
        return this.channelGroupIndex;
    }

    public String getChannelGroupName() {
        return this.channelGroupName;
    }

    public int getChannelIndex() {
        return this.channelIndex;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public int getChannelNum() {
        return this.channelNum;
    }

    public ArrayList<String> getChannelSourceNames() {
        return this.channelSourceNames;
    }

    public ArrayList<String> getChannelUrls() {
        return this.channelUrls;
    }

    public ArrayList<e> getSeekbarEpgList() {
        return this.seekbarEpgList;
    }

    public int getSourceIndex() {
        return this.sourceIndex;
    }

    public String getSourceName() {
        return this.channelSourceNames.get(this.sourceIndex);
    }

    public int getSourceNum() {
        return this.sourceNum;
    }

    public String getUrl() {
        if (this.sourceIndex > this.channelUrls.size() - 1) {
            this.sourceIndex--;
        }
        return this.channelUrls.get(this.sourceIndex);
    }

    public boolean isFavor() {
        return this.isFavor;
    }

    public boolean isIpv6() {
        while (this.sourceIndex > this.channelUrls.size() - 1) {
            this.sourceIndex--;
        }
        return Pattern.compile("^((http|https)://)?(\\[[0-9a-fA-F:]+])(:[0-9]+)?(/.*)?$").matcher(this.channelUrls.get(this.sourceIndex)).matches();
    }

    public void nextSource() {
        int i = this.sourceIndex + 1;
        this.sourceIndex = i;
        if (i == this.sourceNum) {
            this.sourceIndex = 0;
        }
    }

    public void preSource() {
        int i = this.sourceIndex - 1;
        this.sourceIndex = i;
        if (i < 0) {
            this.sourceIndex = this.sourceNum - 1;
        }
    }

    public void setChannelEpg(String str) {
        this.ChannelEpg = str;
    }

    public void setChannelEpgList(ArrayList<e> arrayList) {
        this.channelEpgList = arrayList;
    }

    public void setChannelGroupIndex(int i) {
        this.channelGroupIndex = i;
    }

    public void setChannelGroupName(String str) {
        this.channelGroupName = str;
    }

    public void setChannelIndex(int i) {
        this.channelIndex = i;
    }

    public void setChannelName(String str) {
        this.channelName = str;
    }

    public void setChannelNum(int i) {
        this.channelNum = i;
    }

    public void setChannelSourceNames(ArrayList<String> arrayList) {
        this.channelSourceNames = arrayList;
    }

    public void setChannelUrls(ArrayList<String> arrayList) {
        this.channelUrls = arrayList;
        this.sourceNum = arrayList.size();
    }

    public void setFavor(boolean z) {
        this.isFavor = z;
    }

    public void setIs_ipv6(boolean z) {
        this.is_ipv6 = z;
    }

    public void setSeekbarEpgList(ArrayList<e> arrayList) {
        this.seekbarEpgList = arrayList;
    }

    public void setSourceIndex(int i) {
        this.sourceIndex = i;
    }

    public void setinclude_back(boolean z) {
        this.include_back = z;
    }
}
