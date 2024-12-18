package com.github.tvbox.osc.bean;

import java.util.ArrayList;
public class LiveChannelGroup {
    private ArrayList<LiveChannelItem> PasswordChannelItems = new ArrayList<>();
    private int groupIndex;
    private String groupName;
    private String groupPassword;
    private boolean isRemove;
    private ArrayList<LiveChannelItem> liveChannelItems;

    public int getGroupIndex() {
        return this.groupIndex;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public String getGroupPassword() {
        return this.groupPassword;
    }

    public boolean getIsRemove() {
        return this.isRemove;
    }

    public ArrayList<LiveChannelItem> getLiveChannels() {
        return this.liveChannelItems;
    }

    public ArrayList<LiveChannelItem> getPasswordChannelItems() {
        return this.PasswordChannelItems;
    }

    public void setGroupIndex(int i) {
        this.groupIndex = i;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public void setGroupPassword(String str) {
        this.groupPassword = str;
    }

    public void setIsRemove(boolean z) {
        this.isRemove = z;
    }

    public void setLiveChannels(ArrayList<LiveChannelItem> arrayList) {
        this.liveChannelItems = arrayList;
    }

    public void setPasswordChannelItems(ArrayList<LiveChannelItem> arrayList) {
        this.PasswordChannelItems = arrayList;
    }
}
