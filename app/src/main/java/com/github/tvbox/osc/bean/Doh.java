package com.github.tvbox.osc.bean;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.palayer.ku9.R;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Doh {
    @SerializedName("ips")
    private List<String> ips;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;

    public static List<Doh> arrayFrom(JsonElement jsonElement) {
        List<Doh> list = (List) new Gson().fromJson(jsonElement, new TypeToken<List<Doh>>() {
        }.getType());
        return list == null ? new ArrayList() : list;
    }

    public static List<Doh> get(Context context) {
        ArrayList arrayList = new ArrayList();
        String[] stringArray = context.getResources().getStringArray(R.array.doh_url);
        String[] stringArray2 = context.getResources().getStringArray(R.array.doh_name);
        for (int i = 0; i < stringArray2.length; i++) {
            arrayList.add(new Doh().name(stringArray2[i]).url(stringArray[i]));
        }
        return arrayList;
    }

    public static Doh objectFrom(String str) {
        Doh doh = (Doh) new Gson().fromJson(str,  Doh.class);
        return doh == null ? new Doh() : doh;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Doh) {
            return getUrl().equals(((Doh) obj).getUrl());
        }
        return false;
    }

    public List<InetAddress> getHosts() {
        try {
            ArrayList arrayList = new ArrayList();
            for (String str : getIps()) {
                arrayList.add(InetAddress.getByName(str));
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            return arrayList;
        } catch (Exception unused) {
            return null;
        }
    }

    public List<String> getIps() {
        List<String> list = this.ips;
        return list == null ? Collections.emptyList() : list;
    }

    public String getName() {
        return TextUtils.isEmpty(this.name) ? "" : this.name;
    }

    public String getUrl() {
        return TextUtils.isEmpty(this.url) ? "" : this.url;
    }

    public Doh name(String str) {
        this.name = str;
        return this;
    }

    @NonNull
    public String toString() {
        return new Gson().toJson(this);
    }

    public Doh url(String str) {
        this.url = str;
        return this;
    }
}
