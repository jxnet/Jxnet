package com.ardikars.test;

import java.nio.ByteBuffer;

/**
 * Created by root on 02/04/17.
 */
public class NumberTetst {

    private short number;

    public void setNumber(short number) {
        this.number = number;
    }

    public short getNumber() {
        return number;
    }


    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocateDirect(4);
        bb.putShort((short) 65535);

        bb.rewind();
        System.out.println(bb.getShort() & 0xffff);

    }


}
