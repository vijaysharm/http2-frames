package ca.vijaysharma.http2.frames;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

import static ca.vijaysharma.http2.frames.FrameType.SETTINGS_FRAME;
import static ca.vijaysharma.http2.frames.FrameUtilities.header;

public record SettingsFrame(Map<Settings, Integer> settings, Collection<Flag> flags) implements Frame {
    public enum Settings {
        //: The byte that signals the SETTINGS_HEADER_TABLE_SIZE setting.
        HEADER_TABLE_SIZE((byte) 0x01),
        //: The byte that signals the SETTINGS_ENABLE_PUSH setting.
        ENABLE_PUSH((byte) 0x02),
        //: The byte that signals the SETTINGS_MAX_CONCURRENT_STREAMS setting.
        MAX_CONCURRENT_STREAMS((byte) 0x03),
        //: The byte that signals the SETTINGS_INITIAL_WINDOW_SIZE setting.
        INITIAL_WINDOW_SIZE((byte) 0x04),
        //: The byte that signals the SETTINGS_MAX_FRAME_SIZE setting.
        MAX_FRAME_SIZE((byte) 0x05),
        //: The byte that signals the SETTINGS_MAX_HEADER_LIST_SIZE setting.
        MAX_HEADER_LIST_SIZE((byte) 0x06),
        //: The byte that signals SETTINGS_ENABLE_CONNECT_PROTOCOL setting.
        ENABLE_CONNECT_PROTOCOL((byte) 0x08);

        final byte value;
        Settings(byte value) {
            this.value = value;
        }
    }
    public enum Flag {
        ACK((byte) 0x01);
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
        return SETTINGS_FRAME;
    }

    @Override
    public byte[] bytes() {
        var dataSize = settings.size() * 6; // 2 bytes for key, 4 for value
        var dataBuffer = ByteBuffer.allocate(dataSize)
            .order(ByteOrder.BIG_ENDIAN);
        for (var entry : settings.entrySet()) {
            var key = (byte)0xFF  & entry.getKey().value;
            dataBuffer
                .putShort((short)key)
                .putInt(entry.getValue());
        }
        var data = dataBuffer.array();

        byte flag = 0x00;
        for (var item : flags) {
            flag |= item.value;
        }

        var buffer = ByteBuffer.allocate(9 + dataSize)
            .order(ByteOrder.BIG_ENDIAN)
            .put(header(dataSize, type(), flag, 0));
        if (dataSize > 0) {
            buffer.put(data);
        }

        return buffer.array();
    }

    public static class Builder {
        private Map<Settings, Integer> settings = new HashMap<>();
        private List<Flag> flags = new ArrayList<>();

        public Builder() {
            this(new HashMap<>(), new ArrayList<>());
        }

        public Builder(Map<Settings, Integer> settings, List<Flag> flags) {
            this.settings = settings;
            this.flags = flags;
        }

        public Builder setSettings(Map<Settings, Integer> settings) {
            this.settings = settings;
            return this;
        }

        public Builder setFlags(List<Flag> flags) {
            this.flags = flags;
            return this;
        }

        public SettingsFrame build() {
            return new SettingsFrame(settings, flags);
        }
    }
}
