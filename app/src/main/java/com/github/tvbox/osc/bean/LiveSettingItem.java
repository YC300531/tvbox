package com.github.tvbox.osc.bean;
public class LiveSettingItem {
    private int itemIndex;
    private String itemName;
    private boolean itemSelected = false;

    public int getItemIndex() {
        return this.itemIndex;
    }

    public String getItemName() {
        return this.itemName;
    }

    public boolean isItemSelected() {
        return this.itemSelected;
    }

    public void setItemIndex(int i) {
        this.itemIndex = i;
    }

    public void setItemName(String str) {
        this.itemName = str;
    }

    public void setItemSelected(boolean z) {
        this.itemSelected = z;
    }
}
