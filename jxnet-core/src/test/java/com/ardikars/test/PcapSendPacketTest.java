package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.exception.PcapCloseException;
import com.ardikars.jxnet.util.Validate;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.regex.Pattern;

import static com.ardikars.jxnet.Jxnet.PcapOpenLive;
import static com.ardikars.jxnet.Jxnet.PcapSendPacket;

// 10
public class PcapSendPacketTest {

    @Test
    public void run() throws PcapCloseException {
        StringBuilder errbuf = new StringBuilder();
        String dev = AllTests.deviceName;
        Pcap handler = PcapOpenLive(dev, AllTests.snaplen, AllTests.promisc, AllTests.to_ms, errbuf);
        if (handler == null) {
            throw new PcapCloseException(errbuf.toString());
        }

        byte[] data = parseHex(AllTests.rawData);

        ByteBuffer buffer = ByteBuffer.allocateDirect(data.length);
        buffer.put(data);

        System.out.println("SEND STATUS CODE: " + PcapSendPacket(handler, buffer, buffer.capacity()));
        Jxnet.PcapClose(handler);

    }

    private static final Pattern NO_SEPARATOR_HEX_STRING_PATTERN
            = Pattern.compile("\\A([0-9a-fA-F][0-9a-fA-F])+\\z");

    public static byte[] parseHex(String hexStream) {
        Validate.nullPointer(hexStream);
        if (hexStream.startsWith("0x")) {
            hexStream = hexStream.substring(2);
        }
        hexStream = hexStream.replaceAll("\\s+", "").trim();
        if (!NO_SEPARATOR_HEX_STRING_PATTERN.matcher(hexStream).matches()) {
            throw new IllegalArgumentException();
        }
        int len = hexStream.length();
        byte[] data = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexStream.charAt(i), 16) << 4)
                    + Character.digit(hexStream.charAt(i+1), 16));
        }
        return data;
    }

}
