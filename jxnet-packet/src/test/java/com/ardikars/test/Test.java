package com.ardikars.test;

import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.Inet4Address;
import com.ardikars.jxnet.MacAddress;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.arp.ARP;
import com.ardikars.jxnet.packet.arp.ARPOperationCode;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.packet.ethernet.ProtocolType;

public class Test {

    public static void main(String[] args) {
        Ethernet ethernet = new Ethernet()
                .setDestinationMacAddress(MacAddress.BROADCAST)
                .setSourceMacAddress(MacAddress.DUMMY)
                .setEthernetType(ProtocolType.ARP);
        ARP arp = new ARP()
                .setHardwareType(DataLinkType.EN10MB)
                .setProtocolType(ProtocolType.IPV4)
                .setHardwareAddressLength((byte) 6)
                .setProtocolAddressLength((byte) 4)
                .setOperationCode(ARPOperationCode.ARP_REQUEST)
                .setSenderHardwareAddress(MacAddress.DUMMY)
                .setSenderProtocolAddress(Inet4Address.valueOf("192.168.100.3"))
                .setTargetHardwareAddress(MacAddress.ZERO)
                .setTargetProtocolAddress(Inet4Address.ZERO);

        Packet packet = Packet.PacketBuilder().add(ethernet).add(arp).build();
        System.out.println(packet);
    }

}
