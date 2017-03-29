package com.ardikars.test;

import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.*;

import static com.ardikars.jxnet.Jxnet.*;

public class PcapDatalink {

    @org.junit.Test
    public void run() {
        Pcap handler = AllTests.openHandle(); // Exception already thrown

        int datalink = PcapDataLink(handler);
        String linkName = PcapDataLinkValToName(datalink);
        String linkDesc = PcapDataLinkValToDescription(datalink);

        System.out.println("Datalink : " + datalink);
        System.out.println("Set Datalink to " + datalink + " : "
                + ((PcapSetDataLink(handler, datalink) == 0) ? true : false));
        System.out.println("Datalink Name " + linkName);
        System.out.println("Datalink Desc : " + linkDesc);
        System.out.println("Link name to value : " + PcapDataLinkNameToVal(linkName));
    }

}
