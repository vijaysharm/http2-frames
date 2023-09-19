package ca.vijaysharma.http2.frames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataFrameTest {
    @Test
    void can_write_frame() {
        assertArrayEquals(new byte[] {
            (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x5B,
            (byte) 0xA0, (byte) 0x42, (byte) 0x37
        }, DataFrame.bytes(23456, new byte[] { 0x42, 0x37 }));
    }
}