package ca.vijaysharma.http2.frames;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static ca.vijaysharma.http2.frames.Frame.header;

public class RstStreamFrame {
    private static final byte TYPE = (byte) 0x03;

    public static byte[] bytes(
        long streamId,
        int errorCode
    ) {
        var buffer = ByteBuffer.allocate(4)
            .order(ByteOrder.BIG_ENDIAN)
            .putInt(errorCode);
        var data = buffer.array();
        return ByteBuffer.allocate(9 + data.length)
            .order(ByteOrder.BIG_ENDIAN)
            .put(header(data.length, TYPE, (byte) 0x00, streamId))
            .put(data).array();
    }
}
