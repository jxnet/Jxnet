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

public class StaticLibrary implements Library.Loader {

    @Override
    public void load() {
        switch (Platforms.getName()) {
            case LINUX:
                if (Platforms.isArm()) {
                    if ("v7".equals(Platforms.getVersion()) || "v6".equals(Platforms.getVersion())) {
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
