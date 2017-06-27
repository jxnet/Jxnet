package capture;

import static com.ardikars.jxnet.Jxnet.*;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.packet.PacketHandler;
import com.ardikars.jxnet.packet.PacketHelper;
import com.ardikars.jxnet.util.Platforms;

/**
 * Created by root on 6/26/17.
 */
public class CaptureWithCallback {

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

        PacketHandler<String> callback = (arg, pktHdr, packets) -> {
            System.out.println(arg);
            System.out.println(pktHdr);;
            System.out.println(packets);
        };

        PacketHelper.loop(handle, 10, callback, "blabla");

        PcapStat stat = new PcapStat();
        PcapStats(handle, stat);
        System.out.println(stat);
        PcapClose(handle);
    }

    public static void exit(int status) {
        System.exit(status);
    }

}
