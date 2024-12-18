package com.sun.jna;

import androidx.base.a.b;
import com.sun.jna.Function;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class StringArray extends Memory implements Function.PostCallRead {
    private String encoding;
    private List<NativeString> natives;
    private Object[] original;

    public StringArray(WString[] wStringArr) {
        this(wStringArr, NativeString.WIDE_STRING);
    }

    private StringArray(Object[] objArr, String str) {
        super((objArr.length + 1) * Native.POINTER_SIZE);
        this.natives = new ArrayList();
        this.original = objArr;
        this.encoding = str;
        int i = 0;
        while (true) {
            Pointer pointer = null;
            if (i >= objArr.length) {
                setPointer(Native.POINTER_SIZE * objArr.length, null);
                return;
            }
            if (objArr[i] != null) {
                NativeString nativeString = new NativeString(objArr[i].toString(), str);
                this.natives.add(nativeString);
                pointer = nativeString.getPointer();
            }
            setPointer(Native.POINTER_SIZE * i, pointer);
            i++;
        }
    }

    public StringArray(String[] strArr) {
        this(strArr, false);
    }

    public StringArray(String[] strArr, String str) {
        this((Object[]) strArr, str);
    }

    public StringArray(String[] strArr, boolean z) {
        this((Object[]) strArr, z ? NativeString.WIDE_STRING : Native.getDefaultStringEncoding());
    }

    @Override
    public void read() {
        boolean z = this.original instanceof WString[];
        boolean equals = NativeString.WIDE_STRING.equals(this.encoding);
        for (int i = 0; i < this.original.length; i++) {
            Pointer pointer = getPointer(Native.POINTER_SIZE * i);
            String str = null;
            if (pointer != null) {
                str = equals ? pointer.getWideString(0L) : pointer.getString(0L, this.encoding);
                if (z) {
                    str = new WString(str);
                }
            }
            this.original[i] = str;
        }
    }

    @Override
    public String toString() {
        StringBuilder d = b.d(NativeString.WIDE_STRING.equals(this.encoding) ? "const wchar_t*[]" : "const char*[]");
        d.append(Arrays.asList(this.original));
        return d.toString();
    }
}
