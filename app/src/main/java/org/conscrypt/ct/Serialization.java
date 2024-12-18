package org.conscrypt.ct;

import androidx.base.a.a;
import androidx.exifinterface.media.ExifInterface;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import okio.Utf8;
public class Serialization {
    private static final int DER_LENGTH_LONG_FORM_FLAG = 128;
    private static final int DER_TAG_MASK = 63;
    private static final int DER_TAG_OCTET_STRING = 4;

    private Serialization() {
    }

    public static byte readByte(InputStream inputStream) {
        try {
            int read = inputStream.read();
            if (read != -1) {
                return (byte) read;
            }
            throw new SerializationException("Premature end of input, could not read byte.");
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static byte[] readDEROctetString(InputStream inputStream) {
        int readByte = readByte(inputStream) & Utf8.REPLACEMENT_BYTE;
        if (readByte == 4) {
            int readNumber = readNumber(inputStream, 1);
            if ((readNumber & 128) != 0) {
                readNumber = readNumber(inputStream, readNumber & (-129));
            }
            return readFixedBytes(inputStream, readNumber);
        }
        throw new SerializationException(a.h("Wrong DER tag, expected OCTET STRING, got ", readByte));
    }

    public static byte[] readDEROctetString(byte[] bArr) {
        return readDEROctetString(new ByteArrayInputStream(bArr));
    }

    public static byte[] readFixedBytes(InputStream inputStream, int i) {
        try {
            if (i < 0) {
                throw new SerializationException("Negative length: " + i);
            }
            byte[] bArr = new byte[i];
            int read = inputStream.read(bArr);
            if (read >= i) {
                return bArr;
            }
            throw new SerializationException("Premature end of input, expected " + i + " bytes, only read " + read);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static byte[][] readList(InputStream inputStream, int i, int i2) {
        ArrayList arrayList = new ArrayList();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(readVariableBytes(inputStream, i));
        while (byteArrayInputStream.available() > 0) {
            try {
                arrayList.add(readVariableBytes(byteArrayInputStream, i2));
            } catch (IOException e) {
                throw new SerializationException(e);
            }
        }
        return (byte[][]) arrayList.toArray(new byte[arrayList.size()]);
    }

    public static byte[][] readList(byte[] bArr, int i, int i2) {
        return readList(new ByteArrayInputStream(bArr), i, i2);
    }

    public static long readLong(InputStream inputStream, int i) {
        if (i > 8 || i < 0) {
            throw new IllegalArgumentException(a.h("Invalid width: ", i));
        }
        long j = 0;
        for (int i2 = 0; i2 < i; i2++) {
            j = (j << 8) | (readByte(inputStream) & ExifInterface.MARKER);
        }
        return j;
    }

    public static int readNumber(InputStream inputStream, int i) {
        if (i > 4 || i < 0) {
            throw new SerializationException(a.h("Invalid width: ", i));
        }
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 = (i2 << 8) | (readByte(inputStream) & ExifInterface.MARKER);
        }
        return i2;
    }

    public static byte[] readVariableBytes(InputStream inputStream, int i) {
        return readFixedBytes(inputStream, readNumber(inputStream, i));
    }

    public static void writeFixedBytes(OutputStream outputStream, byte[] bArr) {
        try {
            outputStream.write(bArr);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static void writeNumber(OutputStream outputStream, long j, int i) {
        long j2;
        if (i < 0) {
            throw new SerializationException(a.h("Negative width: ", i));
        }
        if (i < 8 && j >= (1 << (i * 8))) {
            throw new SerializationException("Number too large, " + j + " does not fit in " + i + " bytes");
        }
        while (i > 0) {
            if ((i - 1) * 8 < 64) {
                try {
                    outputStream.write((byte) ((j >> ((int) j2)) & 255));
                } catch (IOException e) {
                    throw new SerializationException(e);
                }
            } else {
                outputStream.write(0);
            }
            i--;
        }
    }

    public static void writeVariableBytes(OutputStream outputStream, byte[] bArr, int i) {
        writeNumber(outputStream, bArr.length, i);
        writeFixedBytes(outputStream, bArr);
    }
}
