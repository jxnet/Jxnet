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

    public static final String DYNAMIC_LINUX_X64 = "/native/libjxnet-linux-x64.so";
    public static final String DYNAMIC_LINUX_X86 = "/native/libjxnet-linux-x86.so";
    public static final String DYNAMIC_WINDOWS_X64 = "/native/jxnet-windows-x64.dll";
    public static final String DYNAMIC_WINDOWS_X86 = "/native/jxnet-windows-x86.dll";
    public static final String DYNAMIC_DARWIN_X64 = "/native/libjxnet-darwin-x64.dylib";

    private static final int BUFFER_SIZE = 1024;

    private Library() {

    }

    public interface Loader {
        void load();
    }

    /**
     * Load native libarary from inner jar.
     * @param path absulute library path start with '/'.
     * @throws IllegalArgumentException illegal argument exception.
     * @throws IOException IO exception.
     */
    public static void loadLibrary(final String path) throws IllegalArgumentException, IOException {
        Validate.nullPointer(path, new NullPointerException("Path should be not null."));
        if (!(path.charAt(0) == '/')) {
            throw new IllegalArgumentException("The path has to be absolute (start with '/').");
        }
        String[] parts = Pattern.compile("/").split(path);
        if (parts != null && parts.length > 1) {
            parts = Pattern.compile("\\.").split(parts[parts.length - 1]);
        } else {
            throw new IllegalArgumentException();
        }
        File temp;
        try {
            temp = File.createTempFile(parts[0], "." + parts[1]);
            temp.deleteOnExit();
        } catch (IOException e) {
            throw e;
        }
        final byte[] buffer = new byte[BUFFER_SIZE];
        int readBytes;
        InputStream is = null;
        OutputStream os = null;
        try {
            is = Library.class.getResourceAsStream(path);
            os = new FileOutputStream(temp);
            while ((readBytes = is.read(buffer)) != -1) {
                try {
                    os.write(buffer, 0, readBytes);
                } catch (IOException e) {
                    throw e;
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        System.load(temp.getAbsolutePath());
        LOGGER.info("Successfully loaded the jxnet native library.");
    }

}
