package com.github.tvbox.osc.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.base.k2.g;
import androidx.base.k3.a0;
import androidx.base.m3.b;
import androidx.base.m3.c;
import androidx.base.m3.d;
import androidx.base.o1.e;
import androidx.base.o1.f;
import androidx.base.u1.l;
import com.github.tvbox.osc.base.App;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.palayer.ku9.R;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Pattern;
public class LoginActivity extends com.github.tvbox.osc.base.a implements Handler.Callback, b.b, Runnable {
    public static String h;
    public static b i;
    public static final Pattern j = Pattern.compile("#[0-9a-fA-F]{6}");
    public TextView e;
    public ProgressBar f;
    public Handler g = new Handler(Looper.getMainLooper(), this);

    public class a extends androidx.base.l2.b<Drawable> {
        public final boolean f;
        public final RelativeLayout g;
        public final ImageView h;

        public a(boolean z, RelativeLayout relativeLayout, ImageView imageView) {
            this.f = z;
            this.g = relativeLayout;
            this.h = imageView;
        }

        @Override
        public void c(@NonNull Object obj, @Nullable androidx.base.m2.b bVar) {
            Drawable drawable = (Drawable) obj;
            if (this.f) {
                this.g.setBackground(drawable);
            } else {
                this.h.setImageDrawable(drawable);
            }
        }

        @Override
        @SuppressLint({"UseCompatLoadingForDrawables"})
        public void j(@Nullable Drawable drawable) {
        }
    }

    @SuppressLint({"CheckResult"})
    public static void g(String str, ImageView imageView, RelativeLayout relativeLayout, boolean z) {
        g gVar = new g();
        gVar.d(l.a);
        f d = com.bumptech.glide.a.d(App.c);
        Objects.requireNonNull(d);
        e a2 = new e(d.c, d, Drawable.class, d.d).x(str).a(gVar);
        a2.w(new a(z, relativeLayout, imageView), null, a2, androidx.base.o2.e.a);
    }

    @Override
    public int c() {
        return R.layout.activity_login;
    }

    public void h() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] strArr = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
            boolean z = false;
            int i2 = 0;
            while (true) {
                if (i2 >= 2) {
                    z = true;
                    break;
                } else if (checkCallingOrSelfPermission(strArr[i2]) != 0) {
                    break;
                } else {
                    i2++;
                }
            }
            if (!z) {
                requestPermissions(strArr, 7);
                return;
            }
        }
        androidx.base.h3.a.a(this);
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        if (message.what == 22) {
            startActivity(new Intent(this, LivePlayActivity.class));
            finish();
            return true;
        }
        return true;
    }

    @Override
    @android.annotation.SuppressLint({"UseCompatLoadingForDrawables"})
    public void init() {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.github.tvbox.osc.ui.activity.LoginActivity.init():void");
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i2, strArr, iArr);
        for (int i3 : iArr) {
            if (i3 == -1) {
                new a0(this).show();
                return;
            }
        }
        androidx.base.h3.a.a(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        e(false);
    }

    @Override
    public void run() {
        i.b(false);
        if (c.a == null) {
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(App.c.getAssets().open("epg_data.json"), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                }
                bufferedReader.close();
                inputStreamReader.close();
                if (!sb.toString().isEmpty()) {
                    JsonObject jsonObject = (JsonObject) new Gson().fromJson(sb.toString(), (Type) JsonObject.class);
                    c.a = jsonObject;
                    Iterator<JsonElement> it = jsonObject.get("epgs").getAsJsonArray().iterator();
                    while (it.hasNext()) {
                        JsonObject jsonObject2 = (JsonObject) it.next();
                        for (String str : jsonObject2.get("name").getAsString().trim().split(",")) {
                            c.b.put(str.toLowerCase(), jsonObject2);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        new androidx.base.m3.g();
        Objects.requireNonNull(d.a());
        File[] listFiles = new File(b.j).listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return;
        }
        for (File file : listFiles) {
            File[] listFiles2 = file.listFiles();
            if (listFiles2 != null && listFiles2.length != 0) {
                for (File file2 : listFiles2) {
                    String name = file2.getName();
                    int lastIndexOf = name.lastIndexOf(".");
                    if (lastIndexOf > 0) {
                        name = name.substring(0, lastIndexOf);
                    }
                    d.a.put(name.toLowerCase() + "_" + file.getName(), file2.toString());
                }
            }
        }
    }
}
