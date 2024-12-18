package com.eclipsesource.v8;

import androidx.base.a.b;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
public class PlatformDetector {

    public static class Arch {
        public static String getName() {
            String property = System.getProperty("os.arch");
            String normalizeArch = PlatformDetector.normalizeArch(property);
            if (normalizeArch.equals("unknown")) {
                throw new UnsatisfiedLinkError(b.b("Unsupported arch: ", property));
            }
            return normalizeArch;
        }
    }

    public static class OS {
        public static String getLibFileExtension() {
            if (isWindows()) {
                return "dll";
            }
            if (isMac()) {
                return "dylib";
            }
            if (isLinux() || isAndroid() || isNativeClient()) {
                return "so";
            }
            StringBuilder d = b.d("Unsupported platform library-extension for: ");
            d.append(getName());
            throw new UnsatisfiedLinkError(d.toString());
        }

        public static String getName() {
            String property = System.getProperty("os.name");
            String normalizeOs = PlatformDetector.normalizeOs(property);
            String property2 = System.getProperty("java.specification.vendor");
            if (PlatformDetector.normalize(property2).contains(Platform.ANDROID) || normalizeOs.contains(Platform.ANDROID)) {
                return Platform.ANDROID;
            }
            if (normalizeOs.equals("unknown")) {
                throw new UnsatisfiedLinkError("Unsupported platform/vendor: " + property + " / " + property2);
            }
            return normalizeOs;
        }

        public static boolean isAndroid() {
            return getName().equals(Platform.ANDROID);
        }

        public static boolean isLinux() {
            return getName().equals(Platform.LINUX);
        }

        public static boolean isMac() {
            return getName().equals(Platform.MACOSX);
        }

        public static boolean isNativeClient() {
            return getName().equals(Platform.NATIVE_CLIENT);
        }

        public static boolean isWindows() {
            return getName().equals(Platform.WINDOWS);
        }
    }

    public static class Vendor {
        private static final String LINUX_ID_PREFIX = "ID=";
        private static final String[] LINUX_OS_RELEASE_FILES = {"/etc/os-release", "/usr/lib/os-release"};
        private static final String REDHAT_RELEASE_FILE = "/etc/redhat-release";

        private static void closeQuietly(Closeable closeable) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException unused) {
                }
            }
        }

        private static String getLinuxOsReleaseId() {
            for (String str : LINUX_OS_RELEASE_FILES) {
                File file = new File(str);
                if (file.exists()) {
                    return parseLinuxOsReleaseFile(file);
                }
            }
            File file2 = new File(REDHAT_RELEASE_FILE);
            if (file2.exists()) {
                return parseLinuxRedhatReleaseFile(file2);
            }
            StringBuilder d = b.d("Unsupported linux vendor: ");
            d.append(getName());
            throw new UnsatisfiedLinkError(d.toString());
        }

        public static String getName() {
            if (OS.isWindows()) {
                return "microsoft";
            }
            if (OS.isMac()) {
                return "apple";
            }
            if (OS.isLinux()) {
                return getLinuxOsReleaseId();
            }
            if (OS.isAndroid()) {
                return "google";
            }
            StringBuilder d = b.d("Unsupported vendor: ");
            d.append(getName());
            throw new UnsatisfiedLinkError(d.toString());
        }

        private static java.lang.String parseLinuxOsReleaseFile(java.io.File r4) {
            
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return null;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.eclipsesource.v8.PlatformDetector.Vendor.parseLinuxOsReleaseFile(java.io.File):java.lang.String");
        }

        private static String parseLinuxRedhatReleaseFile(File file) {
            BufferedReader bufferedReader;
            String str = "centos";
            BufferedReader bufferedReader2 = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        String lowerCase = readLine.toLowerCase(Locale.US);
                        if (!lowerCase.contains("centos")) {
                            if (lowerCase.contains("fedora")) {
                                str = "fedora";
                            } else if (!lowerCase.contains("red hat enterprise linux")) {
                                closeQuietly(bufferedReader);
                                return null;
                            } else {
                                str = "rhel";
                            }
                        }
                        closeQuietly(bufferedReader);
                        return str;
                    }
                } catch (IOException unused) {
                } catch (Throwable th) {
                    th = th;
                    bufferedReader2 = bufferedReader;
                    closeQuietly(bufferedReader2);
                    throw th;
                }
            } catch (IOException unused2) {
                bufferedReader = null;
            } catch (Throwable th2) {
                th = th2;
            }
            closeQuietly(bufferedReader);
            return null;
        }
    }

    public static String normalize(String str) {
        return str == null ? "" : str.toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
    }

    public static String normalizeArch(String str) {
        String normalize = normalize(str);
        return normalize.matches("^(x8664|amd64|ia32e|em64t|x64)$") ? "x86_64" : normalize.matches("^(x8632|x86|i[3-6]86|ia32|x32)$") ? "x86_32" : normalize.matches("^(ia64|itanium64)$") ? "itanium_64" : normalize.matches("^(sparc|sparc32)$") ? "sparc_32" : normalize.matches("^(sparcv9|sparc64)$") ? "sparc_64" : (normalize.matches("^(arm|arm32)$") || normalize.startsWith("armv7")) ? "arm_32" : ("aarch64".equals(normalize) || normalize.startsWith("armv8")) ? "aarch_64" : normalize.matches("^(ppc|ppc32)$") ? "ppc_32" : "ppc64".equals(normalize) ? "ppc_64" : "ppc64le".equals(normalize) ? "ppcle_64" : "s390".equals(normalize) ? "s390_32" : "s390x".equals(normalize) ? "s390_64" : "unknown";
    }

    public static String normalizeOs(String str) {
        String normalize = normalize(str);
        return normalize.startsWith("aix") ? "aix" : normalize.startsWith("hpux") ? "hpux" : (!normalize.startsWith("os400") || (normalize.length() > 5 && Character.isDigit(normalize.charAt(5)))) ? normalize.startsWith(Platform.ANDROID) ? Platform.ANDROID : normalize.startsWith(Platform.LINUX) ? Platform.LINUX : normalize.startsWith(Platform.NATIVE_CLIENT) ? Platform.NATIVE_CLIENT : (normalize.startsWith(Platform.MACOSX) || normalize.startsWith("osx")) ? Platform.MACOSX : normalize.startsWith("freebsd") ? "freebsd" : normalize.startsWith("openbsd") ? "openbsd" : normalize.startsWith("netbsd") ? "netbsd" : (normalize.startsWith("solaris") || normalize.startsWith("sunos")) ? "sunos" : normalize.startsWith(Platform.WINDOWS) ? Platform.WINDOWS : "unknown" : "os400";
    }

    public static String normalizeOsReleaseValue(String str) {
        return str.trim().replace("\"", "");
    }
}
