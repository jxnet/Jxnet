package com.ardikars.test;

import static com.ardikars.jxnet.Jxnet.*;

import com.ardikars.jxnet.*;
import org.junit.Test;

public class ArpAdd {

    @Test
    public void run() {
        Arp arp = ArpOpen();
        ArpEntry entry = ArpEntry.initialize(
            Addr.initialize((short) 2, (short) 32, Inet4Address.valueOf("192.168.1.24").toBytes()),
            Addr.initialize((short) 1, (short) 48, MacAddress.valueOf("de:ad:be:ef:c0:fa").toBytes())
        );
        if (ArpAdd(arp, entry) < 0) {
            System.out.println("ERROR");
        }
        ArpClose(arp);
    }

}
