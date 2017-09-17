package com.ardikars.test;

import com.ardikars.jxnet.util.Platforms;

public class PlatformsTest {

    @org.junit.Test
    public void run() {
        System.out.println("+=============+");
        System.out.println("NAME          : " + Platforms.getName());
        System.out.println("ARCHITECTURE  : " + Platforms.getArchitecture());
        System.out.println("+=============+");
        System.out.println("WINDOWS       : " + Platforms.isWindows());
        System.out.println("LINUX         : " + Platforms.isLinux());
        System.out.println("Android       : " + Platforms.isAndroid());
        System.out.println("FreeBSD       : " + Platforms.isFreeBSD());
        System.out.println("+=============+");
        System.out.println("INTEL         : " + Platforms.isIntel());
        System.out.println("AMD           : " + Platforms.isAmd());
        System.out.println("ARM           : " + Platforms.isArm());
        System.out.println("+=============+");
        System.out.println("Version       : " + Platforms.getVersion());
        System.out.println("+=============+");
    }

}
