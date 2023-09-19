package ca.vijaysharma.http2.frames;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FrameUtilities {
    private FrameUtilities() {}

    static byte[] header(
        int bodyLength,
        FrameType type,
        byte flags,
        long streamId
    ) {
        return ByteBuffer.allocate(9)
            .order(ByteOrder.BIG_ENDIAN)
            .putShort((short) ((bodyLength >> 8) & 0xFFFF))
            .put((byte) (bodyLength & 0xFF))
            .put(type.value)
            .put(flags)
            .putInt((int) (streamId & 0x7FFFFFFF))
            .array();
    }
}
