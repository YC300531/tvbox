package com.eclipsesource.v8;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
public class V8ArrayBuffer extends V8Value {
    public ByteBuffer byteBuffer;

    public V8ArrayBuffer(V8 v8, int i) {
        super(v8);
        initialize(v8.getV8RuntimePtr(), Integer.valueOf(i));
        ByteBuffer createV8ArrayBufferBackingStore = v8.createV8ArrayBufferBackingStore(v8.getV8RuntimePtr(), this.objectHandle, i);
        this.byteBuffer = createV8ArrayBufferBackingStore;
        createV8ArrayBufferBackingStore.order(ByteOrder.nativeOrder());
    }

    public V8ArrayBuffer(V8 v8, ByteBuffer byteBuffer) {
        super(v8);
        byteBuffer = byteBuffer == null ? ByteBuffer.allocateDirect(0) : byteBuffer;
        if (!byteBuffer.isDirect()) {
            throw new IllegalArgumentException("ByteBuffer must be a allocated as a direct ByteBuffer");
        }
        initialize(v8.getV8RuntimePtr(), byteBuffer);
        this.byteBuffer = byteBuffer;
        byteBuffer.order(ByteOrder.nativeOrder());
    }

    public final byte[] array() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.array();
    }

    public final int arrayOffset() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.arrayOffset();
    }

    public final int capacity() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.capacity();
    }

    public final V8ArrayBuffer clear() {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.clear();
        return this;
    }

    public V8ArrayBuffer compact() {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.compact();
        return this;
    }

    @Override
    public V8Value createTwin() {
        return new V8ArrayBuffer(this.v8, this.byteBuffer);
    }

    public int doubleLimit() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.asDoubleBuffer().limit();
    }

    public final V8ArrayBuffer flip() {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.flip();
        return this;
    }

    public int floatLimit() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.asFloatBuffer().limit();
    }

    public byte get() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.get();
    }

    public byte get(int i) {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.get(i);
    }

    public V8ArrayBuffer get(byte[] bArr) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.get(bArr);
        return this;
    }

    public V8ArrayBuffer get(byte[] bArr, int i, int i2) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.get(bArr, i, i2);
        return this;
    }

    public char getChar() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.getChar();
    }

    public char getChar(int i) {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.getChar(i);
    }

    public double getDouble() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.getDouble();
    }

    public double getDouble(int i) {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.getDouble(i);
    }

    public float getFloat() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.getFloat();
    }

    public float getFloat(int i) {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.getFloat(i);
    }

    public int getInt() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.getInt();
    }

    public int getInt(int i) {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.getInt(i);
    }

    public long getLong() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.getLong();
    }

    public long getLong(int i) {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.getLong(i);
    }

    public short getShort() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.getShort();
    }

    public short getShort(int i) {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.getShort(i);
    }

    public final boolean hasArray() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.hasArray();
    }

    public final boolean hasRemaining() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.hasRemaining();
    }

    @Override
    public void initialize(long j, Object obj) {
        long initNewV8ArrayBuffer;
        this.v8.checkThread();
        if (obj instanceof ByteBuffer) {
            ByteBuffer byteBuffer = (ByteBuffer) obj;
            int limit = byteBuffer.limit();
            V8 v8 = this.v8;
            initNewV8ArrayBuffer = v8.initNewV8ArrayBuffer(v8.getV8RuntimePtr(), byteBuffer, limit);
        } else {
            int intValue = ((Integer) obj).intValue();
            V8 v82 = this.v8;
            initNewV8ArrayBuffer = v82.initNewV8ArrayBuffer(v82.getV8RuntimePtr(), intValue);
        }
        this.objectHandle = initNewV8ArrayBuffer;
        this.released = false;
        addObjectReference(this.objectHandle);
    }

    public int intLimit() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.asIntBuffer().limit();
    }

    public boolean isDirect() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.isDirect();
    }

    public boolean isReadOnly() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.isReadOnly();
    }

    public int limit() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.limit();
    }

    public final V8ArrayBuffer limit(int i) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.limit(i);
        return this;
    }

    public final V8ArrayBuffer mark() {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.mark();
        return this;
    }

    public final V8ArrayBuffer order(ByteOrder byteOrder) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.order(byteOrder);
        return this;
    }

    public final ByteOrder order() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.order();
    }

    public final int position() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.position();
    }

    public final V8ArrayBuffer position(int i) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.position(i);
        return this;
    }

    public V8ArrayBuffer put(byte b) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.put(b);
        return this;
    }

    public V8ArrayBuffer put(int i, byte b) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.put(i, b);
        return this;
    }

    public V8ArrayBuffer put(ByteBuffer byteBuffer) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.put(byteBuffer);
        return this;
    }

    public final V8ArrayBuffer put(byte[] bArr) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.put(bArr);
        return this;
    }

    public V8ArrayBuffer put(byte[] bArr, int i, int i2) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.put(bArr, i, i2);
        return this;
    }

    public V8ArrayBuffer putChar(char c) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.putChar(c);
        return this;
    }

    public V8ArrayBuffer putChar(int i, char c) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.putChar(i, c);
        return this;
    }

    public V8ArrayBuffer putDouble(double d) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.putDouble(d);
        return this;
    }

    public V8ArrayBuffer putDouble(int i, double d) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.putDouble(i, d);
        return this;
    }

    public V8ArrayBuffer putFloat(float f) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.putFloat(f);
        return this;
    }

    public V8ArrayBuffer putFloat(int i, float f) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.putFloat(i, f);
        return this;
    }

    public V8ArrayBuffer putInt(int i) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.putInt(i);
        return this;
    }

    public V8ArrayBuffer putInt(int i, int i2) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.asIntBuffer().put(i, i2);
        return this;
    }

    public V8ArrayBuffer putLong(int i, long j) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.putLong(i, j);
        return this;
    }

    public V8ArrayBuffer putLong(long j) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.putLong(j);
        return this;
    }

    public V8ArrayBuffer putShort(int i, short s) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.putShort(i, s);
        return this;
    }

    public V8ArrayBuffer putShort(short s) {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.putShort(s);
        return this;
    }

    public final int remaining() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.remaining();
    }

    public final V8ArrayBuffer reset() {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.reset();
        return this;
    }

    public final V8ArrayBuffer rewind() {
        this.v8.checkThread();
        checkReleased();
        this.byteBuffer.rewind();
        return this;
    }

    public int shortLimit() {
        this.v8.checkThread();
        checkReleased();
        return this.byteBuffer.asShortBuffer().limit();
    }

    @Override
    public V8ArrayBuffer twin() {
        this.v8.checkThread();
        checkReleased();
        return (V8ArrayBuffer) super.twin();
    }
}
