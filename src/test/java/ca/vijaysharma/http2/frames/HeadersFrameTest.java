package ca.vijaysharma.http2.frames;

import org.junit.jupiter.api.Test;

import java.util.List;

import static ca.vijaysharma.http2.frames.HeadersFrame.Flag.END_HEADERS;
import static org.junit.jupiter.api.Assertions.*;

class HeadersFrameTest {
    @Test
    void can_write_frame() {
        assertArrayEquals(new byte[] {
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
            (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x1E,
            (byte) 0xA5
        }, new HeadersFrame.Builder(
            7845,
            List.of(END_HEADERS)
        ).build().bytes());
    }
}