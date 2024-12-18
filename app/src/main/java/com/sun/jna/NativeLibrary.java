package com.sun.jna;

import androidx.base.a.a;
import androidx.base.a.b;
import com.google.android.material.shadow.ShadowDrawableWrapper;
import java.io.File;
import java.io.FilenameFilter;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
public class NativeLibrary {
    private static final Level DEBUG_LOAD_LEVEL;
    private static final int DEFAULT_OPEN_OPTIONS = -1;
    private static final Logger LOG = Logger.getLogger(NativeLibrary.class.getName());
    private static Method addSuppressedMethod;
    private static final Map<String, Reference<NativeLibrary>> libraries;
    private static final LinkedHashSet<String> librarySearchPath;
    private static final Map<String, List<String>> searchPaths;
    public final int callFlags;
    private String encoding;
    private final Map<String, Function> functions;
    private long handle;
    private final String libraryName;
    private final String libraryPath;
    public final Map<String, ?> options;

    static {
        String sb;
        DEBUG_LOAD_LEVEL = Native.DEBUG_LOAD ? Level.INFO : Level.FINE;
        libraries = new HashMap();
        searchPaths = Collections.synchronizedMap(new HashMap());
        librarySearchPath = new LinkedHashSet<>();
        if (Native.POINTER_SIZE == 0) {
            throw new Error("Native library not initialized");
        }
        addSuppressedMethod = null;
        try {
            addSuppressedMethod = Throwable.class.getMethod("addSuppressed", Throwable.class);
        } catch (NoSuchMethodException unused) {
        } catch (SecurityException e) {
            Logger.getLogger(NativeLibrary.class.getName()).log(Level.SEVERE, "Failed to initialize 'addSuppressed' method", (Throwable) e);
        }
        String webStartLibraryPath = Native.getWebStartLibraryPath("jnidispatch");
        if (webStartLibraryPath != null) {
            librarySearchPath.add(webStartLibraryPath);
        }
        if (System.getProperty("jna.platform.library.path") == null && !Platform.isWindows()) {
            if (Platform.isLinux() || Platform.isSolaris() || Platform.isFreeBSD() || Platform.iskFreeBSD()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Platform.isSolaris() ? "/" : "");
                sb2.append(Native.POINTER_SIZE * 8);
                sb = sb2.toString();
            } else {
                sb = "";
            }
            String[] strArr = {b.b("/usr/lib", sb), b.b("/lib", sb), "/usr/lib", "/lib"};
            if (Platform.isLinux() || Platform.iskFreeBSD() || Platform.isGNU()) {
                String multiArchPath = getMultiArchPath();
                strArr = new String[]{b.b("/usr/lib/", multiArchPath), b.b("/lib/", multiArchPath), b.b("/usr/lib", sb), b.b("/lib", sb), "/usr/lib", "/lib"};
            }
            if (Platform.isLinux()) {
                ArrayList<String> linuxLdPaths = getLinuxLdPaths();
                for (int length = strArr.length - 1; length >= 0; length--) {
                    int indexOf = linuxLdPaths.indexOf(strArr[length]);
                    if (indexOf != -1) {
                        linuxLdPaths.remove(indexOf);
                    }
                    linuxLdPaths.add(0, strArr[length]);
                }
                strArr = (String[]) linuxLdPaths.toArray(new String[0]);
            }
            String str = "";
            String str2 = str;
            for (int i = 0; i < strArr.length; i++) {
                File file = new File(strArr[i]);
                if (file.exists() && file.isDirectory()) {
                    StringBuilder e2 = b.e(str, str2);
                    e2.append(strArr[i]);
                    str = e2.toString();
                    str2 = File.pathSeparator;
                }
            }
            if (!"".equals(str)) {
                System.setProperty("jna.platform.library.path", str);
            }
        }
        librarySearchPath.addAll(initPaths("jna.platform.library.path"));
    }

    private NativeLibrary(String str, String str2, long j, Map<String, ?> map) {
        HashMap hashMap = new HashMap();
        this.functions = hashMap;
        String libraryName = getLibraryName(str);
        this.libraryName = libraryName;
        this.libraryPath = str2;
        this.handle = j;
        Object obj = map.get(Library.OPTION_CALLING_CONVENTION);
        int intValue = obj instanceof Number ? ((Number) obj).intValue() : 0;
        this.callFlags = intValue;
        this.options = map;
        String str3 = (String) map.get(Library.OPTION_STRING_ENCODING);
        this.encoding = str3;
        if (str3 == null) {
            this.encoding = Native.getDefaultStringEncoding();
        }
        if (Platform.isWindows() && "kernel32".equals(libraryName.toLowerCase())) {
            synchronized (hashMap) {
                hashMap.put(functionKey("GetLastError", intValue, this.encoding), new Function(this, "GetLastError", 63, this.encoding) {
                    @Override
                    public Object invoke(Method method, Class<?>[] clsArr, Class<?> cls, Object[] objArr, Map<String, ?> map2) {
                        return Integer.valueOf(Native.getLastError());
                    }

                    @Override
                    public Object invoke(Object[] objArr, Class<?> cls, boolean z, int i) {
                        return Integer.valueOf(Native.getLastError());
                    }
                });
            }
        }
    }

    public static final void addSearchPath(String str, String str2) {
        Map<String, List<String>> map = searchPaths;
        synchronized (map) {
            List<String> list = map.get(str);
            if (list == null) {
                list = Collections.synchronizedList(new ArrayList());
                map.put(str, list);
            }
            list.add(str2);
        }
    }

    private static void addSuppressedReflected(Throwable th, Throwable th2) {
        Method method = addSuppressedMethod;
        if (method == null) {
            return;
        }
        try {
            method.invoke(th, th2);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to call addSuppressedMethod", e);
        } catch (IllegalArgumentException e2) {
            throw new RuntimeException("Failed to call addSuppressedMethod", e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException("Failed to call addSuppressedMethod", e3);
        }
    }

    public static void disposeAll() {
        LinkedHashSet<Reference> linkedHashSet;
        Map<String, Reference<NativeLibrary>> map = libraries;
        synchronized (map) {
            linkedHashSet = new LinkedHashSet(map.values());
        }
        for (Reference reference : linkedHashSet) {
            NativeLibrary nativeLibrary = (NativeLibrary) reference.get();
            if (nativeLibrary != null) {
                nativeLibrary.dispose();
            }
        }
    }

    private static String findLibraryPath(String str, Collection<String> collection) {
        if (new File(str).isAbsolute()) {
            return str;
        }
        String mapSharedLibraryName = mapSharedLibraryName(str);
        for (String str2 : collection) {
            File file = new File(str2, mapSharedLibraryName);
            if (file.exists()) {
                return file.getAbsolutePath();
            }
            if (Platform.isMac() && mapSharedLibraryName.endsWith(".dylib")) {
                File file2 = new File(str2, mapSharedLibraryName.substring(0, mapSharedLibraryName.lastIndexOf(".dylib")) + ".jnilib");
                if (file2.exists()) {
                    return file2.getAbsolutePath();
                }
            }
        }
        return mapSharedLibraryName;
    }

    private static String functionKey(String str, int i, String str2) {
        return str + "|" + i + "|" + str2;
    }

    public static final NativeLibrary getInstance(String str) {
        return getInstance(str, Collections.emptyMap());
    }

    public static final NativeLibrary getInstance(String str, ClassLoader classLoader) {
        return getInstance(str, Collections.singletonMap(Library.OPTION_CLASSLOADER, classLoader));
    }

    public static final NativeLibrary getInstance(String str, Map<String, ?> map) {
        NativeLibrary nativeLibrary;
        File file;
        HashMap hashMap = new HashMap(map);
        if (hashMap.get(Library.OPTION_CALLING_CONVENTION) == null) {
            hashMap.put(Library.OPTION_CALLING_CONVENTION, 0);
        }
        if ((Platform.isLinux() || Platform.isFreeBSD() || Platform.isAIX()) && Platform.C_LIBRARY_NAME.equals(str)) {
            str = null;
        }
        Map<String, Reference<NativeLibrary>> map2 = libraries;
        synchronized (map2) {
            Reference<NativeLibrary> reference = map2.get(str + hashMap);
            nativeLibrary = reference != null ? reference.get() : null;
            if (nativeLibrary == null) {
                nativeLibrary = str == null ? new NativeLibrary("<process>", null, Native.open(null, openFlags(hashMap)), hashMap) : loadLibrary(str, hashMap);
                WeakReference weakReference = new WeakReference(nativeLibrary);
                map2.put(nativeLibrary.getName() + hashMap, weakReference);
                if (nativeLibrary.getFile() != null) {
                    map2.put(file.getAbsolutePath() + hashMap, weakReference);
                    map2.put(file.getName() + hashMap, weakReference);
                }
            }
        }
        return nativeLibrary;
    }

    private String getLibraryName(String str) {
        String mapSharedLibraryName = mapSharedLibraryName("---");
        int indexOf = mapSharedLibraryName.indexOf("---");
        if (indexOf > 0 && str.startsWith(mapSharedLibraryName.substring(0, indexOf))) {
            str = str.substring(indexOf);
        }
        int indexOf2 = str.indexOf(mapSharedLibraryName.substring(indexOf + 3));
        return indexOf2 != -1 ? str.substring(0, indexOf2) : str;
    }

    private static java.util.ArrayList<java.lang.String> getLinuxLdPaths() {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return null;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.sun.jna.NativeLibrary.getLinuxLdPaths():java.util.ArrayList");
    }

    private static String getMultiArchPath() {
        String str = Platform.ARCH;
        String str2 = Platform.iskFreeBSD() ? "-kfreebsd" : Platform.isGNU() ? "" : "-linux";
        String str3 = "-gnu";
        if (Platform.isIntel()) {
            str = Platform.is64Bit() ? "x86_64" : "i386";
        } else if (Platform.isPPC()) {
            str = Platform.is64Bit() ? "powerpc64" : "powerpc";
        } else if (Platform.isARM()) {
            str = "arm";
            str3 = "-gnueabi";
        } else if (str.equals("mips64el")) {
            str3 = "-gnuabi64";
        }
        return a.j(str, str2, str3);
    }

    public static final synchronized NativeLibrary getProcess() {
        NativeLibrary nativeLibrary;
        synchronized (NativeLibrary.class) {
            nativeLibrary = getInstance(null);
        }
        return nativeLibrary;
    }

    public static final synchronized NativeLibrary getProcess(Map<String, ?> map) {
        NativeLibrary nativeLibrary;
        synchronized (NativeLibrary.class) {
            nativeLibrary = getInstance((String) null, map);
        }
        return nativeLibrary;
    }

    private static List<String> initPaths(String str) {
        String property = System.getProperty(str, "");
        if ("".equals(property)) {
            return Collections.emptyList();
        }
        StringTokenizer stringTokenizer = new StringTokenizer(property, File.pathSeparator);
        ArrayList arrayList = new ArrayList();
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            if (!"".equals(nextToken)) {
                arrayList.add(nextToken);
            }
        }
        return arrayList;
    }

    public static boolean isVersionedName(String str) {
        int lastIndexOf;
        int i;
        if (!str.startsWith("lib") || (lastIndexOf = str.lastIndexOf(".so.")) == -1 || (i = lastIndexOf + 4) >= str.length()) {
            return false;
        }
        for (i = lastIndexOf + 4; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (!Character.isDigit(charAt) && charAt != '.') {
                return false;
            }
        }
        return true;
    }

    private static com.sun.jna.NativeLibrary loadLibrary(java.lang.String r16, java.util.Map<java.lang.String, ?> r17) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return null;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.sun.jna.NativeLibrary.loadLibrary(java.lang.String, java.util.Map):com.sun.jna.NativeLibrary");
    }

    public static String mapSharedLibraryName(String str) {
        if (Platform.isMac()) {
            if (str.startsWith("lib") && (str.endsWith(".dylib") || str.endsWith(".jnilib"))) {
                return str;
            }
            String mapLibraryName = System.mapLibraryName(str);
            if (mapLibraryName.endsWith(".jnilib")) {
                return mapLibraryName.substring(0, mapLibraryName.lastIndexOf(".jnilib")) + ".dylib";
            }
            return mapLibraryName;
        }
        if (Platform.isLinux() || Platform.isFreeBSD()) {
            if (isVersionedName(str) || str.endsWith(".so")) {
                return str;
            }
        } else if (Platform.isAIX()) {
            if (str.startsWith("lib")) {
                return str;
            }
        } else if (Platform.isWindows() && (str.endsWith(".drv") || str.endsWith(".dll") || str.endsWith(".ocx"))) {
            return str;
        }
        return System.mapLibraryName(str);
    }

    public static String[] matchFramework(String str) {
        String absolutePath;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        File file = new File(str);
        if (file.isAbsolute()) {
            if (!str.contains(".framework")) {
                File parentFile = file.getParentFile();
                File file2 = new File(new File(parentFile, file.getName() + ".framework"), file.getName());
                if (file2.exists()) {
                    return new String[]{file2.getAbsolutePath()};
                }
                absolutePath = file2.getAbsolutePath();
            } else if (file.exists()) {
                return new String[]{file.getAbsolutePath()};
            } else {
                absolutePath = file.getAbsolutePath();
            }
            linkedHashSet.add(absolutePath);
        } else {
            String[] strArr = {System.getProperty("user.home"), "", "/System"};
            if (!str.contains(".framework")) {
                str = a.j(str, ".framework/", str);
            }
            for (int i = 0; i < 3; i++) {
                File file3 = new File(a.j(strArr[i], "/Library/Frameworks/", str));
                if (file3.exists()) {
                    return new String[]{file3.getAbsolutePath()};
                }
                linkedHashSet.add(file3.getAbsolutePath());
            }
        }
        return (String[]) linkedHashSet.toArray(new String[0]);
    }

    public static String matchLibrary(final String str, Collection<String> collection) {
        File file = new File(str);
        if (file.isAbsolute()) {
            collection = Arrays.asList(file.getParent());
        }
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(java.io.File r4, java.lang.String r5) {
                
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return true;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.sun.jna.NativeLibrary.2.accept(java.io.File, java.lang.String):boolean");
            }
        };
        LinkedList<File> linkedList = new LinkedList();
        for (String str2 : collection) {
            File[] listFiles = new File(str2).listFiles(filenameFilter);
            if (listFiles != null && listFiles.length > 0) {
                linkedList.addAll(Arrays.asList(listFiles));
            }
        }
        double d = -1.0d;
        String str3 = null;
        for (File file2 : linkedList) {
            String absolutePath = file2.getAbsolutePath();
            double parseVersion = parseVersion(absolutePath.substring(absolutePath.lastIndexOf(".so.") + 4));
            if (parseVersion > d) {
                str3 = absolutePath;
                d = parseVersion;
            }
        }
        return str3;
    }

    private static int openFlags(Map<String, ?> map) {
        Object obj = map.get(Library.OPTION_OPEN_FLAGS);
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return -1;
    }

    public static double parseVersion(String str) {
        String str2;
        int indexOf = str.indexOf(".");
        double d = 1.0d;
        double d2 = 0.0d;
        while (str != null) {
            if (indexOf != -1) {
                String substring = str.substring(0, indexOf);
                String substring2 = str.substring(indexOf + 1);
                indexOf = substring2.indexOf(".");
                str2 = substring2;
                str = substring;
            } else {
                str2 = null;
            }
            try {
                double parseInt = Integer.parseInt(str);
                Double.isNaN(parseInt);
                d2 += parseInt / d;
                d *= 100.0d;
                str = str2;
            } catch (NumberFormatException unused) {
                return ShadowDrawableWrapper.COS_45;
            }
        }
        return d2;
    }

    public void dispose() {
        HashSet hashSet = new HashSet();
        Map<String, Reference<NativeLibrary>> map = libraries;
        synchronized (map) {
            for (Map.Entry<String, Reference<NativeLibrary>> entry : map.entrySet()) {
                if (entry.getValue().get() == this) {
                    hashSet.add(entry.getKey());
                }
            }
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                libraries.remove((String) it.next());
            }
        }
        synchronized (this) {
            long j = this.handle;
            if (j != 0) {
                Native.close(j);
                this.handle = 0L;
            }
        }
    }

    public void finalize() {
        dispose();
    }

    public File getFile() {
        if (this.libraryPath == null) {
            return null;
        }
        return new File(this.libraryPath);
    }

    public Function getFunction(String str) {
        return getFunction(str, this.callFlags);
    }

    public Function getFunction(String str, int i) {
        return getFunction(str, i, this.encoding);
    }

    public Function getFunction(String str, int i, String str2) {
        Function function;
        Objects.requireNonNull(str, "Function name may not be null");
        synchronized (this.functions) {
            String functionKey = functionKey(str, i, str2);
            function = this.functions.get(functionKey);
            if (function == null) {
                function = new Function(this, str, i, str2);
                this.functions.put(functionKey, function);
            }
        }
        return function;
    }

    public Function getFunction(String str, Method method) {
        FunctionMapper functionMapper = (FunctionMapper) this.options.get(Library.OPTION_FUNCTION_MAPPER);
        if (functionMapper != null) {
            str = functionMapper.getFunctionName(this, method);
        }
        String property = System.getProperty("jna.profiler.prefix", "$YJP$");
        if (str.startsWith(property)) {
            str = str.substring(property.length());
        }
        int i = this.callFlags;
        for (Class<?> cls : method.getExceptionTypes()) {
            if (LastErrorException.class.isAssignableFrom(cls)) {
                i |= 64;
            }
        }
        return getFunction(str, i);
    }

    public Pointer getGlobalVariableAddress(String str) {
        try {
            return new Pointer(getSymbolAddress(str));
        } catch (UnsatisfiedLinkError e) {
            StringBuilder f = b.f("Error looking up '", str, "': ");
            f.append(e.getMessage());
            throw new UnsatisfiedLinkError(f.toString());
        }
    }

    public String getName() {
        return this.libraryName;
    }

    public Map<String, ?> getOptions() {
        return this.options;
    }

    public long getSymbolAddress(String str) {
        long j = this.handle;
        if (j != 0) {
            return Native.findSymbol(j, str);
        }
        throw new UnsatisfiedLinkError("Library has been unloaded");
    }

    public String toString() {
        StringBuilder d = b.d("Native Library <");
        d.append(this.libraryPath);
        d.append("@");
        return a.l(d, this.handle, ">");
    }
}
