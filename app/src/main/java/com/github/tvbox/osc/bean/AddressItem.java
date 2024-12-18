package com.github.tvbox.osc.bean;
public class AddressItem {
    private int EpgType;
    private String Url;
    private String UrlName;

    public int getEpgType() {
        return this.EpgType;
    }

    public String getLiveUrl() {
        return this.Url;
    }

    public String getLiveUrlName() {
        return this.UrlName;
    }

    public void setEpgType(int i) {
        this.EpgType = i;
    }

    public void setLiveUrl(String str) {
        this.Url = str;
    }

    public void setLiveUrlName(String str) {
        this.UrlName = str;
    }
}
