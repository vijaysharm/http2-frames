package ca.vijaysharma.http2.frames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RstStreamFrameTest {
    @Test
    void can_write_frame() {
        assertArrayEquals(new byte[] {
            (byte) 0x0,(byte) 0x0,(byte) 0x4,(byte) 0x3,
            (byte) 0x0,(byte) 0x0,(byte) 0x0,(byte) 0x15,
            (byte) 0x27,(byte) 0x0,(byte) 0x0,(byte) 0x0,
            (byte) 0x13
        }, RstStreamFrame.bytes(
            5415,
            19
        ));
    }
}