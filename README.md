# http2-framing
A small java library that reads and writes a few http2 frame to and from bytes

## Usage

Creating a DataFrame byte array

```java
var bytes = DataFrame.bytes(23456, new byte[] { 0x42, 0x37 });

// Output:
// var bytes = new byte[]{
//    (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00,
//    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x5B,
//    (byte) 0xA0, (byte) 0x42, (byte) 0x37
// }
```
