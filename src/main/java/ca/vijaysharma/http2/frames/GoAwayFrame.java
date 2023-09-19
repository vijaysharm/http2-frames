package ca.vijaysharma.http2.frames;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static ca.vijaysharma.http2.frames.FrameType.GO_AWAY_FRAME;
import static ca.vijaysharma.http2.frames.FrameUtilities.header;

public record GoAwayFrame(long lastStreamId, int errorCode, byte[] extra) implements Frame {
    @Override
    public FrameType type() {
        return GO_AWAY_FRAME;
    }

    @Override
    public byte[] bytes() {
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
                .put(header(data.length, type(), (byte) 0x00, 0))
                .put(data).array();
    }

    public static class Builder {
        private long lastStreamId = 0;
        private int errorCode = 0;
        private byte[] extra = null;

        public Builder() {
            this(0, 0, null);
        }

        public Builder(long lastStreamId, int errorCode, byte[] extra) {
            this.lastStreamId = lastStreamId;
            this.errorCode = errorCode;
            this.extra = extra;
        }

        public Builder setLastStreamId(long lastStreamId) {
            this.lastStreamId = lastStreamId;
            return this;
        }

        public Builder setErrorCode(int errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder setExtra(byte[] extra) {
            this.extra = extra;
            return this;
        }

        public GoAwayFrame build() {
            return new GoAwayFrame(lastStreamId, errorCode, extra);
        }
    }
}
