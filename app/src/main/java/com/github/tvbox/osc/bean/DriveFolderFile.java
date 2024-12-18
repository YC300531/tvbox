package com.github.tvbox.osc.bean;

import android.util.Base64;
import androidx.base.a.a;
import androidx.base.c3.c;
import androidx.base.n3.e;
import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
public class DriveFolderFile {
    private String[] accessingPath;
    private List<DriveFolderFile> children;
    private JsonObject config;
    private c driveData;
    public String fileType;
    public String fileUrl;
    public boolean isDelMode;
    public boolean isFile;
    public boolean isSelected;
    public Long lastModifiedDate;
    public String name;
    public DriveFolderFile parentFolder;
    public int version;

    public DriveFolderFile(c cVar) {
        this.version = 0;
        this.accessingPath = new String[0];
        Objects.requireNonNull(cVar);
        throw null;
    }

    public DriveFolderFile(DriveFolderFile driveFolderFile, String str, int i, boolean z, String str2, Long l) {
        this.version = 0;
        this.accessingPath = new String[0];
        if (driveFolderFile != null) {
            LinkedList linkedList = new LinkedList();
            for (DriveFolderFile driveFolderFile2 = driveFolderFile; driveFolderFile2 != null; driveFolderFile2 = driveFolderFile2.parentFolder) {
                linkedList.add(0, driveFolderFile2.name);
            }
            this.accessingPath = (String[]) linkedList.toArray(new String[linkedList.size()]);
        }
        this.parentFolder = driveFolderFile;
        this.name = str;
        this.version = i;
        this.isFile = z;
        if (str2 != null) {
            this.fileType = str2.toUpperCase(Locale.ROOT);
        }
        this.lastModifiedDate = l;
    }

    public String[] getAccessingPath() {
        String[] strArr = this.accessingPath;
        return (String[]) Arrays.copyOf(strArr, strArr.length);
    }

    public String getAccessingPathStr() {
        String str = "";
        for (String str2 : this.accessingPath) {
            str = a.j(str, str2, "/");
        }
        return str;
    }

    public List<DriveFolderFile> getChildren() {
        return this.children;
    }

    public JsonObject getConfig() {
        return this.config;
    }

    public c getDriveData() {
        return null;
    }

    public e getDriveType() {
        e.values();
        Objects.requireNonNull(null);
        throw null;
    }

    public String getFormattedLastModified() {
        if (this.lastModifiedDate != null) {
            return new SimpleDateFormat("MM/dd/yyyy hh:mm aa").format((Object) new Date(this.lastModifiedDate.longValue()));
        }
        return "";
    }

    public String getWebDAVBase64Credential() {
        try {
            if (this.config.has("username") && this.config.has("password")) {
                return Base64.encodeToString((this.config.get("username").getAsString() + ":" + this.config.get("password").getAsString()).getBytes(StandardCharsets.UTF_8), 2);
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public boolean isDrive() {
        return false;
    }

    public void setAccessingPath(String[] strArr) {
        this.accessingPath = strArr;
    }

    public void setChildren(List<DriveFolderFile> list) {
        this.children = list;
    }

    public void setConfig(JsonObject jsonObject) {
        this.config = jsonObject;
    }

    public void setDriveData(c cVar) {
    }
}
