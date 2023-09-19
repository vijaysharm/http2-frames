package ca.vijaysharma.http2.frames;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static ca.vijaysharma.http2.frames.FrameType.HEADERS_FRAME;
import static ca.vijaysharma.http2.frames.FrameUtilities.header;

public record HeadersFrame(long streamId, Collection<Flag> flags) implements Frame {
    public enum Flag {
        END_STREAM((byte) 0x01),
        END_HEADERS((byte) 0x04),
        PADDED((byte) 0x08),
        PRIORITY((byte) 0x20);
        final byte value;
        Flag(byte value) {
            this.value = value;
        }

        public static List<Flag> parse(byte flags) {
            List<Flag> results = new ArrayList<>();
            for (var flag : values()) {
                if ((flag.value & flags) != 0) {
                    results.add(flag);
                }
            }
            return results;
        }
    }

    @Override
    public FrameType type() {
        return HEADERS_FRAME;
    }

    @Override
    public byte[] bytes() {
        byte flag = 0x00;
        for (var item : flags) {
            flag |= item.value;
        }
        return header(0, type(), flag, streamId);
    }

    public static class Builder {
        private long streamId = 0;
        private List<Flag> flags = new ArrayList<>();

        public Builder() {
            this(0, new ArrayList<>());
        }

        public Builder setStreamId(long streamId) {
            this.streamId = streamId;
            return this;
        }

        public Builder setFlags(List<Flag> flags) {
            this.flags = flags;
            return this;
        }

        public Builder(long streamId, List<Flag> flags) {
            this.streamId = streamId;
            this.flags = flags;
        }

        public HeadersFrame build() {
            return new HeadersFrame(streamId, flags);
        }
    }
}
