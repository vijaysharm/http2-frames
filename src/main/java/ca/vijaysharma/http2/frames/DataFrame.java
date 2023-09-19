package ca.vijaysharma.http2.frames;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static ca.vijaysharma.http2.frames.Frame.header;

public class DataFrame {
    private static final byte TYPE = (byte) 0x00;

    public static byte[] bytes(
        long streamId,
        byte[] data
    ) {
        var dataSize = data == null ? 0 : data.length;

        var buffer = ByteBuffer.allocate(9 + dataSize)
                .order(ByteOrder.BIG_ENDIAN)
                .put(header(dataSize, TYPE, (byte) 0x00, streamId));
        if (data != null) {
            buffer.put(data);
        }

        return buffer.array();
    }
}
