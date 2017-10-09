package traceroute;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.PacketListener;
import com.ardikars.jxnet.packet.ProtocolType;
import com.ardikars.jxnet.packet.UnknownPacket;
import com.ardikars.jxnet.packet.arp.ARP;
import com.ardikars.jxnet.packet.arp.ARPOperationCode;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.packet.icmp.ICMP;
import com.ardikars.jxnet.packet.icmp.ICMPTypeAndCode;
import com.ardikars.jxnet.packet.icmp.ICMPv4;
import com.ardikars.jxnet.packet.icmp.icmpv4.ICMPv4TimeExceeded;
import com.ardikars.jxnet.packet.ip.IPProtocolType;
import com.ardikars.jxnet.packet.ip.IPv4;
import com.ardikars.jxnet.util.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Map;

import static com.ardikars.jxnet.Jxnet.PcapCreate;
import static com.ardikars.jxnet.Jxnet.*;

public class ICMPTraceRoute {

    public static void main(String[] args) {
        StringBuilder errbuf = new StringBuilder();
        PcapIf source = SelectNetowrkInterface(errbuf);
        if (source == null) {
            System.err.println(errbuf.toString());
            exit(-1);
        }
        MacAddress macAddress = MacAddress.fromNicName(source.getName());
        Inet4Address address = null;
        for (PcapAddr pcapAddr : source.getAddresses()) {
            if (pcapAddr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
                address = Inet4Address.valueOf(pcapAddr.getAddr().getData());
            }
        }
        Inet4Address gateway = null;

        try {
            gateway = AddrUtils.GetGatewayAddress();
        } catch (IOException e) {
            e.printStackTrace();
            exit(-4);
        }

        Pcap handle = PcapCreate(source, errbuf);
        if (handle == null) {
            System.err.println(errbuf.toString());
            exit(-2);
        }
        PcapSetSnaplen(handle, 1500);
        PcapSetPromisc(handle, PromiscuousMode.PRIMISCUOUS);
        PcapSetTimeout(handle, 2000);
        if (!Platforms.isWindows()) {
            PcapSetImmediateMode(handle, ImmediateMode.IMMEDIATE);
        }
        if (PcapActivate(handle) != OK) {
            System.err.println(PcapGetErr(handle));
            PcapClose(handle);
            exit(-3);
        }

        MacAddress gwMacAddr = getGatwayHardwareAddress(macAddress, address, gateway, handle);

        if (gwMacAddr == null) {
            PcapClose(handle);
            exit(-1);
        }
        BpfProgram bpfProgram = new BpfProgram();

        java.net.InetAddress pingAddr = null;
        try {
            pingAddr = java.net.Inet4Address.getByName("ardikars.com");
            Inet4Address netp = Inet4Address.valueOf(0);
            Inet4Address maskp = Inet4Address.valueOf("255.255.255.0");
            //PcapLookupNet(source.getName(), netp, maskp, errbuf);

            PcapCompile(handle, bpfProgram,"icmp and dst host " + address.toString(),BpfProgram.BpfCompileMode.OPTIMIZE, maskp);
            PcapSetFilter(handle, bpfProgram);
        } catch (UnknownHostException e) {
            PcapClose(handle);
            exit(-1);
            e.printStackTrace();
        }

        Ethernet ethernet = new Ethernet()
                .setDestinationMacAddress(gwMacAddr)
                .setSourceMacAddress(macAddress)
                .setEthernetType(ProtocolType.IPV4);

        IPv4 iPv4 = new IPv4()
                .setHeaderLength((byte) 0x45)
                .setDiffServ((byte) 0x0)
                .setTotalLength((short) 0x003c)
                .setIdentification((short) 0x3bfe)
                .setFlags((byte) 0x0)
                .setFragmentOffset((byte) 0x0)
                .setTtl((byte) 0x1)
                .setProtocol(IPProtocolType.ICMP)
                .setSourceAddress(address)
                .setDestinationAddress(Inet4Address.valueOf(pingAddr.getAddress()));

        UnknownPacket unknownPacket = new UnknownPacket();
        unknownPacket.setData(HexUtils.parseHex("0x1560006048494a4b4c4d4e4f505152535455565758595a5b5c5d5e5f6061626364656667"));

        ICMP icmPv4 = (ICMPv4) new ICMPv4()
                .setTypeAndCode(ICMPTypeAndCode.getTypeAndCode((byte) 0x8, (byte) 0x0));

        icmPv4.setPacket(unknownPacket);
        PcapPktHdr pktHdr = new PcapPktHdr();
        Map<Class, Packet> packetMap;
        int ttl = 1;
        while (true) {
            iPv4.setTtl((byte) ttl++);
            iPv4.setPacket(icmPv4);
            ethernet.setPacket(iPv4);
            ByteBuffer buffer = ethernet.buffer();
            PcapSendPacket(handle, buffer, buffer.capacity());
            System.out.println("Send");
            packetMap = PacketListener.nextMap(handle, pktHdr);
            if (packetMap == null) {
                continue;
            } else {
                System.out.println(packetMap);
            }
            ICMPv4 icmp = (ICMPv4) packetMap.get(ICMPv4.class);
            System.out.println(icmp);
        }
    }

    public static MacAddress getGatwayHardwareAddress(MacAddress macAddress, Inet4Address address, Inet4Address gateway, Pcap handle) {
        Ethernet ethernet = new Ethernet()
                .setDestinationMacAddress(MacAddress.BROADCAST)
                .setSourceMacAddress(macAddress)
                .setEthernetType(ProtocolType.ARP);
        ARP arp = new ARP()
                .setHardwareType(DataLinkType.EN10MB)
                .setProtocolType(ProtocolType.IPV4)
                .setHardwareAddressLength((byte) MacAddress.MAC_ADDRESS_LENGTH)
                .setProtocolAddressLength((byte) Inet4Address.IPV4_ADDRESS_LENGTH)
                .setOperationCode(ARPOperationCode.ARP_REQUEST)
                .setSenderProtocolAddress(address)
                .setSenderHardwareAddress(macAddress)
                .setTargetProtocolAddress(gateway)
                .setTargetHardwareAddress(MacAddress.ZERO);
        Packet packet = null;
        PcapPktHdr pktHdr = new PcapPktHdr();
        MacAddress gwMac = null;
        for (int i=0; i<100; i++) {
            if (gwMac == null) {
                packet = Packet.PacketBuilder().add(ethernet).add(arp).build();
                ByteBuffer buffer = packet.buffer();
                Jxnet.PcapSendPacket(handle, buffer, buffer.capacity());
                Map<Class, Packet> packetMap = PacketListener.nextMap(handle, pktHdr);
                ARP arpC = (ARP) packetMap.get(ARP.class);
                if (arpC != null) {
                    gwMac = arpC.getSenderHardwareAddress();
                    break;
                }
            }
            try {
                Thread.sleep(3600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return gwMac;
    }

    public static void exit(int status) {
        System.exit(status);
    }

}
