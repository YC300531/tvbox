package com.eclipsesource.v8;

import androidx.base.a.b;
import androidx.exifinterface.media.ExifInterface;
public class V8TypedArray extends V8Array {

    public static class V8ArrayData {
        private V8ArrayBuffer buffer;
        private int offset;
        private int size;
        private int type;

        public V8ArrayData(V8ArrayBuffer v8ArrayBuffer, int i, int i2, int i3) {
            this.buffer = v8ArrayBuffer;
            this.offset = i;
            this.size = i2;
            this.type = i3;
        }
    }

    private V8TypedArray(V8 v8) {
        super(v8);
    }

    public V8TypedArray(V8 v8, V8ArrayBuffer v8ArrayBuffer, int i, int i2, int i3) {
        super(v8, new V8ArrayData(v8ArrayBuffer, i2, i3, i));
    }

    private void checkArrayProperties(V8ArrayData v8ArrayData) {
        checkOffset(v8ArrayData);
        checkSize(v8ArrayData);
    }

    private void checkOffset(V8ArrayData v8ArrayData) {
        if (v8ArrayData.offset % getStructureSize(v8ArrayData.type) == 0) {
            return;
        }
        StringBuilder d = b.d("RangeError: Start offset of Int32Array must be a multiple of ");
        d.append(getStructureSize(v8ArrayData.type));
        throw new IllegalStateException(d.toString());
    }

    private void checkSize(V8ArrayData v8ArrayData) {
        if (v8ArrayData.size < 0) {
            throw new IllegalStateException("RangeError: Invalid typed array length");
        }
        if ((getStructureSize(v8ArrayData.type) * v8ArrayData.size) + v8ArrayData.offset > v8ArrayData.buffer.limit()) {
            throw new IllegalStateException("RangeError: Invalid typed array length");
        }
    }

    private long createTypedArray(long j, V8ArrayData v8ArrayData) {
        int i = v8ArrayData.type;
        if (i != 1) {
            if (i != 2) {
                if (i != 9) {
                    switch (i) {
                        case 11:
                            return this.v8.initNewV8UInt8Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
                        case 12:
                            return this.v8.initNewV8UInt8ClampedArray(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
                        case 13:
                            return this.v8.initNewV8Int16Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
                        case 14:
                            return this.v8.initNewV8UInt16Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
                        case 15:
                            return this.v8.initNewV8UInt32Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
                        case 16:
                            return this.v8.initNewV8Float32Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
                        default:
                            StringBuilder d = b.d("Cannot create a typed array of type ");
                            d.append(V8Value.getStringRepresentation(v8ArrayData.type));
                            throw new IllegalArgumentException(d.toString());
                    }
                }
                return this.v8.initNewV8Int8Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
            }
            return this.v8.initNewV8Float64Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
        }
        return this.v8.initNewV8Int32Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
    }

    public static int getStructureSize(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i != 9) {
                    switch (i) {
                        case 11:
                        case 12:
                            break;
                        case 13:
                        case 14:
                            return 2;
                        case 15:
                        case 16:
                            return 4;
                        default:
                            StringBuilder d = b.d("Cannot create a typed array of type ");
                            d.append(V8Value.getStringRepresentation(i));
                            throw new IllegalArgumentException(d.toString());
                    }
                }
                return 1;
            }
            return 8;
        }
        return 4;
    }

    @Override
    public V8Value createTwin() {
        this.v8.checkThread();
        checkReleased();
        return new V8TypedArray(this.v8);
    }

    @Override
    public Object get(int i) {
        this.v8.checkThread();
        checkReleased();
        int type = getType();
        if (type != 1 && type != 2) {
            if (type != 9) {
                switch (type) {
                    case 11:
                        return Short.valueOf((short) (((Number) super.get(i)).shortValue() & 255));
                    case 12:
                        return Short.valueOf((short) (((Number) super.get(i)).byteValue() & ExifInterface.MARKER));
                    case 13:
                        return Short.valueOf(((Number) super.get(i)).shortValue());
                    case 14:
                        return Integer.valueOf(((Integer) super.get(i)).intValue() & 65535);
                    case 15:
                        return Long.valueOf((-1) & ((Number) super.get(i)).longValue());
                    case 16:
                        return Float.valueOf(((Number) super.get(i)).floatValue());
                    default:
                        return null;
                }
            }
            return Byte.valueOf(((Number) super.get(i)).byteValue());
        }
        return super.get(i);
    }

    public V8ArrayBuffer getBuffer() {
        return (V8ArrayBuffer) get("buffer");
    }

    @Override
    public void initialize(long j, Object obj) {
        this.v8.checkThread();
        if (obj == null) {
            super.initialize(j, obj);
            return;
        }
        V8ArrayData v8ArrayData = (V8ArrayData) obj;
        checkArrayProperties(v8ArrayData);
        long createTypedArray = createTypedArray(j, v8ArrayData);
        this.released = false;
        addObjectReference(createTypedArray);
    }
}
