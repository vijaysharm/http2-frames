package ca.vijaysharma.http2.frames;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

public interface Frame {
    FrameType type();
    byte[] bytes();

    static Frame from(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);
        var lengthPart1 = (int)buffer.getShort();
        var lengthPart2 = (int)buffer.get();

        var length = (lengthPart1 << 8) + lengthPart2;
        var type = FrameType.from(buffer.get());
        var flags = buffer.get();
        var streamId = buffer.getInt() & 0x7FFFFFFF;
        var remaining = new byte[length];
        buffer.get(remaining, 0, length);

        return switch (type) {
            case DATA_FRAME -> new DataFrame.Builder()
                .setStreamId(streamId)
                .setData(remaining)
                .build();
            case HEADERS_FRAME -> new HeadersFrame.Builder()
                .setStreamId(streamId)
                .setFlags(HeadersFrame.Flag.parse(flags))
                .build();
            case RST_STREAM_FRAME -> new RstStreamFrame.Builder()
                .setStreamId(streamId)
                .setErrorCode(0 /* TODO: parse the remaining to get data */)
                .build();
            case SETTINGS_FRAME -> new SettingsFrame.Builder()
                .setSettings(Map.of() /* TODO: parse the remaining to get data */)
                .setFlags(SettingsFrame.Flag.parse(flags))
                .build();
            case GO_AWAY_FRAME -> new GoAwayFrame.Builder()
                /* TODO: parse the remaining to get data */
                .build();
            case WINDOW_UPDATE_FRAME -> new WindowUpdateFrame.Builder()
                /* TODO: parse the remaining to get data */
                .build();
        };
    }
}
