package send;

import static com.ardikars.jxnet.Jxnet.*;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.PacketHelper;
import com.ardikars.jxnet.packet.arp.ARP;
import com.ardikars.jxnet.packet.arp.ARPOperationCode;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.packet.ProtocolType;
import com.ardikars.jxnet.util.AddrUtils;
import com.ardikars.jxnet.util.BufferUtils;
import com.ardikars.jxnet.util.Platforms;

import java.io.IOException;
import java.util.Map;


/**
 * Created by root on 6/26/17.
 */
public class ArpReq {

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

        System.out.println("Address     : " + address);
        System.out.println("Mac Address : " + macAddress);
        System.out.println("Gateway     : " + gateway);
        System.out.print("Gateway Mac : ");
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
                byte[] pkt = packet.toBytes();
                Jxnet.PcapSendPacket(handle, BufferUtils.toDirectByteBuffer(pkt), pkt.length);
                Map<Class, Packet> packetMap = PacketHelper.next(handle, pktHdr);
                ARP arpC = (ARP) packetMap.get(ARP.class);
                if (arpC != null) {
                    gwMac = arpC.getSenderHardwareAddress();
                    System.out.println(gwMac);
                    break;
                }
            }
            try {
                Thread.sleep(3600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        PcapClose(handle);

    }

    public static void exit(int status) {
        System.exit(status);
    }

}
