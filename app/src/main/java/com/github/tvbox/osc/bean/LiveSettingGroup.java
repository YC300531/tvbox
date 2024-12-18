package com.github.tvbox.osc.bean;

import java.util.ArrayList;
public class LiveSettingGroup {
    private int groupIndex;
    private String groupName;
    private ArrayList<LiveSettingItem> liveSettingItems;

    public int getGroupIndex() {
        return this.groupIndex;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public ArrayList<LiveSettingItem> getLiveSettingItems() {
        return this.liveSettingItems;
    }

    public void setGroupIndex(int i) {
        this.groupIndex = i;
    }

    public void setGroupName(String str) {
        this.groupName = str;
    }

    public void setLiveSettingItems(ArrayList<LiveSettingItem> arrayList) {
        this.liveSettingItems = arrayList;
    }
}
