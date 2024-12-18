package com.sun.jna;

import androidx.base.a.a;
import androidx.base.a.b;
import androidx.media3.extractor.text.CueDecoder;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
public final class Platform {
    public static final int AIX = 7;
    public static final int ANDROID = 8;
    public static final String ARCH;
    public static final String C_LIBRARY_NAME;
    public static final int FREEBSD = 4;
    public static final int GNU = 9;
    public static final boolean HAS_AWT;
    public static final boolean HAS_BUFFERS;
    public static final boolean HAS_DLL_CALLBACKS;
    public static final boolean HAS_JAWT;
    public static final int KFREEBSD = 10;
    public static final int LINUX = 1;
    public static final int MAC = 0;
    public static final String MATH_LIBRARY_NAME;
    public static final int NETBSD = 11;
    public static final int OPENBSD = 5;
    public static final String RESOURCE_PREFIX;
    public static final boolean RO_FIELDS;
    public static final int SOLARIS = 3;
    public static final int UNSPECIFIED = -1;
    public static final int WINDOWS = 2;
    public static final int WINDOWSCE = 6;
    private static final int osType;

    static {
        boolean z;
        String property = System.getProperty("os.name");
        boolean z2 = false;
        if (property.startsWith("Linux")) {
            if ("dalvik".equals(System.getProperty("java.vm.name").toLowerCase())) {
                osType = 8;
                System.setProperty("jna.nounpack", "true");
            } else {
                osType = 1;
            }
        } else if (property.startsWith("AIX")) {
            osType = 7;
        } else if (property.startsWith("Mac") || property.startsWith("Darwin")) {
            osType = 0;
        } else if (property.startsWith("Windows CE")) {
            osType = 6;
        } else if (property.startsWith("Windows")) {
            osType = 2;
        } else {
            osType = (property.startsWith("Solaris") || property.startsWith("SunOS")) ? 3 : property.startsWith("FreeBSD") ? 4 : property.startsWith("OpenBSD") ? 5 : property.equalsIgnoreCase("gnu") ? 9 : property.equalsIgnoreCase("gnu/kfreebsd") ? 10 : property.equalsIgnoreCase("netbsd") ? 11 : -1;
        }
        try {
            Class.forName("java.nio.Buffer");
            z = true;
        } catch (ClassNotFoundException unused) {
            z = false;
        }
        int i = osType;
        boolean z3 = (i == 6 || i == 8 || i == 7) ? false : true;
        HAS_AWT = z3;
        HAS_JAWT = z3 && i != 0;
        HAS_BUFFERS = z;
        RO_FIELDS = i != 6;
        String str = "coredll";
        C_LIBRARY_NAME = i == 2 ? "msvcrt" : i == 6 ? "coredll" : CueDecoder.BUNDLE_FIELD_CUES;
        if (i == 2) {
            str = "msvcrt";
        } else if (i != 6) {
            str = "m";
        }
        MATH_LIBRARY_NAME = str;
        String canonicalArchitecture = getCanonicalArchitecture(System.getProperty("os.arch"), i);
        ARCH = canonicalArchitecture;
        if (i == 2 && !canonicalArchitecture.startsWith("aarch")) {
            z2 = true;
        }
        HAS_DLL_CALLBACKS = z2;
        RESOURCE_PREFIX = getNativeLibraryResourcePrefix();
    }

    private Platform() {
    }

    public static String getCanonicalArchitecture(String str, int i) {
        String trim = str.toLowerCase().trim();
        if ("powerpc".equals(trim)) {
            trim = "ppc";
        } else if ("powerpc64".equals(trim)) {
            trim = "ppc64";
        } else if ("i386".equals(trim) || "i686".equals(trim)) {
            trim = "x86";
        } else if ("x86_64".equals(trim) || "amd64".equals(trim)) {
            trim = "x86-64";
        } else if ("zarch_64".equals(trim)) {
            trim = "s390x";
        }
        if ("ppc64".equals(trim) && "little".equals(System.getProperty("sun.cpu.endian"))) {
            trim = "ppc64le";
        }
        return ("arm".equals(trim) && i == 1 && isSoftFloat()) ? "armel" : trim;
    }

    public static String getNativeLibraryResourcePrefix() {
        String property = System.getProperty("jna.prefix");
        return property != null ? property : getNativeLibraryResourcePrefix(getOSType(), System.getProperty("os.arch"), System.getProperty("os.name"));
    }

    public static String getNativeLibraryResourcePrefix(int i, String str, String str2) {
        StringBuilder sb;
        String str3;
        String canonicalArchitecture = getCanonicalArchitecture(str, i);
        switch (i) {
            case 0:
                sb = new StringBuilder();
                str3 = "darwin-";
                break;
            case 1:
                sb = new StringBuilder();
                str3 = "linux-";
                break;
            case 2:
                sb = new StringBuilder();
                str3 = "win32-";
                break;
            case 3:
                sb = new StringBuilder();
                str3 = "sunos-";
                break;
            case 4:
                sb = new StringBuilder();
                str3 = "freebsd-";
                break;
            case 5:
                sb = new StringBuilder();
                str3 = "openbsd-";
                break;
            case 6:
                sb = new StringBuilder();
                str3 = "w32ce-";
                break;
            case 7:
            case 9:
            default:
                String lowerCase = str2.toLowerCase();
                int indexOf = lowerCase.indexOf(" ");
                if (indexOf != -1) {
                    lowerCase = lowerCase.substring(0, indexOf);
                }
                return a.j(lowerCase, "-", canonicalArchitecture);
            case 8:
                if (canonicalArchitecture.startsWith("arm")) {
                    canonicalArchitecture = "arm";
                }
                sb = new StringBuilder();
                str3 = "android-";
                break;
            case 10:
                sb = new StringBuilder();
                str3 = "kfreebsd-";
                break;
            case 11:
                sb = new StringBuilder();
                str3 = "netbsd-";
                break;
        }
        return b.c(sb, str3, canonicalArchitecture);
    }

    public static final int getOSType() {
        return osType;
    }

    public static final boolean hasRuntimeExec() {
        return (isWindowsCE() && "J9".equals(System.getProperty("java.vm.name"))) ? false : true;
    }

    public static final boolean is64Bit() {
        String property = System.getProperty("sun.arch.data.model", System.getProperty("com.ibm.vm.bitmode"));
        if (property != null) {
            return "64".equals(property);
        }
        String str = ARCH;
        return "x86-64".equals(str) || "ia64".equals(str) || "ppc64".equals(str) || "ppc64le".equals(str) || "sparcv9".equals(str) || "mips64".equals(str) || "mips64el".equals(str) || "amd64".equals(str) || "aarch64".equals(str) || Native.POINTER_SIZE == 8;
    }

    public static final boolean isAIX() {
        return osType == 7;
    }

    public static final boolean isARM() {
        String str = ARCH;
        return str.startsWith("arm") || str.startsWith("aarch");
    }

    public static final boolean isAndroid() {
        return osType == 8;
    }

    public static final boolean isFreeBSD() {
        return osType == 4;
    }

    public static final boolean isGNU() {
        return osType == 9;
    }

    public static final boolean isIntel() {
        return ARCH.startsWith("x86");
    }

    public static final boolean isLinux() {
        return osType == 1;
    }

    public static final boolean isMIPS() {
        String str = ARCH;
        return str.equals("mips") || str.equals("mips64") || str.equals("mipsel") || str.equals("mips64el");
    }

    public static final boolean isMac() {
        return osType == 0;
    }

    public static final boolean isNetBSD() {
        return osType == 11;
    }

    public static final boolean isOpenBSD() {
        return osType == 5;
    }

    public static final boolean isPPC() {
        return ARCH.startsWith("ppc");
    }

    public static final boolean isSPARC() {
        return ARCH.startsWith("sparc");
    }

    public static boolean isSoftFloat() {
        Logger logger;
        Level level;
        String str;
        try {
            File file = new File("/proc/self/exe");
            if (file.exists()) {
                return !ELFAnalyser.analyse(file.getCanonicalPath()).isArmHardFloat();
            }
            return false;
        } catch (IOException e) {
            e = e;
            logger = Logger.getLogger(Platform.class.getName());
            level = Level.INFO;
            str = "Failed to read '/proc/self/exe' or the target binary.";
            logger.log(level, str, e);
            return false;
        } catch (SecurityException e2) {
            e = e2;
            logger = Logger.getLogger(Platform.class.getName());
            level = Level.INFO;
            str = "SecurityException while analysing '/proc/self/exe' or the target binary.";
            logger.log(level, str, e);
            return false;
        }
    }

    public static final boolean isSolaris() {
        return osType == 3;
    }

    public static final boolean isWindows() {
        int i = osType;
        return i == 2 || i == 6;
    }

    public static final boolean isWindowsCE() {
        return osType == 6;
    }

    public static final boolean isX11() {
        return (isWindows() || isMac()) ? false : true;
    }

    public static final boolean iskFreeBSD() {
        return osType == 10;
    }
}
