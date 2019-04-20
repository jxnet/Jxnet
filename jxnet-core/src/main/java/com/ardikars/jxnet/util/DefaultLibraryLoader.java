/**
 * Copyright (C) 2015-2018 Jxnet
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

import com.ardikars.common.annotation.Mutable;
import com.ardikars.common.util.Callback;
import com.ardikars.common.util.InternalNativeLibrary;
import com.ardikars.common.util.Loader;
import com.ardikars.common.util.Platforms;
import com.ardikars.jxnet.exception.PlatformNotSupportedException;

import java.io.File;

/**
 * Helper class for load jxnet shared libarary from inner jar.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.1.5
 */
@Mutable
public class DefaultLibraryLoader implements Loader<Void> {

    public static final String NPCAP_DLL = "C:\\Windows\\System32\\Npcap\\wpcap.dll";
    public static final String WPCAP_DLL = "C:\\Windows\\System32\\wpcap.dll";

    public static final String LINUX_X64 = "/native/libjxnet-linux-x64.so";
    public static final String LINUX_X86 = "/native/libjxnet-linux-x86.so";
    public static final String WINDOWS_X64 = "/native/jxnet-windows-x64.dll";
    public static final String WINDOWS_X86 = "/native/jxnet-windows-x86.dll";
    public static final String DARWIN_X64 = "/native/libjxnet-darwin-x64.dylib";

    private final InternalNativeLibrary nativeLibrary = new InternalNativeLibrary();

    private static final boolean USE_SYSTEM_LIBRARY;

    /**
     * Perform load native library.
     */
    @Override
    public void load(final Callback<Void> callback) {
        doLoad(callback);
    }

    /**
     * Loadd classes then perform load native library.
     */
    @Override
    public void load(Callback<Void> callback, Class[] loadClasses) {
        for (Class clazzes : loadClasses) {
            try {
                Class.forName(clazzes.getName());
                doLoad(callback);
            } catch (ClassNotFoundException e) {
                callback.onFailure(e);
            }
        }
    }

    private void doLoad(final Callback<Void> callback) {
        if (USE_SYSTEM_LIBRARY) {
            try {
                System.loadLibrary("jxnet");
                callback.onSuccess(null);
                return;
            } catch (Exception e) {
                // use internal library
            }
        }
        switch (Platforms.getName()) {
            case LINUX:
                if (Platforms.is64Bit()) {
                    nativeLibrary.register(LINUX_X64);
                } else {
                    nativeLibrary.register(LINUX_X86);
                }
                break;
            case WINDOWS:
                File npcapFile = new File(NPCAP_DLL);
                if (npcapFile.exists())  {
                    System.load(NPCAP_DLL);
                } else {
                    File winpcapFile = new File(WPCAP_DLL);
                    if (winpcapFile.exists()) {
                        System.load(WPCAP_DLL);
                    } else {
                        callback.onFailure(new UnsatisfiedLinkError("Npcap or Winpcap is not installed yet."));
                    }
                }
                if (Platforms.is64Bit()) {
                    nativeLibrary.register(WINDOWS_X64);
                } else {
                    nativeLibrary.register(WINDOWS_X86);
                }
                break;
            case DARWIN:
                if (Platforms.is64Bit()) {
                    nativeLibrary.register(DARWIN_X64);
                }
                break;
            default:
                throw new PlatformNotSupportedException("Your platform does't supported by dynamic jxnet.");
        }
        nativeLibrary.load(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                callback.onSuccess(null);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    static {
        boolean isUseSystemLibrary;
        String useSystemLibrary = System.getProperty("jxnet.system.library", "false");
        if ("yes".equalsIgnoreCase(useSystemLibrary)
                || "true".equalsIgnoreCase(useSystemLibrary)
                || "1".equalsIgnoreCase(useSystemLibrary)) {
            isUseSystemLibrary = true;
        } else {
            isUseSystemLibrary = false;
        }
        USE_SYSTEM_LIBRARY = isUseSystemLibrary;
    }

}
