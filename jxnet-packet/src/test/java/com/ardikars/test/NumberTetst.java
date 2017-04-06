package com.ardikars.test;

import com.ardikars.jxnet.packet.tcp.TCPFlags;

import java.nio.ByteBuffer;

public class NumberTetst {

    public static byte ONE = (byte) 1;
    public static byte ZERO = (byte) 0;

    public static void main(String[] args) {
        TCPFlags flags = TCPFlags.newInstance(ZERO, ONE, ZERO, ONE, ONE, ZERO, ONE, ZERO, ONE);
        System.out.println(flags);
        short sflags = flags.toShort();
        TCPFlags f = TCPFlags.newInstance(sflags);
        System.out.println(f);

    }


}
