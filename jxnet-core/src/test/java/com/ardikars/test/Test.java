package com.ardikars.test;


import com.ardikars.jxnet.util.ByteUtils;
import com.ardikars.jxnet.util.FormatUtils;
import com.ardikars.jxnet.util.HexUtils;

import java.util.Random;

public class Test {

    public static void main(String[] args) {
        Random random = new Random();
        int[] integer = new int[100];
        for (int i=0; i<100; i++) {
            integer[i] = random.nextInt();
        }
        byte[] data = ByteUtils.toByteArray(integer);
        System.out.println(HexUtils.toPrettyHexDump(data));
    }

}
