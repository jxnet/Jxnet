package capture;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.packet.PacketListener;

import static com.ardikars.jxnet.Jxnet.*;

/**
 * Created by root on 6/27/17.
 */
public class CaptureWithFilter {

    public static void main(String[] args) {
        StringBuilder errbuf = new StringBuilder();
        PcapIf source = SelectNetowrkInterface(errbuf);
        if (source == null) {
            System.err.println(errbuf.toString());
            exit(-1);
        }
        Inet4Address netmask = Inet4Address.valueOf("255.255.255.0");
        for (PcapAddr addr : source.getAddresses()) {
            if (addr.getNetmask().getSaFamily() == SockAddr.Family.AF_INET) {
                netmask = Inet4Address.valueOf(addr.getNetmask().getData());
                System.out.println(netmask);
            }
        }

        Pcap handle = PcapOpenLive(source, 1500, PromiscuousMode.PRIMISCUOUS, 2000, errbuf);
        if (handle == null) {
            System.err.println(errbuf.toString());
            exit(-2);
        }

        PacketListener.Map<String> callback = (arg, pktHdr, packets) -> {
            System.out.println(packets);
        };

        BpfProgram bpfProgram = new BpfProgram();
        PcapCompile(handle, bpfProgram, "icmp", BpfProgram.BpfCompileMode.OPTIMIZE, netmask);
        PcapSetFilter(handle, bpfProgram);
        PacketListener.loop(handle, 10, callback, "");
        PcapClose(handle);
    }

    public static void exit(int status) {
        System.exit(status);
    }

}
