package com.sun.jna;

import androidx.base.a.a;
import androidx.base.r5.b;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
public class Memory extends Pointer {
    private static LinkedReference HEAD;
    private static ReferenceQueue<Memory> QUEUE = new ReferenceQueue<>();
    private static final WeakMemoryHolder buffers = new WeakMemoryHolder();
    private final LinkedReference reference;
    public long size;

    public static class LinkedReference extends WeakReference<Memory> {
        private LinkedReference next;
        private LinkedReference prev;

        private LinkedReference(Memory memory) {
            super(memory, Memory.QUEUE);
        }

        public static LinkedReference track(Memory memory) {
            synchronized (Memory.QUEUE) {
                while (true) {
                    LinkedReference linkedReference = (LinkedReference) Memory.QUEUE.poll();
                    if (linkedReference == null) {
                        break;
                    }
                    linkedReference.unlink();
                }
            }
            LinkedReference linkedReference2 = new LinkedReference(memory);
            synchronized (LinkedReference.class) {
                if (Memory.HEAD != null) {
                    linkedReference2.next = Memory.HEAD;
                    Memory.HEAD.prev = linkedReference2;
                }
                LinkedReference unused = Memory.HEAD = linkedReference2;
            }
            return linkedReference2;
        }

        public void unlink() {
            LinkedReference linkedReference;
            synchronized (LinkedReference.class) {
                if (Memory.HEAD != this) {
                    LinkedReference linkedReference2 = this.prev;
                    if (linkedReference2 == null) {
                        return;
                    }
                    linkedReference = this.next;
                    linkedReference2.next = linkedReference;
                } else {
                    linkedReference = Memory.HEAD = Memory.HEAD.next;
                }
                if (linkedReference != null) {
                    linkedReference.prev = this.prev;
                }
                this.prev = null;
            }
        }
    }

    public class SharedMemory extends Memory {
        public SharedMemory(long j, long j2) {
            this.size = j2;
            this.peer = peer + j;
        }

        @Override
        public void boundsCheck(long j, long j2) {
            Memory memory = Memory.this;
            memory.boundsCheck((this.peer - memory.peer) + j, j2);
        }

        @Override
        public synchronized void dispose() {
            this.peer = 0L;
        }

        @Override
        public String toString() {
            return super.toString() + " (shared from " + toString() + ")";
        }
    }

    public Memory() {
        this.reference = null;
    }

    public Memory(long j) {
        this.size = j;
        if (j <= 0) {
            throw new IllegalArgumentException("Allocation size must be greater than zero");
        }
        long malloc = malloc(j);
        this.peer = malloc;
        if (malloc != 0) {
            this.reference = LinkedReference.track(this);
            return;
        }
        throw new OutOfMemoryError("Cannot allocate " + j + " bytes");
    }

    public static void disposeAll() {
        synchronized (LinkedReference.class) {
            while (true) {
                LinkedReference linkedReference = HEAD;
                if (linkedReference != null) {
                    Memory memory = linkedReference.get();
                    if (memory != null) {
                        memory.dispose();
                    } else {
                        HEAD.unlink();
                    }
                    if (HEAD == linkedReference) {
                        throw new IllegalStateException("the HEAD did not change");
                    }
                }
            }
        }
        synchronized (QUEUE) {
            while (true) {
                LinkedReference linkedReference2 = (LinkedReference) QUEUE.poll();
                if (linkedReference2 != null) {
                    linkedReference2.unlink();
                }
            }
        }
    }

    public static void free(long j) {
        if (j != 0) {
            Native.free(j);
        }
    }

    public static int integrityCheck() {
        synchronized (LinkedReference.class) {
            if (HEAD == null) {
                return 0;
            }
            ArrayList arrayList = new ArrayList();
            for (LinkedReference linkedReference = HEAD; linkedReference != null; linkedReference = linkedReference.next) {
                arrayList.add(linkedReference);
            }
            int size = arrayList.size() - 1;
            LinkedReference linkedReference2 = (LinkedReference) arrayList.get(size);
            while (linkedReference2 != null) {
                if (arrayList.get(size) != linkedReference2) {
                    throw new IllegalStateException(arrayList.get(size) + " vs. " + linkedReference2 + " at index " + size);
                }
                linkedReference2 = linkedReference2.prev;
                size--;
            }
            return arrayList.size();
        }
    }

    public static long malloc(long j) {
        return Native.malloc(j);
    }

    public static void purge() {
        buffers.clean();
    }

    private Pointer shareReferenceIfInBounds(Pointer pointer) {
        if (pointer == null) {
            return null;
        }
        long j = pointer.peer - this.peer;
        return (j < 0 || j >= this.size) ? pointer : share(j);
    }

    public Memory align(int i) {
        if (i > 0) {
            for (int i2 = 0; i2 < 32; i2++) {
                if (i == (1 << i2)) {
                    long j = i;
                    long j2 = (j - 1) ^ (-1);
                    long j3 = this.peer;
                    if ((j3 & j2) != j3) {
                        long j4 = ((j + j3) - 1) & j2;
                        long j5 = (this.size + j3) - j4;
                        if (j5 > 0) {
                            return (Memory) share(j4 - j3, j5);
                        }
                        throw new IllegalArgumentException("Insufficient memory to align to the requested boundary");
                    }
                    return this;
                }
            }
            throw new IllegalArgumentException("Byte boundary must be a power of two");
        }
        throw new IllegalArgumentException(a.h("Byte boundary must be positive: ", i));
    }

    public void boundsCheck(long j, long j2) {
        if (j < 0) {
            throw new IndexOutOfBoundsException(b.d("Invalid offset: ", j));
        }
        long j3 = j + j2;
        if (j3 <= this.size) {
            return;
        }
        StringBuilder d = androidx.base.a.b.d("Bounds exceeds available space : size=");
        d.append(this.size);
        d.append(", offset=");
        d.append(j3);
        throw new IndexOutOfBoundsException(d.toString());
    }

    public void clear() {
        clear(this.size);
    }

    public synchronized void dispose() {
        long j = this.peer;
        if (j == 0) {
            return;
        }
        free(j);
        this.peer = 0L;
        this.reference.unlink();
    }

    public String dump() {
        return dump(0L, (int) size());
    }

    public void finalize() {
        dispose();
    }

    @Override
    public byte getByte(long j) {
        boundsCheck(j, 1L);
        return super.getByte(j);
    }

    @Override
    public ByteBuffer getByteBuffer(long j, long j2) {
        boundsCheck(j, j2);
        ByteBuffer byteBuffer = super.getByteBuffer(j, j2);
        buffers.put(byteBuffer, this);
        return byteBuffer;
    }

    @Override
    public char getChar(long j) {
        boundsCheck(j, Native.WCHAR_SIZE);
        return super.getChar(j);
    }

    @Override
    public double getDouble(long j) {
        boundsCheck(j, 8L);
        return super.getDouble(j);
    }

    @Override
    public float getFloat(long j) {
        boundsCheck(j, 4L);
        return super.getFloat(j);
    }

    @Override
    public int getInt(long j) {
        boundsCheck(j, 4L);
        return super.getInt(j);
    }

    @Override
    public long getLong(long j) {
        boundsCheck(j, 8L);
        return super.getLong(j);
    }

    @Override
    public Pointer getPointer(long j) {
        boundsCheck(j, Native.POINTER_SIZE);
        return shareReferenceIfInBounds(super.getPointer(j));
    }

    @Override
    public short getShort(long j) {
        boundsCheck(j, 2L);
        return super.getShort(j);
    }

    @Override
    public String getString(long j, String str) {
        boundsCheck(j, 0L);
        return super.getString(j, str);
    }

    @Override
    public String getWideString(long j) {
        boundsCheck(j, 0L);
        return super.getWideString(j);
    }

    @Override
    public void read(long j, byte[] bArr, int i, int i2) {
        boundsCheck(j, i2 * 1);
        super.read(j, bArr, i, i2);
    }

    @Override
    public void read(long j, char[] cArr, int i, int i2) {
        boundsCheck(j, Native.WCHAR_SIZE * i2);
        super.read(j, cArr, i, i2);
    }

    @Override
    public void read(long j, double[] dArr, int i, int i2) {
        boundsCheck(j, i2 * 8);
        super.read(j, dArr, i, i2);
    }

    @Override
    public void read(long j, float[] fArr, int i, int i2) {
        boundsCheck(j, i2 * 4);
        super.read(j, fArr, i, i2);
    }

    @Override
    public void read(long j, int[] iArr, int i, int i2) {
        boundsCheck(j, i2 * 4);
        super.read(j, iArr, i, i2);
    }

    @Override
    public void read(long j, long[] jArr, int i, int i2) {
        boundsCheck(j, i2 * 8);
        super.read(j, jArr, i, i2);
    }

    @Override
    public void read(long j, Pointer[] pointerArr, int i, int i2) {
        boundsCheck(j, Native.POINTER_SIZE * i2);
        super.read(j, pointerArr, i, i2);
    }

    @Override
    public void read(long j, short[] sArr, int i, int i2) {
        boundsCheck(j, i2 * 2);
        super.read(j, sArr, i, i2);
    }

    @Override
    public void setByte(long j, byte b) {
        boundsCheck(j, 1L);
        super.setByte(j, b);
    }

    @Override
    public void setChar(long j, char c) {
        boundsCheck(j, Native.WCHAR_SIZE);
        super.setChar(j, c);
    }

    @Override
    public void setDouble(long j, double d) {
        boundsCheck(j, 8L);
        super.setDouble(j, d);
    }

    @Override
    public void setFloat(long j, float f) {
        boundsCheck(j, 4L);
        super.setFloat(j, f);
    }

    @Override
    public void setInt(long j, int i) {
        boundsCheck(j, 4L);
        super.setInt(j, i);
    }

    @Override
    public void setLong(long j, long j2) {
        boundsCheck(j, 8L);
        super.setLong(j, j2);
    }

    @Override
    public void setPointer(long j, Pointer pointer) {
        boundsCheck(j, Native.POINTER_SIZE);
        super.setPointer(j, pointer);
    }

    @Override
    public void setShort(long j, short s) {
        boundsCheck(j, 2L);
        super.setShort(j, s);
    }

    @Override
    public void setString(long j, String str, String str2) {
        boundsCheck(j, Native.getBytes(str, str2).length + 1);
        super.setString(j, str, str2);
    }

    @Override
    public void setWideString(long j, String str) {
        boundsCheck(j, (str.length() + 1) * Native.WCHAR_SIZE);
        super.setWideString(j, str);
    }

    @Override
    public Pointer share(long j) {
        return share(j, size() - j);
    }

    @Override
    public Pointer share(long j, long j2) {
        boundsCheck(j, j2);
        return new SharedMemory(j, j2);
    }

    public long size() {
        return this.size;
    }

    @Override
    public String toString() {
        StringBuilder d = androidx.base.a.b.d("allocated@0x");
        d.append(Long.toHexString(this.peer));
        d.append(" (");
        return a.l(d, this.size, " bytes)");
    }

    public boolean valid() {
        return this.peer != 0;
    }

    @Override
    public void write(long j, byte[] bArr, int i, int i2) {
        boundsCheck(j, i2 * 1);
        super.write(j, bArr, i, i2);
    }

    @Override
    public void write(long j, char[] cArr, int i, int i2) {
        boundsCheck(j, Native.WCHAR_SIZE * i2);
        super.write(j, cArr, i, i2);
    }

    @Override
    public void write(long j, double[] dArr, int i, int i2) {
        boundsCheck(j, i2 * 8);
        super.write(j, dArr, i, i2);
    }

    @Override
    public void write(long j, float[] fArr, int i, int i2) {
        boundsCheck(j, i2 * 4);
        super.write(j, fArr, i, i2);
    }

    @Override
    public void write(long j, int[] iArr, int i, int i2) {
        boundsCheck(j, i2 * 4);
        super.write(j, iArr, i, i2);
    }

    @Override
    public void write(long j, long[] jArr, int i, int i2) {
        boundsCheck(j, i2 * 8);
        super.write(j, jArr, i, i2);
    }

    @Override
    public void write(long j, Pointer[] pointerArr, int i, int i2) {
        boundsCheck(j, Native.POINTER_SIZE * i2);
        super.write(j, pointerArr, i, i2);
    }

    @Override
    public void write(long j, short[] sArr, int i, int i2) {
        boundsCheck(j, i2 * 2);
        super.write(j, sArr, i, i2);
    }
}
