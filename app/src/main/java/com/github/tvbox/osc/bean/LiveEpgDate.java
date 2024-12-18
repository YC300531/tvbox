package com.github.tvbox.osc.bean;

import java.util.Date;
public class LiveEpgDate {
    private Date dateParamVal;
    private String datePresented;
    private int index;
    private String month;

    public Date getDateParamVal() {
        return this.dateParamVal;
    }

    public String getDatePresented() {
        return this.datePresented;
    }

    public int getIndex() {
        return this.index;
    }

    public String getMonth() {
        return this.month;
    }

    public void setDateParamVal(Date date) {
        this.dateParamVal = date;
    }

    public void setDatePresented(String str) {
        this.datePresented = str;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public void setMonth(String str) {
        this.month = str;
    }
}
