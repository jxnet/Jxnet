package com.ardikars.test;

import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.*;

import static com.ardikars.jxnet.Jxnet.*;

public class PcapDatalink {

    @org.junit.Test
    public void run() {
        StringBuilder errbuf = new StringBuilder();
        String dev = AllTests.deviceName;
        Pcap handler = PcapOpenLive(dev, AllTests.snaplen, AllTests.promisc, AllTests.to_ms, errbuf);
        if (handler == null) {
            throw new PcapCloseException(errbuf.toString());
        }
        int datalink = PcapDatalink(handler);
        String linkName = PcapDatalinkValToName(datalink);
        String linkDesc = PcapDatalinkValToDescription(datalink);

        System.out.println("Datalink : " + datalink);
        System.out.println("Set Datalink to " + datalink + " : "
                + ((PcapSetDatalink(handler, datalink) == 0) ? true : false));
        System.out.println("Datalink Name " + linkName);
        System.out.println("Datalink Desc : " + linkDesc);
        System.out.println("Link name to value : " + PcapDatalinkNameToVal(linkName));
    }

}
