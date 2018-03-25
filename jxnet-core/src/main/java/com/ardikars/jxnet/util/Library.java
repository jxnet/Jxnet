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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public final class Library {

    private static final Logger LOGGER = Logger.getLogger(Library.class.getName());

    public static final String NPCAP_DLL = "C:\\Windows\\System32\\Npcap\\wpcap.dll";
    public static final String WPCAP_DLL = "C:\\Windows\\System32\\wpcap.dll";

    public static final String DYNAMIC_LINUX_X64 = "/dynamic/linux/lib/x64/libjxnet.so";
    public static final String DYNAMIC_LINUX_X86 = "/dynamic/linux/lib/x86/libjxnet.so";
    public static final String DYNAMIC_LINUX_ARM32 = "/dynamic/linux/lib/arm32/libjxnet.so";
    public static final String DYNAMIC_FREEBSD_X64 = "/dynamic/freebsd/lib/x64/libjxnet.so";
    public static final String DYNAMIC_FREEBSD_X86 = "/dynamic/freebsd/lib/x86/libjxnet.so";
    public static final String DYNAMIC_WINDOWS_X64 = "/dynamic/windows/lib/x64/jxnet.dll";
    public static final String DYNAMIC_WINDOWS_X86 = "/dynamic/windows/lib/x86/jxnet.dll";
    public static final String DYNAMIC_DARWIN_X64 = "/dynamic/darwin/lib/x64/libjxnet.dylib";

    public static final String STATIC_LINUX_X64 = "/static/linux/lib/x64/libjxnet.so";
    public static final String STATIC_LINUX_X86 = "/static/linux/lib/x86/libjxnet.so";
    public static final String STATIC_LINUX_ARM32 = "/static/linux/lib/arm32/libjxnet.so";
    public static final String STATIC_FREEBSD_X64 = "/static/freebsd/lib/x64/libjxnet.so";
    public static final String STATIC_FREEBSD_X86 = "/static/freebsd/lib/x86/libjxnet.so";
    public static final String STATIC_DARWIN_X64 = "/static/darwin/lib/x64/libjxnet.dylib";

    private static final int BUFFER_SIZE = 1024;

    private Library() {

    }

    public interface Loader {
        void load();
    }

    public static void loadLibrary(final String path) {
        Validate.nullPointer(path, "Path should be not null.");
        if (!(path.charAt(0) == '/')) {
            throw new IllegalArgumentException("The path has to be absolute (start with '/').");
        }
        String[] parts = Pattern.compile("/").split(path);
        if (parts != null && parts.length > 1) {
            parts = Pattern.compile("\\.").split(parts[parts.length - 1]);
        }
        File temp = null;
        try {
            temp = File.createTempFile(parts[0], "." + parts[1]);
            temp.deleteOnExit();
        } catch (IOException e) {
            LOGGER.warning("Failed to create temporary file: " + e.getMessage());
        }
        final byte[] buffer = new byte[BUFFER_SIZE];
        int readBytes;
        final InputStream is = Library.class.getResourceAsStream(path);
        if (is == null) {
            LOGGER.warning("Error: " + path + " not found in classpath.");
            return;
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(temp);
            while ((readBytes = is.read(buffer)) != -1) {
                try {
                    os.write(buffer, 0, readBytes);
                } catch (IOException e) {
                    LOGGER.warning("Failed to write into temporary file: " + e.getMessage());
                    return;
                }
            }
        } catch (IOException e) {
            LOGGER.warning("Failed to write into temporary file: " + e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                LOGGER.warning("Failed to write into temporary file: " + e.getMessage());
                return;
            }
        }
        System.load(temp.getAbsolutePath());
        LOGGER.info("Successfully loaded the jxnet native library.");
    }

}
