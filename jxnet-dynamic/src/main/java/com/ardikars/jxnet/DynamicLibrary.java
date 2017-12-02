package com.ardikars.jxnet;

import com.ardikars.jxnet.util.Platforms;

public class DynamicLibrary implements Library.Loader {

    @Override
    public void load() throws UnsatisfiedLinkError {
        switch (Platforms.getName()) {
            case LINUX:
                if (Platforms.isArm()) {
                    if (Platforms.getVersion().equals("v7") || Platforms.getVersion().equals("v6")) {
                        Library.loadLibrary("/dynamic/linux/lib/arm32/libjxnet.so");
                    }
                } else {
                    if (Platforms.is64Bit()) {
                        Library.loadLibrary("/dynamic/linux/lib/x64/libjxnet.so");
                    } else {
                        Library.loadLibrary("/dynamic/linux/lib/x86/libjxnet.so");
                    }
                }
                break;
            case WINDOWS:
                if (Platforms.is64Bit()) {
                    Library.loadLibrary("/dynamic/windows/lib/x64/jxnet.dll");
                } else {
                    Library.loadLibrary("/dynamic/windows/lib/x86/jxnet.dll");
                }
                break;
            case FREEBSD:
                if (Platforms.is64Bit()) {
                    Library.loadLibrary("/dynamic/freebsd/lib/x64/libjxnet.so");
                } else {
                    Library.loadLibrary("/dynamic/freebsd/lib/x86/libjxnet.so");
                }
                break;
            case DARWIN:
                if (Platforms.is64Bit()) {
                    Library.loadLibrary("/dynamic/darwin/lib/x64/libjxnet.dylib");
                } else {
                    Library.loadLibrary("/dynamic/darwin/lib/x86/libjxnet.dylib");
                }
                break;
            default:
                throw new UnsatisfiedLinkError("Not supported platform.");
        }
    }

}
