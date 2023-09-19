package ca.vijaysharma.http2.frames;

import static ca.vijaysharma.http2.frames.Frame.header;

public class HeadersFrame {
    public enum Flag {
        END_STREAM((byte) 0x01),
        END_HEADERS((byte) 0x04),
        PADDED((byte) 0x08),
        PRIORITY((byte) 0x20);
        final byte value;
        Flag(byte value) {
            this.value = value;
        }
    }

    private static final byte TYPE = (byte) 0x01;

    public static byte[] bytes(
            long streamId,
            Flag... flags
    ) {
        byte flag = 0x00;
        for (var item : flags) {
            flag |= item.value;
        }
        return header(0, TYPE, flag, streamId);
    }
}
