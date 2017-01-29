package com.ardikars.test;

import org.junit.Test;

import static com.ardikars.jxnet.Jxnet.*;
import com.ardikars.jxnet.*;

/**
 * Created by root on 1/29/17.
 */
public class ArpGet {

    @Test
    public void run() {
        Arp arp = ArpOpen();
        ArpEntry entry = ArpEntry.initialize(
                Addr.initialize((short) 2, (short) 32, Inet4Address.valueOf("192.168.1.254").toBytes()),
                null);

        if (ArpGet(arp, entry) < 0) {
            System.out.println("ERROR");
        }
        System.out.println(entry.getArpHa());
        ArpClose(arp);
    }
}
