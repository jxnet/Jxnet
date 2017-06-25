package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.PcapIf;

public class Test {

    public static void main(String[] args) {
        StringBuilder errbuf = new StringBuilder();
        PcapIf pcapIf = Jxnet.SelectNetowrkInterface(errbuf);
        System.out.println(pcapIf);


    }

}
