package com.ardikars.jxnet.packet;

import com.ardikars.common.util.Platforms;
import jnr.ffi.LibraryLoader;
import jnr.ffi.Platform;
import jnr.ffi.Runtime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PacketTest {

    NativeMappings mappings = LibraryLoader.create(NativeMappings.class).load("Packet");

    @Test
    public void test01_PacketGetVersion() {
        if (Platforms.isWindows()) {
            System.out.println(mappings.PacketGetVersion());
        }
        assert true;
    }

    @Test
    public void test02_PacketGetDriverVersion() {
        if (Platforms.isWindows()) {
            System.out.println(mappings.PacketGetDriverVersion());
        }
        assert true;
    }

    @Test
    public void g9() {
        assert true;
    }
}
