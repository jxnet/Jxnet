import static com.ardikars.jxnet.Jxnet.*;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.util.Platforms;

import java.nio.ByteBuffer;


/**
 * Created by root on 6/26/17.
 */
public class Capture1 {

    public static void main(String[] args) {

        ApplicationInitializer initializer = new ApplicationInitializer() {
            @Override
            public void initialize(Application.Context context) {
                context.addLibrary(new DynamicLibrary());
                context.addLibrary(new StaticLibrary());
            }
        };

        Application.run("Capture1", "1", initializer);

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
        ByteBuffer buf;
        PcapPktHdr pktHdr = new PcapPktHdr();
        for (int i=0; i<10; i++) {
            buf = PcapNext(handle, pktHdr);
            System.out.println(buf);
            System.out.println(pktHdr);
        }
        PcapStat stat = new PcapStat();
        PcapStats(handle, stat);
        System.out.println(stat);
        PcapClose(handle);
    }

    public static void exit(int status)  {
        System.exit(status);
    }

}
