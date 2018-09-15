package com.ardikars.jxnet.packet;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Runtime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PacketTest {

    NativeMappings mappings = LibraryLoader.create(NativeMappings.class).load("Adapter");
    Runtime runtime = Runtime.getRuntime(mappings);

    @Test
    public void test01_PacketGetVersion() {
        System.out.println(mappings.PacketGetVersion());
    }

    @Test
    public void test02_PacketGetDriverVersion() {
        System.out.println(mappings.PacketGetDriverVersion());
    }

    @Test
    public void g9() {
        assert true;
    }
}
