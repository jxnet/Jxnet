import com.ardikars.jxnet.*;
import com.ardikars.jxnet.util.Platforms;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class NetworkGateway {

    private static final StringBuilder ERRBUF = new StringBuilder();
    private static final int SNAPLEN = 65535;
    private static final PromiscuousMode PROMISC = PromiscuousMode.PRIMISCUOUS;
    private static final int TIMEOUT = 2000;
    private static final ImmediateMode IMMEDIATE = ImmediateMode.IMMEDIATE;

    private static Pcap handle;
    private static BpfProgram bpf;

    public static void main(String[] args) {
        gatewayMacAddress();
    }

    private static Pcap open() {
        if (handle == null) {
            PcapIf source = Jxnet.SelectNetowrkInterface(ERRBUF);
            handle = Jxnet.PcapCreate(source, ERRBUF);
            if (handle == null) {
                showErrorMessageAndExit(ERRBUF.toString());
            }
            if (Jxnet.PcapSetSnaplen(handle, SNAPLEN) != Jxnet.OK) {
                showErrorMessageAndExit(ERRBUF.toString());
            }
            if (Jxnet.PcapSetPromisc(handle, PROMISC) != Jxnet.OK) {
                showErrorMessageAndExit(ERRBUF.toString());
            }
            if (Jxnet.PcapSetTimeout(handle, TIMEOUT) != Jxnet.OK) {
                showErrorMessageAndExit(ERRBUF.toString());
            }
            if (!Platforms.isWindows()) {
                if (Jxnet.PcapSetImmediateMode(handle, IMMEDIATE) != Jxnet.OK) {
                    showErrorMessageAndExit("PcapSetImmediateMode: ");
                }
            }
            if (Jxnet.PcapActivate(handle) != Jxnet.OK) {
                showErrorMessageAndExit("PcapActivate: ");
            }
        }
        return handle;
    }

    private static BpfProgram bpf() {
        if (bpf == null) {
            bpf = new BpfProgram();
        }
        return bpf;
    }

    private static void filter() {
        try {
            java.net.InetAddress pingAddr = java.net.InetAddress.getByName("ardikars.com");
            bpf = bpf();
            handle = open();
            if (Jxnet.PcapCompile(handle, bpf, "tcp and dst host " + pingAddr.getHostAddress(),
                    1, Inet4Address.valueOf("255.255.255.0").toInt()) != Jxnet.OK) {
                showErrorMessageAndExit("PcapCompile: ");
            }
            if (Jxnet.PcapSetFilter(handle, bpf) != Jxnet.OK) {
                showErrorMessageAndExit("PcapSetFilter: ");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void showErrorMessageAndExit(String message) {
        System.err.println(message);
        if (handle != null) {
            Jxnet.PcapClose(handle);
        }
        System.exit(-1);
    }

    private static MacAddress gatewayMacAddress() {
        open();
        bpf();
        filter();
        PcapPktHdr pktHdr = new PcapPktHdr();
        ByteBuffer pkt;
        while (true) {
            try {
                new URL("http://ardikars.com").openStream().close();
                pkt = Jxnet.PcapNext(handle, pktHdr);
                System.out.printf(pkt.toString());
                if (pkt != null) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        return null;
    }

}
