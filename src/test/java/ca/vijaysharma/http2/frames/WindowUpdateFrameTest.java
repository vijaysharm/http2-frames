package ca.vijaysharma.http2.frames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class WindowUpdateFrameTest {
    @Test
    void can_write_frame() {
        assertArrayEquals(new byte[] {
            (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x08,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x26,
            (byte) 0xD9, (byte) 0x00, (byte) 0x0F, (byte) 0x00, (byte) 0x01
        }, new WindowUpdateFrame(9945, 983041).bytes());
    }
}