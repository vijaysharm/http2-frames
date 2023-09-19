package ca.vijaysharma.http2.frames;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static ca.vijaysharma.http2.frames.FrameType.RST_STREAM_FRAME;
import static ca.vijaysharma.http2.frames.FrameUtilities.header;

public record RstStreamFrame(long streamId, int errorCode) implements Frame {

    @Override
    public FrameType type() {
        return RST_STREAM_FRAME;
    }

    @Override
    public byte[] bytes() {
        var buffer = ByteBuffer.allocate(4)
            .order(ByteOrder.BIG_ENDIAN)
            .putInt(errorCode);
        var data = buffer.array();
        return ByteBuffer.allocate(9 + data.length)
            .order(ByteOrder.BIG_ENDIAN)
            .put(header(data.length, type(), (byte) 0x00, streamId))
            .put(data).array();
    }

    public static class Builder {
        private long streamId = 0;
        private int errorCode = 0;

        public Builder() {
            this(0, 0);
        }

        public Builder(long streamId, int errorCode) {
            this.streamId = streamId;
            this.errorCode = errorCode;
        }

        public Builder setStreamId(long streamId) {
            this.streamId = streamId;
            return this;
        }

        public Builder setErrorCode(int errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public RstStreamFrame build() {
            return new RstStreamFrame(streamId, errorCode);
        }
    }
}
