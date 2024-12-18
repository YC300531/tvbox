package com.forcetech.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.gsoft.mitv.MainActivity;
import java.util.Objects;
public class PxpUtil {
    public static int MTV = 9002;
    public static int P2P = 9906;
    public static int P3P = 9907;
    public static int P4P = 9908;
    public static int P5P = 9909;
    public static int P6P = 9910;
    public static int P7P = 9911;
    public static int P8P = 9912;
    public static int P9P = 9913;

    private static Class<?> clz(String str) {
        Objects.requireNonNull(str);
        char c = 65535;
        switch (str.hashCode()) {
            case 109294:
                if (str.equals("p2p")) {
                    c = 0;
                    break;
                }
                break;
            case 109325:
                if (str.equals("p3p")) {
                    c = 1;
                    break;
                }
                break;
            case 109356:
                if (str.equals("p4p")) {
                    c = 2;
                    break;
                }
                break;
            case 109387:
                if (str.equals("p5p")) {
                    c = 3;
                    break;
                }
                break;
            case 109418:
                if (str.equals("p6p")) {
                    c = 4;
                    break;
                }
                break;
            case 109449:
                if (str.equals("p7p")) {
                    c = 5;
                    break;
                }
                break;
            case 109480:
                if (str.equals("p8p")) {
                    c = 6;
                    break;
                }
                break;
            case 109511:
                if (str.equals("p9p")) {
                    c = 7;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return P2PService.class;
            case 1:
            case 7:
                return P3PService.class;
            case 2:
            case 5:
                return P4PService.class;
            case 3:
            case 6:
                return P5PService.class;
            case 4:
                return P6PService.class;
            default:
                return MainActivity.class;
        }
    }

    public static Intent intent(Context context, String str) {
        Intent intent = new Intent(context, clz(str));
        intent.putExtra("scheme", str);
        return intent;
    }

    public static int port(String str) {
        Objects.requireNonNull(str);
        char c = 65535;
        switch (str.hashCode()) {
            case 109294:
                if (str.equals("p2p")) {
                    c = 0;
                    break;
                }
                break;
            case 109325:
                if (str.equals("p3p")) {
                    c = 1;
                    break;
                }
                break;
            case 109356:
                if (str.equals("p4p")) {
                    c = 2;
                    break;
                }
                break;
            case 109387:
                if (str.equals("p5p")) {
                    c = 3;
                    break;
                }
                break;
            case 109418:
                if (str.equals("p6p")) {
                    c = 4;
                    break;
                }
                break;
            case 109449:
                if (str.equals("p7p")) {
                    c = 5;
                    break;
                }
                break;
            case 109480:
                if (str.equals("p8p")) {
                    c = 6;
                    break;
                }
                break;
            case 109511:
                if (str.equals("p9p")) {
                    c = 7;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return P2P;
            case 1:
            case 7:
                return P3P;
            case 2:
            case 5:
                return P4P;
            case 3:
            case 6:
                return P5P;
            case 4:
                return P6P;
            default:
                return MTV;
        }
    }

    public static String scheme(String str) {
        String scheme = Uri.parse(str).getScheme();
        if (scheme == null) {
            return "";
        }
        if (scheme.equals("P2p")) {
            scheme = "mitv";
        }
        return scheme.toLowerCase();
    }

    public static String trans(ComponentName componentName) {
        String className = componentName.getClassName();
        return className.substring(className.lastIndexOf(".") + 1).replace("Service", "").replace("MainActivity", "mitv").toLowerCase();
    }
}
