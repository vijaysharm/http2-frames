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
        }, new DataFrame(23456, new byte[] { 0x42, 0x37 }).bytes());
    }

    @Test
    void can_read_frame() {
        var frame = Frame.from(new byte[] {
            (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x5B,
            (byte) 0xA0, (byte) 0x42, (byte) 0x37
        });
        assertEquals(FrameType.DATA_FRAME, frame.type());
        assertInstanceOf(DataFrame.class, frame);

        var dataFrame = (DataFrame)frame;
        assertEquals(23456, dataFrame.streamId());
        assertArrayEquals(new byte[] { 0x42, 0x37 }, dataFrame.data());
    }
}