package com.ardikars.test;


import com.ardikars.jxnet.*;

public class Test {

    public static void main(String[] args) {
        print(Jxnet.class);
        print(BpfProgram.class);
        print(Preconditions.class);
        print(File.class);
        print(MacAddress.class);
        print(PcapAddr.class);
        print(PcapIf.class);
        print(SockAddr.class);
        print(PcapDumper.class);
        print(PcapPktHdr.class);
        print(PcapStat.class);
    }

    public static void print(Class clazz) {
        System.out.println("case " + (clazz.getName().hashCode() & 0xffffffff) +": \t\n#ifdef DEBUG\n"+ "puts(\"Initializing " + clazz.getName()+" IDs\")" +"\n#endif\n\tbreak;");
    }

}
