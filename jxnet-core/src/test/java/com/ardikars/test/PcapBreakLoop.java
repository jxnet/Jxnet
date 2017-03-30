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

            if (Jxnet.PcapLoop(handler, -1, AllTests.handler, null) !=0) {
                System.err.println(Jxnet.PcapGetErr(handler));
            }

        }

    }

    @org.junit.Test
    public void run() {

        Pcap handler = AllTests.openHandle(); // Exception already thrown

        Thread thread = new MyThread(handler);
        thread.start();
        for (int i=0; i<AllTests.maxIteration; i++) {
            System.out.println("i = " + i);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == (AllTests.maxIteration/2)) {
                System.out.println("Break loop.");
                Jxnet.PcapBreakLoop(handler);
            }
        }
        Jxnet.PcapClose(handler);
    }
}
