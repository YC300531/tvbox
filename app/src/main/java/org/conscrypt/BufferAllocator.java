package org.conscrypt;

import java.nio.ByteBuffer;
public abstract class BufferAllocator {
    private static final BufferAllocator UNPOOLED = new BufferAllocator() {
        @Override
        public AllocatedBuffer allocateDirectBuffer(int i) {
            return AllocatedBuffer.wrap(ByteBuffer.allocateDirect(i));
        }
    };

    public static BufferAllocator unpooled() {
        return UNPOOLED;
    }

    public abstract AllocatedBuffer allocateDirectBuffer(int i);
}
