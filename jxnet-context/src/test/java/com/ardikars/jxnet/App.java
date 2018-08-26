package com.ardikars.jxnet;

import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.util.Hexs;
import com.ardikars.common.util.Loader;
import com.ardikars.jxnet.util.DefaultLibraryLoader;

import java.nio.ByteBuffer;
import java.util.Set;

public class App {

    static class Initializer implements ApplicationInitializer<String> {


        @Override
        public Loader<Void> initialize(String arguments) {
            return new DefaultLibraryLoader();
        }
    }

    public static void main(String[] args) {
        StringBuilder errbuf = new StringBuilder();
        Application.run("Test", "0.1", Initializer.class,
                new Pcap.Builder().pcapType(Pcap.PcapType.LIVE)
                        .source("en0")
                        .immediateMode(ImmediateMode.IMMEDIATE)
                        .errbuf(errbuf),
                new BpfProgram.Builder().bpfCompileMode(BpfProgram.BpfCompileMode.OPTIMIZE)
                        .filter("tcp")
                        .netmask(Inet4Address.valueOf("255.255.255.0").toInt()),
                "Yoo"
        );
        Context context = Application.getApplicationContext();
        context.pcapLoop(-1, new PcapHandler<String>() {
            @Override
            public void nextPacket(String user, PcapPktHdr h, ByteBuffer byteBuffer) {
                byte[] buffer = new byte[byteBuffer.capacity()];
                byteBuffer.get(buffer, 0, buffer.length);
                System.out.println("Header: " + h);
                System.out.println("Buffer: \n" + Hexs.toPrettyHexDump(buffer));
                System.out.println(user);
            }
        }, "***********************");
        context.pcapClose();
    }
}
