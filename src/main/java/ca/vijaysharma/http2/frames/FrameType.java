package ca.vijaysharma.http2.frames;

public enum FrameType {
    DATA_FRAME((byte)0x00),
    HEADERS_FRAME((byte)0x01),
    RST_STREAM_FRAME((byte)0x03),
    SETTINGS_FRAME((byte)0x04),
    GO_AWAY_FRAME((byte)0x07),
    WINDOW_UPDATE_FRAME((byte)0x08)
    ;
    final byte value;
    FrameType(byte value) {
        this.value = value;
    }

    public static FrameType from(byte type) {
        for (var value : values()) {
            if ((value.value & type) == value.value) {
                return value;
            }
        }
        throw new RuntimeException("Unknown or Unsupported Frame type " + type + "=(" + Integer.toHexString(type) + ")" );
    }
}
