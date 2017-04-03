package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.packet.PacketHandler;
import com.ardikars.jxnet.packet.PacketHelper;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.util.FormatUtils;
import org.junit.Test;

public class Ethernet_ARP {

    private static int index = 0;

    @Test
    public void run() {
        String[] hexStream = new String[] {
                "14cc20ccb9ecb827eb9a9c5f08060001080006040001b827eb9a9c5fc0a80196000000000000c0a801fe",
                "b827eb9a9c5f14cc20ccb9ec0806000108000604000214cc20ccb9ecc0a801feb827eb9a9c5fc0a801964445045f7375620b5f676f6f676c65636173",
                "ffffffffffffb827eb9a9c5f08060001080006040001b827eb9a9c5fc0a80196000000000000c0a80197",
                "b827eb9a9c5f14dda95e8bbc0806000108000604000214dda95e8bbcc0a80197b827eb9a9c5fc0a80196e25dfb605018f926679a0000150303001a00",
                "ffffffffffff14dda95e8bbc0806000108000604000114dda95e8bbcc0a80197000000000000c0a80196e00000fc0001000000000000204648464145",
                "14dda95e8bbcb827eb9a9c5f08060001080006040002b827eb9a9c5fc0a8019614dda95e8bbcc0a80197",
                "ffffffffffffb827eb9a9c5f08060001080006040001b827eb9a9c5fc0a80196000000000000c0a80164",
                "b827eb9a9c5fe8de27452f1a08060001080006040002e8de27452f1ac0a80164b827eb9a9c5fc0a8019698f601c9f0100913a4e600000101080a007f"
        };

        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = Jxnet.PcapOpenOffline("../sample-capture/eth_arp_pcap.pcapng", errbuf);
        if (pcap == null) {
            System.err.println(errbuf.toString());
            return;
        }

        PacketHandler<String> callback = (arg, pktHdr, packets) -> {
            Ethernet eth = (Ethernet) packets.get(Ethernet.class);
            if (eth != null) {
                if (!FormatUtils.toHexString(eth.toBytes()).equals(hexStream[index])) {
                    System.out.println(index+": "+FormatUtils.toHexString(eth.toBytes()));
                }
                index++;
            }
        };

        PacketHelper.loop(pcap, -1, callback, null);

        Jxnet.PcapClose(pcap);

    }

}
