/**
 * Copyright (C) 2017-2018  Ardika Rommy Sanjaya <contact@ardikars.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ardikars.jxnet.util;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class Platforms {

    private static final String OS_ARCH = "os.arch";
    private static final String OS_NAME = "os.name";
    private static final String JAVA_VM_NAME = "java.vm.name";

    public enum Name {
        WINDOWS, LINUX, ANDROID, FREEBSD, DARWIN, UNKNOWN;
    }

    public enum Architecture {
        _32_BIT, _64_BIT;
    }

    private Platforms() {

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

    public static final boolean isFreeBsd() {
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
        return System.getProperty(OS_ARCH).toLowerCase().trim().startsWith("arm");
    }

    public static final boolean isIntel() {
        final String arch = System.getProperty(OS_ARCH).toLowerCase().trim();
        return arch.startsWith("x86") || arch.startsWith("x64");
    }

    public static final boolean isAmd() {
        return System.getProperty(OS_ARCH).toLowerCase().trim().startsWith("amd");
    }

    /**
     * Get CPU Version
     * @return CPU Version
     */
    public static final String getVersion() {
        final String version = System.getProperty("os.version");
        if (Character.isDigit(version.charAt(version.indexOf('v') + 1))) {
            return String.valueOf("v" + version.charAt(version.indexOf('v') + 1));
        }
        return Strings.EMPTY;
    }

    static {
        final String osName = System.getProperty(OS_NAME).toUpperCase().trim();
        final String osArch = System.getProperty(OS_ARCH).toLowerCase().trim();
        if (osName.startsWith("LINUX")) {
            if ("DALVIK".equals(System.getProperty(JAVA_VM_NAME).toUpperCase().trim())) {
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
