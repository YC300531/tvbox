package org.conscrypt;

import androidx.base.a.a;
public final class ArrayUtils {
    private ArrayUtils() {
    }

    public static void checkOffsetAndCount(int i, int i2, int i3) {
        if ((i2 | i3) < 0 || i2 > i || i - i2 < i3) {
            StringBuilder o = a.o("length=", i, "; regionStart=", i2, "; regionLength=");
            o.append(i3);
            throw new ArrayIndexOutOfBoundsException(o.toString());
        }
    }
}
