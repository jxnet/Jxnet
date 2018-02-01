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

package com.ardikars.jxnet;

import com.ardikars.jxnet.util.Platforms;

import java.io.File;

public class DynamicLibrary implements Library.Loader {

    @Override
    public void load() {
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
                if (!new File("C:\\Windows\\System32\\wpcap.dll").exists()) {
                    if (Platforms.is64Bit()) {
                        Library.loadLibrary("/dynamic/windows/lib/x64/jxnet.dll");
                    } else {
                        Library.loadLibrary("/dynamic/windows/lib/x86/jxnet.dll");
                    }
                } else {
                    if (new File("C:\\Windows\\System32\\Npcap\\wpcap.dll").exists()) {
                        throw new UnsatisfiedLinkError("Npcap is not installed yet.");
                    } else {
                        throw new UnsatisfiedLinkError("Npcap is installed, but with no Winpcap supported mode.");
                    }
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
