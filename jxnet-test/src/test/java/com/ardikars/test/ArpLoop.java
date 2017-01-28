package com.ardikars.test;

import static com.ardikars.jxnet.Jxnet.*;
import com.ardikars.jxnet.Arp;
import com.ardikars.jxnet.ArpEntry;
import com.ardikars.jxnet.ArpHandler;
import com.ardikars.jxnet.MacAddress;
import org.junit.Test;

public class ArpLoop {

    @Test
    public void run() {
        Arp arp = ArpOpen();
        ArpHandler<String> callback = new ArpHandler<String>() {
            @Override
            public int nextArpEntry(ArpEntry arpEntry, String arg) {
                System.out.println(arpEntry);
                return 0;
            }
        };
        int i = ArpLoop(arp, callback, "myarg");
        arp = ArpClose(arp);
    }
}
