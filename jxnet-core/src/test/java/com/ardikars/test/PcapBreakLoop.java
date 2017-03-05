package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapPktHdr;
import org.junit.*;

import java.nio.ByteBuffer;
import java.sql.Time;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class PcapBreakLoop {

    class MyThread extends Thread {

        private volatile Pcap handler;

        public MyThread(Pcap handler) {
            this.handler = handler;
        }

        @Override
        public void run() {

            PcapHandler<String> callback = (t, pph, bb) -> {
                //System.out.println("User   : " + t);
                System.out.println("PktHdr : " + pph);
                System.out.println("Data   : " + bb);
            };

            if (Jxnet.PcapLoop(handler, -1, callback, null) !=0) {
                System.err.println("Gagal");
            }

        }

    }

    @org.junit.Test
    public void run() {

        StringBuilder errbuf = new StringBuilder();
        String dev = AllTests.deviceName;

        Pcap handler = Jxnet.PcapOpenLive(dev, AllTests.snaplen, AllTests.promisc, AllTests.to_ms, errbuf);

        Thread thread = new MyThread(handler);
        thread.start();
        for (int i=0; i<10; i++) {
            System.out.println("i = " + i);
            try {
                Thread.sleep(3600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == 5 ) {
                Jxnet.PcapBreakLoop(handler);
            }
        }
        Jxnet.PcapClose(handler);
    }
}
