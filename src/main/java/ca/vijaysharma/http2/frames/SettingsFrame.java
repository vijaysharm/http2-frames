package ca.vijaysharma.http2.frames;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;

import static ca.vijaysharma.http2.frames.Frame.header;

public class SettingsFrame {
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
    }
    private static final byte TYPE = (byte) 0x04;

    public static byte[] bytes(Map<Settings, Integer> settings, Flag...flags) {
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
                .put(header(dataSize, TYPE, flag, 0));
        if (dataSize > 0) {
            buffer.put(data);
        }

        return buffer.array();
    }
}
