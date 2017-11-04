package com.ardikars.test;

import java.nio.ByteBuffer;

public class Test {

    @org.junit.Test
    public void test() {

        ByteBuffer buffer = ByteBuffer.allocateDirect(1100);
        buffer.putInt(1);
        buffer.putInt(1);
        buffer.putInt(1);
        buffer.putInt(1);
        buffer.putInt(1);
        buffer.putInt(1);
        buffer.putInt(1);
        buffer.putInt(1);
        buffer.position(0);
        buffer.rewind();
        System.out.println("Position   : " + buffer.position());
        System.out.println("Limit      : " + buffer.limit());
        System.out.println("Capacity   : " + buffer.capacity());
    }

}
