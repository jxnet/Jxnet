package com.ardikars.jxnet;

import com.ardikars.jxnet.util.Platforms;

public class StaticLibrary implements Library.Loader {

    @Override
    public void load() throws UnsatisfiedLinkError {
        switch (Platforms.getName()) {
            case LINUX:
                if (Platforms.isArm()) {
                    if (Platforms.getVersion().equals("v7") || Platforms.getVersion().equals("v6")) {
                        Library.loadLibrary("/static/linux/lib/arm32/libjxnet.so");
                    }
                } else {
                    if (Platforms.is64Bit()) {
                        Library.loadLibrary("/static/linux/lib/x64/libjxnet.so");
                    } else {
                        Library.loadLibrary("/static/linux/lib/x86/libjxnet.so");
                    }
                }
                break;
            case FREEBSD:
                if (Platforms.is64Bit()) {
                    Library.loadLibrary("/static/freebsd/lib/x64/libjxnet.so");
                } else {
                    Library.loadLibrary("/static/freebsd/lib/x86/libjxnet.so");
                }
                break;
            case DARWIN:
                if (Platforms.is64Bit()) {
                    Library.loadLibrary("/static/darwin/lib/x64/libjxnet.dylib");
                } else {
                    Library.loadLibrary("/static/darwin/lib/x86/libjxnet.dylib");
                }
                break;
            default:
                throw new UnsatisfiedLinkError("Not supported platform.");

        }
    }

}
