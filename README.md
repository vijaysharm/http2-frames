# http2-framing
A small java library that reads and writes a few http2 frame to and from bytes

Currently supported frames:
 * DataFrame
 * GoAwayFrame
 * HeadersFrame
 * RstStreamFrame
 * SettingsFrame
 * WindowUpdateFrame

## Usage

Creating a DataFrame byte array

```java
var bytes = new DataFrame(23456, new byte[] { 0x42, 0x37 }).bytes();

// Output:
// var bytes = new byte[]{
//    (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00,
//    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x5B,
//    (byte) 0xA0, (byte) 0x42, (byte) 0x37
// }
```

Reading a DataFrame from a byte array

```java
var frame = Frame.from(new byte[] {
    (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00,
    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x5B,
    (byte) 0xA0, (byte) 0x42, (byte) 0x37
});

// Output:
// DataFrame[streamId=23456, data=[{byte[2]@[0x42, 0x37]}]
```