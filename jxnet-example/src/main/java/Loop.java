import com.ardikars.jxnet.*;

import java.nio.ByteBuffer;

public class Loop {

    private static StringBuilder errbuf = new StringBuilder();

    private static final String COUNT_KEY = Loop.class.getName() + ".count";
    private static final int COUNT = Integer.getInteger(COUNT_KEY, 5);

    private static final String READ_TIMEOUT_KEY = Loop.class.getName() + ".readTimeout";
    private static final int READ_TIMEOUT = Integer.getInteger(READ_TIMEOUT_KEY, 10); // [ms]

    private static final String SNAPLEN_KEY = Loop.class.getName() + ".snaplen";
    private static final int SNAPLEN = Integer.getInteger(SNAPLEN_KEY, 65535); // [bytes]

    private Loop() {}

    public static void main(String[] args) {

//        Application.getInstance().enableDevelopmentMode();

        errbuf.setLength(0);

        String filter = args.length != 0 ? args[0] : "";

        System.out.println(COUNT_KEY + ": " + COUNT);
        System.out.println(READ_TIMEOUT_KEY + ": " + READ_TIMEOUT);
        System.out.println(SNAPLEN_KEY + ": " + SNAPLEN);
        System.out.println("\n");

        PcapIf nif = Jxnet.SelectNetowrkInterface(errbuf);

        if (nif == null) {
            System.err.println(errbuf.toString());
            return;
        }

        System.out.println(nif.getName() + "(" + nif.getDescription() + ")");

        final Pcap handle = Jxnet.PcapOpenLive(nif, SNAPLEN, PromiscuousMode.PRIMISCUOUS, READ_TIMEOUT, errbuf);

        if (handle == null) {
            System.err.println(errbuf.toString());
            return;
        }

        BpfProgram fp = null;
        if (filter.length() != 0) {

            fp = new BpfProgram();
            Jxnet.PcapCompile(handle, fp, filter,
                    BpfProgram.BpfCompileMode.OPTIMIZE,
                    Inet4Address.valueOf("255.255.255.0"));
            Jxnet.PcapSetFilter(handle, fp);
        }

        PcapHandler<String> callback = new PcapHandler<String>() {
            @Override
            public void nextPacket(String user, PcapPktHdr h, ByteBuffer bytes) {
                System.out.println("Argument   : " + user);
                System.out.println("Header     : " + h);
                System.out.println("Raw Packet : " + bytes);
            }
        };

        Jxnet.PcapLoop(handle, COUNT, callback, "");

        PcapStat ps = new PcapStat();
        Jxnet.PcapStats(handle, ps);
        System.out.println("ps_recv: " + ps.getPsRecv());
        System.out.println("ps_drop: " + ps.getPsDrop());
        System.out.println("ps_ifdrop: " + ps.getPsIfdrop());
//        if (Platform.isWindows()) {
//            System.out.println("bs_capt: " + ps.get());
//        }
        if (fp != null) {
            Jxnet.PcapFreeCode(fp);
        }
        Jxnet.PcapClose(handle);
    }

}
