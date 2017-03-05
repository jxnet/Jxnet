package com.ardikars.test;

import com.ardikars.jxnet.Arp;
import org.junit.Test;

import static  com.ardikars.jxnet.Jxnet.*;
import static com.ardikars.jxnet.Arp.*;

public class ArpOpen {

    @Test
    public void run() {
        Arp arp = ArpOpen();
        System.out.println(arp);
        arp = ArpClose(arp);
        System.out.println(arp);
        arp = ArpOpen();
        System.out.println(arp);
    }

}
