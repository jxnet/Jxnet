package com.ardikars.test;

import com.ardikars.jxnet.Inet6Address;
import com.ardikars.jxnet.MacAddress;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.packet.ethernet.ProtocolType;
import com.ardikars.jxnet.packet.icmp.ICMPv6;
import com.ardikars.jxnet.packet.icmp.icmpv6.ICMPv6NeighborAdvertisement;
import com.ardikars.jxnet.packet.icmp.icmpv6.ICMPv6NeighborSolicitation;
import com.ardikars.jxnet.packet.ip.IPProtocolType;
import com.ardikars.jxnet.packet.ip.IPv6;

import java.util.Map;

/**
 * Created by root on 4/3/17.
 */
public class MainTest {

    public static void main(String[] args) {

        try {
            Class.forName("com.ardikars.jxnet.packet.icmp.ICMPTypeAndCode");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Ethernet ethernet = new Ethernet()
                .setDestinationMacAddress(MacAddress.BROADCAST)
                .setSourceMacAddress(MacAddress.DUMMY)
                .setEthernetType(ProtocolType.IPV6);

        IPv6 iPv6 = new IPv6()
                .setNextHeader(IPProtocolType.IPV6_ICMP)
                .setSourceAddress(Inet6Address.valueOf("fe80::c001:2ff:fe40:0"))
                .setDestinationAddress(Inet6Address.valueOf("ff02::1:ffe4:0"))
                .setHopLimit((byte)0xff);
        ICMPv6 icmPv6 = (ICMPv6) new ICMPv6()
                .setTypeAndCode(ICMPv6NeighborAdvertisement.NEIGHBOR_ADVERTISEMENT);

        Packet packet = Packet.PacketBuilder()
                .add(ethernet)
                .add(iPv6)
                .add(icmPv6).build();

        System.out.println(MacAddress.BROADCAST);
    }
}
