package ca.vijaysharma.http2.frames;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static ca.vijaysharma.http2.frames.FrameUtilities.header;

public record DataFrame(long streamId, byte[] data) implements Frame {
    @Override
    public FrameType type() {
        return FrameType.DATA_FRAME;
    }

    @Override
    public byte[] bytes() {
        var dataSize = data == null ? 0 : data.length;

        var buffer = ByteBuffer.allocate(9 + dataSize)
            .order(ByteOrder.BIG_ENDIAN)
            .put(header(dataSize, type(), (byte) 0x00, streamId));
        if (data != null) {
            buffer.put(data);
        }

        return buffer.array();
    }

    public static class Builder {
        private long streamId = 0;
        private byte[] data = null;

        public Builder() {
            this(0, null);
        }

        public Builder(long streamId, byte[] data) {
            this.streamId = streamId;
            this.data = data;
        }

        public Builder setStreamId(long streamId) {
            this.streamId = streamId;
            return this;
        }

        public Builder setData(byte[] data) {
            this.data = data;
            return this;
        }

        public DataFrame build() {
            return new DataFrame(streamId, data);
        }
    }
}
