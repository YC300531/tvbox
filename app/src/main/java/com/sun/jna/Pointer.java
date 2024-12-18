package com.sun.jna;

import androidx.base.a.b;
import androidx.exifinterface.media.ExifInterface;
import androidx.media3.exoplayer.rtsp.SessionDescription;
import com.google.android.material.shadow.ShadowDrawableWrapper;
import com.sun.jna.Structure;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
public class Pointer {
    public static final Pointer NULL = null;
    public long peer;

    public static class fun1 {
    }

    public static class Opaque extends Pointer {
        private final String MSG;

        private Opaque(long j) {
            super(j);
            this.MSG = "This pointer is opaque: " + this;
        }

        public Opaque(long j, 1 r3) {
            this(j);
        }

        @Override
        public void clear(long j) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public String dump(long j, int i) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public byte getByte(long j) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public ByteBuffer getByteBuffer(long j, long j2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public char getChar(long j) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public double getDouble(long j) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public float getFloat(long j) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public int getInt(long j) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public long getLong(long j) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public Pointer getPointer(long j) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public short getShort(long j) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public String getString(long j, String str) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public String getWideString(long j) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public long indexOf(long j, byte b) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long j, byte[] bArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long j, char[] cArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long j, double[] dArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long j, float[] fArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long j, int[] iArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long j, long[] jArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long j, Pointer[] pointerArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long j, short[] sArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setByte(long j, byte b) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setChar(long j, char c) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setDouble(long j, double d) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setFloat(long j, float f) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setInt(long j, int i) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setLong(long j, long j2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setMemory(long j, long j2, byte b) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setPointer(long j, Pointer pointer) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setShort(long j, short s) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setString(long j, String str, String str2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setWideString(long j, String str) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public Pointer share(long j, long j2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public String toString() {
            StringBuilder d = b.d("const@0x");
            d.append(Long.toHexString(this.peer));
            return d.toString();
        }

        @Override
        public void write(long j, byte[] bArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long j, char[] cArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long j, double[] dArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long j, float[] fArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long j, int[] iArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long j, long[] jArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long j, Pointer[] pointerArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long j, short[] sArr, int i, int i2) {
            throw new UnsupportedOperationException(this.MSG);
        }
    }

    public Pointer() {
    }

    public Pointer(long j) {
        this.peer = j;
    }

    public static final Pointer createConstant(int i) {
        return new Opaque(i & (-1), null);
    }

    public static final Pointer createConstant(long j) {
        return new Opaque(j, null);
    }

    public static long nativeValue(Pointer pointer) {
        if (pointer == null) {
            return 0L;
        }
        return pointer.peer;
    }

    public static void nativeValue(Pointer pointer, long j) {
        pointer.peer = j;
    }

    private void readArray(long j, Object obj, Class<?> cls) {
        int length = Array.getLength(obj);
        if (cls == Byte.TYPE) {
            read(j, (byte[]) obj, 0, length);
        } else if (cls == Short.TYPE) {
            read(j, (short[]) obj, 0, length);
        } else if (cls == Character.TYPE) {
            read(j, (char[]) obj, 0, length);
        } else if (cls == Integer.TYPE) {
            read(j, (int[]) obj, 0, length);
        } else if (cls == Long.TYPE) {
            read(j, (long[]) obj, 0, length);
        } else if (cls == Float.TYPE) {
            read(j, (float[]) obj, 0, length);
        } else if (cls == Double.TYPE) {
            read(j, (double[]) obj, 0, length);
        } else if (Pointer.class.isAssignableFrom(cls)) {
            read(j, (Pointer[]) obj, 0, length);
        } else {
            int i = 0;
            if (!Structure.class.isAssignableFrom(cls)) {
                if (!NativeMapped.class.isAssignableFrom(cls)) {
                    throw new IllegalArgumentException(androidx.base.r5.b.g("Reading array of ", cls, " from memory not supported"));
                }
                NativeMapped[] nativeMappedArr = (NativeMapped[]) obj;
                NativeMappedConverter nativeMappedConverter = NativeMappedConverter.getInstance(cls);
                int nativeSize = Native.getNativeSize(obj.getClass(), obj) / nativeMappedArr.length;
                while (i < nativeMappedArr.length) {
                    nativeMappedArr[i] = (NativeMapped) nativeMappedConverter.fromNative(getValue((nativeSize * i) + j, nativeMappedConverter.nativeType(), nativeMappedArr[i]), new FromNativeContext(cls));
                    i++;
                }
                return;
            }
            Structure[] structureArr = (Structure[]) obj;
            if (Structure.ByReference.class.isAssignableFrom(cls)) {
                Pointer[] pointerArray = getPointerArray(j, structureArr.length);
                while (i < structureArr.length) {
                    structureArr[i] = Structure.updateStructureByReference(cls, structureArr[i], pointerArray[i]);
                    i++;
                }
                return;
            }
            Structure structure = structureArr[0];
            if (structure == null) {
                structure = Structure.newInstance((Class<Structure>) cls, share(j));
                structure.conditionalAutoRead();
                structureArr[0] = structure;
            } else {
                structure.useMemory(this, (int) j, true);
                structure.read();
            }
            Structure[] array = structure.toArray(structureArr.length);
            for (int i2 = 1; i2 < structureArr.length; i2++) {
                if (structureArr[i2] == null) {
                    structureArr[i2] = array[i2];
                } else {
                    structureArr[i2].useMemory(this, (int) ((structureArr[i2].size() * i2) + j), true);
                    structureArr[i2].read();
                }
            }
        }
    }

    private void writeArray(long j, Object obj, Class<?> cls) {
        if (cls == Byte.TYPE) {
            byte[] bArr = (byte[]) obj;
            write(j, bArr, 0, bArr.length);
        } else if (cls == Short.TYPE) {
            short[] sArr = (short[]) obj;
            write(j, sArr, 0, sArr.length);
        } else if (cls == Character.TYPE) {
            char[] cArr = (char[]) obj;
            write(j, cArr, 0, cArr.length);
        } else if (cls == Integer.TYPE) {
            int[] iArr = (int[]) obj;
            write(j, iArr, 0, iArr.length);
        } else if (cls == Long.TYPE) {
            long[] jArr = (long[]) obj;
            write(j, jArr, 0, jArr.length);
        } else if (cls == Float.TYPE) {
            float[] fArr = (float[]) obj;
            write(j, fArr, 0, fArr.length);
        } else if (cls == Double.TYPE) {
            double[] dArr = (double[]) obj;
            write(j, dArr, 0, dArr.length);
        } else if (Pointer.class.isAssignableFrom(cls)) {
            Pointer[] pointerArr = (Pointer[]) obj;
            write(j, pointerArr, 0, pointerArr.length);
        } else {
            int i = 0;
            if (!Structure.class.isAssignableFrom(cls)) {
                if (!NativeMapped.class.isAssignableFrom(cls)) {
                    throw new IllegalArgumentException(androidx.base.r5.b.g("Writing array of ", cls, " to memory not supported"));
                }
                NativeMapped[] nativeMappedArr = (NativeMapped[]) obj;
                NativeMappedConverter nativeMappedConverter = NativeMappedConverter.getInstance(cls);
                Class<?> nativeType = nativeMappedConverter.nativeType();
                int nativeSize = Native.getNativeSize(obj.getClass(), obj) / nativeMappedArr.length;
                while (i < nativeMappedArr.length) {
                    setValue((i * nativeSize) + j, nativeMappedConverter.toNative(nativeMappedArr[i], new ToNativeContext()), nativeType);
                    i++;
                }
                return;
            }
            Structure[] structureArr = (Structure[]) obj;
            if (Structure.ByReference.class.isAssignableFrom(cls)) {
                int length = structureArr.length;
                Pointer[] pointerArr2 = new Pointer[length];
                while (i < structureArr.length) {
                    if (structureArr[i] == null) {
                        pointerArr2[i] = null;
                    } else {
                        pointerArr2[i] = structureArr[i].getPointer();
                        structureArr[i].write();
                    }
                    i++;
                }
                write(j, pointerArr2, 0, length);
                return;
            }
            Structure structure = structureArr[0];
            if (structure == null) {
                structure = Structure.newInstance((Class<Structure>) cls, share(j));
                structureArr[0] = structure;
            } else {
                structure.useMemory(this, (int) j, true);
            }
            structure.write();
            Structure[] array = structure.toArray(structureArr.length);
            for (int i2 = 1; i2 < structureArr.length; i2++) {
                if (structureArr[i2] == null) {
                    structureArr[i2] = array[i2];
                } else {
                    structureArr[i2].useMemory(this, (int) ((structureArr[i2].size() * i2) + j), true);
                }
                structureArr[i2].write();
            }
        }
    }

    public void clear(long j) {
        setMemory(0L, j, (byte) 0);
    }

    public String dump(long j, int i) {
        StringWriter stringWriter = new StringWriter(((i / 4) * 4) + (i * 2) + 13);
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println("memory dump");
        for (int i2 = 0; i2 < i; i2++) {
            byte b = getByte(i2 + j);
            int i3 = i2 % 4;
            if (i3 == 0) {
                printWriter.print("[");
            }
            if (b >= 0 && b < 16) {
                printWriter.print(SessionDescription.SUPPORTED_SDP_VERSION);
            }
            printWriter.print(Integer.toHexString(b & ExifInterface.MARKER));
            if (i3 == 3 && i2 < i - 1) {
                printWriter.println("]");
            }
        }
        if (stringWriter.getBuffer().charAt(stringWriter.getBuffer().length() - 2) != ']') {
            printWriter.println("]");
        }
        return stringWriter.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return obj != null && (obj instanceof Pointer) && ((Pointer) obj).peer == this.peer;
    }

    public byte getByte(long j) {
        return Native.getByte(this, this.peer, j);
    }

    public byte[] getByteArray(long j, int i) {
        byte[] bArr = new byte[i];
        read(j, bArr, 0, i);
        return bArr;
    }

    public ByteBuffer getByteBuffer(long j, long j2) {
        return Native.getDirectByteBuffer(this, this.peer, j, j2).order(ByteOrder.nativeOrder());
    }

    public char getChar(long j) {
        return Native.getChar(this, this.peer, j);
    }

    public char[] getCharArray(long j, int i) {
        char[] cArr = new char[i];
        read(j, cArr, 0, i);
        return cArr;
    }

    public double getDouble(long j) {
        return Native.getDouble(this, this.peer, j);
    }

    public double[] getDoubleArray(long j, int i) {
        double[] dArr = new double[i];
        read(j, dArr, 0, i);
        return dArr;
    }

    public float getFloat(long j) {
        return Native.getFloat(this, this.peer, j);
    }

    public float[] getFloatArray(long j, int i) {
        float[] fArr = new float[i];
        read(j, fArr, 0, i);
        return fArr;
    }

    public int getInt(long j) {
        return Native.getInt(this, this.peer, j);
    }

    public int[] getIntArray(long j, int i) {
        int[] iArr = new int[i];
        read(j, iArr, 0, i);
        return iArr;
    }

    public long getLong(long j) {
        return Native.getLong(this, this.peer, j);
    }

    public long[] getLongArray(long j, int i) {
        long[] jArr = new long[i];
        read(j, jArr, 0, i);
        return jArr;
    }

    public NativeLong getNativeLong(long j) {
        return new NativeLong(NativeLong.SIZE == 8 ? getLong(j) : getInt(j));
    }

    public Pointer getPointer(long j) {
        return Native.getPointer(this.peer + j);
    }

    public Pointer[] getPointerArray(long j) {
        ArrayList arrayList = new ArrayList();
        Pointer pointer = getPointer(j);
        int i = 0;
        while (pointer != null) {
            arrayList.add(pointer);
            i += Native.POINTER_SIZE;
            pointer = getPointer(i + j);
        }
        return (Pointer[]) arrayList.toArray(new Pointer[0]);
    }

    public Pointer[] getPointerArray(long j, int i) {
        Pointer[] pointerArr = new Pointer[i];
        read(j, pointerArr, 0, i);
        return pointerArr;
    }

    public short getShort(long j) {
        return Native.getShort(this, this.peer, j);
    }

    public short[] getShortArray(long j, int i) {
        short[] sArr = new short[i];
        read(j, sArr, 0, i);
        return sArr;
    }

    public String getString(long j) {
        return getString(j, Native.getDefaultStringEncoding());
    }

    public String getString(long j, String str) {
        return Native.getString(this, j, str);
    }

    public String[] getStringArray(long j) {
        return getStringArray(j, -1, Native.getDefaultStringEncoding());
    }

    public String[] getStringArray(long j, int i) {
        return getStringArray(j, i, Native.getDefaultStringEncoding());
    }

    public String[] getStringArray(long j, int i, String str) {
        ArrayList arrayList = new ArrayList();
        if (i == -1) {
            int i2 = 0;
            while (true) {
                Pointer pointer = getPointer(i2 + j);
                if (pointer == null) {
                    break;
                }
                arrayList.add(NativeString.WIDE_STRING.equals(str) ? pointer.getWideString(0L) : pointer.getString(0L, str));
                i2 += Native.POINTER_SIZE;
            }
        } else {
            Pointer pointer2 = getPointer(0 + j);
            int i3 = 0;
            int i4 = 0;
            while (true) {
                int i5 = i3 + 1;
                if (i3 >= i) {
                    break;
                }
                arrayList.add(pointer2 == null ? null : NativeString.WIDE_STRING.equals(str) ? pointer2.getWideString(0L) : pointer2.getString(0L, str));
                if (i5 < i) {
                    i4 += Native.POINTER_SIZE;
                    pointer2 = getPointer(i4 + j);
                }
                i3 = i5;
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public String[] getStringArray(long j, String str) {
        return getStringArray(j, -1, str);
    }

    public java.lang.Object getValue(long r5, java.lang.Class<?> r7, java.lang.Object r8) {
        
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return null;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
Method not decompiled: com.sun.jna.Pointer.getValue(long, java.lang.Class, java.lang.Object):java.lang.Object");
    }

    public String getWideString(long j) {
        return Native.getWideString(this, this.peer, j);
    }

    public String[] getWideStringArray(long j) {
        return getWideStringArray(j, -1);
    }

    public String[] getWideStringArray(long j, int i) {
        return getStringArray(j, i, NativeString.WIDE_STRING);
    }

    public int hashCode() {
        long j = this.peer;
        return (int) ((j >>> 32) + (j & (-1)));
    }

    public long indexOf(long j, byte b) {
        return Native.indexOf(this, this.peer, j, b);
    }

    public void read(long j, byte[] bArr, int i, int i2) {
        Native.read(this, this.peer, j, bArr, i, i2);
    }

    public void read(long j, char[] cArr, int i, int i2) {
        Native.read(this, this.peer, j, cArr, i, i2);
    }

    public void read(long j, double[] dArr, int i, int i2) {
        Native.read(this, this.peer, j, dArr, i, i2);
    }

    public void read(long j, float[] fArr, int i, int i2) {
        Native.read(this, this.peer, j, fArr, i, i2);
    }

    public void read(long j, int[] iArr, int i, int i2) {
        Native.read(this, this.peer, j, iArr, i, i2);
    }

    public void read(long j, long[] jArr, int i, int i2) {
        Native.read(this, this.peer, j, jArr, i, i2);
    }

    public void read(long j, Pointer[] pointerArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            Pointer pointer = getPointer((Native.POINTER_SIZE * i3) + j);
            int i4 = i3 + i;
            Pointer pointer2 = pointerArr[i4];
            if (pointer2 == null || pointer == null || pointer.peer != pointer2.peer) {
                pointerArr[i4] = pointer;
            }
        }
    }

    public void read(long j, short[] sArr, int i, int i2) {
        Native.read(this, this.peer, j, sArr, i, i2);
    }

    public void setByte(long j, byte b) {
        Native.setByte(this, this.peer, j, b);
    }

    public void setChar(long j, char c) {
        Native.setChar(this, this.peer, j, c);
    }

    public void setDouble(long j, double d) {
        Native.setDouble(this, this.peer, j, d);
    }

    public void setFloat(long j, float f) {
        Native.setFloat(this, this.peer, j, f);
    }

    public void setInt(long j, int i) {
        Native.setInt(this, this.peer, j, i);
    }

    public void setLong(long j, long j2) {
        Native.setLong(this, this.peer, j, j2);
    }

    public void setMemory(long j, long j2, byte b) {
        Native.setMemory(this, this.peer, j, j2, b);
    }

    public void setNativeLong(long j, NativeLong nativeLong) {
        if (NativeLong.SIZE == 8) {
            setLong(j, nativeLong.longValue());
        } else {
            setInt(j, nativeLong.intValue());
        }
    }

    public void setPointer(long j, Pointer pointer) {
        Native.setPointer(this, this.peer, j, pointer != null ? pointer.peer : 0L);
    }

    public void setShort(long j, short s) {
        Native.setShort(this, this.peer, j, s);
    }

    public void setString(long j, WString wString) {
        setWideString(j, wString == null ? null : wString.toString());
    }

    public void setString(long j, String str) {
        setString(j, str, Native.getDefaultStringEncoding());
    }

    public void setString(long j, String str, String str2) {
        byte[] bytes = Native.getBytes(str, str2);
        write(j, bytes, 0, bytes.length);
        setByte(j + bytes.length, (byte) 0);
    }

    public void setValue(long j, Object obj, Class<?> cls) {
        Pointer pointer;
        int i = 0;
        i = 0;
        if (cls == Boolean.TYPE || cls == Boolean.class) {
            if (Boolean.TRUE.equals(obj)) {
                i = -1;
            }
        } else if (cls == Byte.TYPE || cls == Byte.class) {
            setByte(j, obj != null ? ((Byte) obj).byteValue() : (byte) 0);
            return;
        } else if (cls == Short.TYPE || cls == Short.class) {
            setShort(j, obj != null ? ((Short) obj).shortValue() : (short) 0);
            return;
        } else if (cls == Character.TYPE || cls == Character.class) {
            setChar(j, obj != null ? ((Character) obj).charValue() : (char) 0);
            return;
        } else if (cls != Integer.TYPE && cls != Integer.class) {
            if (cls == Long.TYPE || cls == Long.class) {
                setLong(j, obj == null ? 0L : ((Long) obj).longValue());
                return;
            } else if (cls == Float.TYPE || cls == Float.class) {
                setFloat(j, obj == null ? 0.0f : ((Float) obj).floatValue());
                return;
            } else if (cls == Double.TYPE || cls == Double.class) {
                setDouble(j, obj == null ? ShadowDrawableWrapper.COS_45 : ((Double) obj).doubleValue());
                return;
            } else {
                if (cls == Pointer.class || cls == String.class || cls == WString.class) {
                    pointer = (Pointer) obj;
                } else {
                    if (Structure.class.isAssignableFrom(cls)) {
                        Structure structure = (Structure) obj;
                        if (!Structure.ByReference.class.isAssignableFrom(cls)) {
                            structure.useMemory(this, (int) j, true);
                            structure.write();
                            return;
                        }
                        setPointer(j, structure != null ? structure.getPointer() : null);
                        if (structure != null) {
                            structure.autoWrite();
                            return;
                        }
                        return;
                    } else if (!Callback.class.isAssignableFrom(cls)) {
                        if (Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(cls)) {
                            setPointer(j, obj != null ? Native.getDirectBufferPointer((Buffer) obj) : null);
                            return;
                        } else if (NativeMapped.class.isAssignableFrom(cls)) {
                            NativeMappedConverter nativeMappedConverter = NativeMappedConverter.getInstance(cls);
                            setValue(j, nativeMappedConverter.toNative(obj, new ToNativeContext()), nativeMappedConverter.nativeType());
                            return;
                        } else if (!cls.isArray()) {
                            throw new IllegalArgumentException(androidx.base.r5.b.g("Writing ", cls, " to memory is not supported"));
                        } else {
                            writeArray(j, obj, cls.getComponentType());
                            return;
                        }
                    } else {
                        pointer = CallbackReference.getFunctionPointer((Callback) obj);
                    }
                }
                setPointer(j, pointer);
                return;
            }
        } else if (obj != null) {
            i = ((Integer) obj).intValue();
        }
        setInt(j, i);
    }

    public void setWideString(long j, String str) {
        Native.setWideString(this, this.peer, j, str);
    }

    public Pointer share(long j) {
        return share(j, 0L);
    }

    public Pointer share(long j, long j2) {
        return j == 0 ? this : new Pointer(this.peer + j);
    }

    public String toString() {
        StringBuilder d = b.d("native@0x");
        d.append(Long.toHexString(this.peer));
        return d.toString();
    }

    public void write(long j, byte[] bArr, int i, int i2) {
        Native.write(this, this.peer, j, bArr, i, i2);
    }

    public void write(long j, char[] cArr, int i, int i2) {
        Native.write(this, this.peer, j, cArr, i, i2);
    }

    public void write(long j, double[] dArr, int i, int i2) {
        Native.write(this, this.peer, j, dArr, i, i2);
    }

    public void write(long j, float[] fArr, int i, int i2) {
        Native.write(this, this.peer, j, fArr, i, i2);
    }

    public void write(long j, int[] iArr, int i, int i2) {
        Native.write(this, this.peer, j, iArr, i, i2);
    }

    public void write(long j, long[] jArr, int i, int i2) {
        Native.write(this, this.peer, j, jArr, i, i2);
    }

    public void write(long j, Pointer[] pointerArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            setPointer((Native.POINTER_SIZE * i3) + j, pointerArr[i + i3]);
        }
    }

    public void write(long j, short[] sArr, int i, int i2) {
        Native.write(this, this.peer, j, sArr, i, i2);
    }
}
