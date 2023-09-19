package ca.vijaysharma.http2.frames;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * >: big-endian, std. size & alignment
 * B:unsigned byte
 * L:unsigned long;
 * H:unsigned short
 *
 *
 * _STRUCT_LL = struct.Struct(">LL")
 * _STRUCT_HL = struct.Struct(">HL")
 * _STRUCT_LB = struct.Struct(">LB")
 * _STRUCT_L = struct.Struct(">L")
 * _STRUCT_H = struct.Struct(">H")
 * _STRUCT_B = struct.Struct(">B")
 *
 * DataFrame, Frame, GoAwayFrame, HeadersFrame, RstStreamFrame, SettingsFrame, WindowUpdateFrame
 */
public class Frame {
    // _STRUCT_HBBBL = struct.Struct(">HBBBL")
    static byte[] header(
        int bodyLength,
        byte type,
        byte flags,
        long streamId
    ) {
        return ByteBuffer.allocate(9)
            .order(ByteOrder.BIG_ENDIAN)
            .putShort((short) ((bodyLength >> 8) & 0xFFFF))
            .put((byte) (bodyLength & 0xFF))
            .put(type)
            .put(flags)
            .putInt((int) (streamId & 0x7FFFFFFF))
            .array();
    }
}
