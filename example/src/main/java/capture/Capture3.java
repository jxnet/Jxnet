package capture;

import static com.ardikars.jxnet.Jxnet.*;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.PacketHelper;
import com.ardikars.jxnet.packet.ip.IPv4;
import com.ardikars.jxnet.util.Platforms;

import java.nio.ByteBuffer;
import java.util.Map;


/**
 * Created by root on 6/26/17.
 */
public class Capture3 {

    public static void main(String[] args) {
        StringBuilder errbuf = new StringBuilder();
        PcapIf source = SelectNetowrkInterface(errbuf);
        if (source == null) {
            System.err.println(errbuf.toString());
            exit(-1);
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
        Map<Class, Packet> packet;
        PcapPktHdr pktHdr = new PcapPktHdr();
        for (int i=0; i<10; i++) {
            packet = PacketHelper.next(handle, pktHdr);
            System.out.println(packet.get(IPv4.class));
        }
        PcapStat stat = new PcapStat();
        PcapStats(handle, stat);
        System.out.println(stat);
        PcapClose(handle);
    }

    public static void exit(int status) {
        System.exit(status);
    }

}
