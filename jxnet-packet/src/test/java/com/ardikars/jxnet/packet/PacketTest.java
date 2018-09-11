package com.ardikars.jxnet.packet;

import com.ardikars.common.net.MacAddress;
import jnr.ffi.LibraryLoader;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.logging.Logger;

@RunWith(JUnit4.class)
public class PacketTest {

    NativeMappings mappings = LibraryLoader.create(NativeMappings.class).load("Packet");
    Runtime runtime = Runtime.getRuntime(mappings);

    public static void printMacAddress(Struct.Unsigned8[] mac) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i].get(), (i < mac.length - 1) ? "-" : ""));
        }
        System.out.println(sb.toString());
    }

    @Test
    public void myTest() {
        String source = "\\Device\\NPF_{53152A2F-39F7-458E-BD58-24D17099256A}";
        Pointer lpAdapter = mappings.PacketOpenAdapter(source);
        NativeMappings.Structures.PPACKET_OID_DATA oid_data = new NativeMappings.Structures.PPACKET_OID_DATA(runtime, 6);
        oid_data.Oid.set(0x01010102L);
        oid_data.Length.set(6L);
        printMacAddress(oid_data.Data);
        mappings.PacketRequest(lpAdapter, false, oid_data);
        printMacAddress(oid_data.Data);
        mappings.PacketCloseAdapter(lpAdapter);
    }

    @Test
    public void test01_PacketGetVersion() {
        System.out.println(mappings.PacketGetVersion());
    }

    @Test
    public void test02_PacketGetDriverVersion() {
        System.out.println(mappings.PacketGetDriverVersion());
    }

    public void g9() {
        NativeMappings.Structures.bpf_insn bpf_insn = new NativeMappings.Structures.bpf_insn(runtime);
        NativeMappings.Structures.bpf_program bpf_program = new NativeMappings.Structures.bpf_program(runtime);

    }
}
