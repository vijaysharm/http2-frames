package ca.vijaysharma.http2.frames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoAwayFrameTest {
    @Test
    void can_write_frame() {
        assertArrayEquals(new byte[] {
            (byte) 0x00, (byte) 0x00, (byte) 0x0A, (byte) 0x07,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x26,
            (byte) 0x94, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x0C, (byte) 0x42, (byte) 0x37
        }, GoAwayFrame.bytes(
            9876,
            12,
            new byte[] { 0x42, 0x37 })
        );
    }
}