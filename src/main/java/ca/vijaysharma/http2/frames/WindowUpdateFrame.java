package ca.vijaysharma.http2.frames;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static ca.vijaysharma.http2.frames.FrameUtilities.header;

public record WindowUpdateFrame(long streamId, int windowIncrement) implements Frame{
    @Override
    public FrameType type() {
        return FrameType.WINDOW_UPDATE_FRAME;
    }

    @Override
    public byte[] bytes() {
        var buffer = ByteBuffer.allocate(4)
            .order(ByteOrder.BIG_ENDIAN)
            .putInt((windowIncrement & 0x7FFFFFFF));
        var data = buffer.array();
        return ByteBuffer.allocate(9 + data.length)
            .order(ByteOrder.BIG_ENDIAN)
            .put(header(data.length, type(), (byte) 0x00, streamId))
            .put(data).array();
    }

    public static class Builder {
        private long streamId = 0;
        private int windowIncrement = 0;

        public Builder() {
            this(0, 0);
        }

        public Builder(long streamId, int windowIncrement) {
            this.streamId = streamId;
            this.windowIncrement = windowIncrement;
        }

        public Builder setStreamId(long streamId) {
            this.streamId = streamId;
            return this;
        }

        public Builder setWindowIncrement(int windowIncrement) {
            this.windowIncrement = windowIncrement;
            return this;
        }

        public WindowUpdateFrame build() {
            return new WindowUpdateFrame(streamId, windowIncrement);
        }
    }
}
