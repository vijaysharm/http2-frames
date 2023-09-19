package ca.vijaysharma.http2.frames;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static ca.vijaysharma.http2.frames.SettingsFrame.Flag.ACK;
import static org.junit.jupiter.api.Assertions.*;

class SettingsFrameTest {
    @Test
    void can_write_frame() {
        assertArrayEquals(new byte[] {
            (byte) 0x00, (byte) 0x00, (byte) 0x0C, (byte) 0x04,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x64, (byte) 0x00,
            (byte) 0x04, (byte) 0x00, (byte) 0x10, (byte) 0x00,
            (byte) 0x00
        }, SettingsFrame.bytes(
            Map.of(
                SettingsFrame.Settings.MAX_CONCURRENT_STREAMS, 100,
                SettingsFrame.Settings.INITIAL_WINDOW_SIZE, 1048576
            )
        ));
    }

    @Test
    void can_write_ack_settings_frame() {
        assertArrayEquals(new byte[] {
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04,
            (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00
        }, SettingsFrame.bytes(
            Map.of(), ACK
        ));
    }
}