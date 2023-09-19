package ca.vijaysharma.http2.frames;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static ca.vijaysharma.http2.frames.Frame.header;

public class GoAwayFrame {
    private static final byte TYPE = (byte) 0x07;
    public static byte[] bytes(
        long lastStreamId,
        int errorCode,
        byte[] extra
    ) {
        var extraSize = extra == null ? 0 : extra.length;
        var buffer = ByteBuffer.allocate(8 + extraSize)
            .order(ByteOrder.BIG_ENDIAN)
            .putInt((int) (lastStreamId & 0x7FFFFFFF))
            .putInt(errorCode);
        if (extra != null) {
            buffer.put(extra);
        }
        var data = buffer.array();
        return ByteBuffer.allocate(9 + data.length)
            .order(ByteOrder.BIG_ENDIAN)
            .put(header(data.length, TYPE, (byte) 0x00, 0))
            .put(data).array();
    }
}
