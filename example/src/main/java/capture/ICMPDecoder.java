package capture;

import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.packet.PacketDecoder;
import com.ardikars.jxnet.packet.icmp.ICMPv4;

/**
 * Created by root on 6/26/17.
 */
public class ICMPDecoder extends PacketDecoder<ICMPv4> {

    @Override
    protected void read(Pcap pcap, PcapPktHdr pktHdr, ICMPv4 packet) {
        System.out.println(pktHdr);
        System.out.println(packet);
    }

}
