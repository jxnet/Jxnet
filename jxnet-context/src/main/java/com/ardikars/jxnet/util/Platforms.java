package com.ardikars.jxnet.util;

public class Platforms {

    public enum Name {
        WINDOWS, LINUX, ANDROID, FREEBSD, DARWIN, UNKNOWN;
    }

    public enum Architecture {
        _32_BIT, _64_BIT;
    }

    private static Name name;
    private static Architecture architecture;

    public static Name getName() {
        return name;
    }

    public static Architecture getArchitecture() {
        return architecture;
    }

    public static final boolean isWindows() {
        return name == Name.WINDOWS;
    }

    public static final boolean isLinux() {
        return name == Name.LINUX;
    }

    public static final boolean isAndroid() {
        return name == Name.ANDROID;
    }

    public static final boolean isFreeBSD() {
        return name == Name.FREEBSD;
    }

    public static final boolean isDarwin() {
        return name == Name.DARWIN;
    }

    public static final boolean is32Bit() {
        return architecture == Architecture._32_BIT;
    }

    public static final boolean is64Bit() {
        return architecture == Architecture._64_BIT;
    }

    public static final boolean isArm() {
        return (System.getProperty("os.arch").toLowerCase().trim().startsWith("arm"));
    }

    public static final boolean isIntel() {
        String arch = System.getProperty("os.arch").toLowerCase().trim();
        return (arch.startsWith("x86") || arch.startsWith("x64"));
    }

    public static final boolean isAmd() {
        return System.getProperty("os.arch").toLowerCase().trim().startsWith("amd");
    }

    /**
     * Get CPU Version
     * @return CPU Version
     */
    public static final String getVersion() {
        String version = System.getProperty("os.version");
        if (Character.isDigit(version.charAt(version.indexOf("v") + 1))) {
            return String.valueOf("v" + version.charAt(version.indexOf("v") + 1));
        }
        return null;
    }

    static {
        String osName = System.getProperty("os.name").toUpperCase().trim();
        String osArch = System.getProperty("os.arch").toLowerCase().trim();
        if (osName.startsWith("LINUX")) {
            if (new String("DALVIK").equals(System.getProperty("java.vm.name").toUpperCase().trim())) {
                name = Name.ANDROID;
            } else {
                name = Name.LINUX;
            }
        } else if (osName.startsWith("WINDOWS")) {
            name = Name.WINDOWS;
        } else if (osName.startsWith("FREEBSD")) {
            name = Name.FREEBSD;
        } else if (osName.startsWith("MAC OS")) {
            name = Name.DARWIN;
        } else {
            name = Name.UNKNOWN;
        }

        if ("i386".equals(osArch) || "i686".equals(osArch) || "i586".equals(osArch)) {
            architecture = Architecture._32_BIT;
        } else if ("x86_64".equals(osArch) || "amd64".equals(osArch) || "x64".equals(osArch)) {
            architecture = Architecture._64_BIT;
        }
    }

}
