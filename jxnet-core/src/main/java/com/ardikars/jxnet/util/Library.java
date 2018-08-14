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

import com.ardikars.common.util.Validate;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    public static final String LINUX_X64 = "/native/libjxnet-linux-x64.so";
    public static final String LINUX_X86 = "/native/libjxnet-linux-x86.so";
    public static final String WINDOWS_X64 = "/native/jxnet-windows-x64.dll";
    public static final String WINDOWS_X86 = "/native/jxnet-windows-x86.dll";
    public static final String DARWIN_X64 = "/native/libjxnet-darwin-x64.dylib";

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
        InputStream is = readLibrary(path);
        if (is == null) {
            throw new FileNotFoundException(path + " is not found.");
        }
        OutputStream os = openOutputStream(temp, parts[0], "." + parts[1]);
        if (os == null) {
            throw new IOException("Failed export file in classpath: " + path);
        }
        writeLibrary(is, os);
        System.load(temp.getAbsolutePath());
        LOGGER.info("Successfully loaded the jxnet native library.");
    }

    private static InputStream readLibrary(String path) {
        InputStream inputStream;
        inputStream = Library.class.getResourceAsStream(path);
        return inputStream;
    }

    private static OutputStream openOutputStream(File temporary, String prefix, String suffix) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(temporary);
        } catch (IOException e) {
            return null;
        }
        return outputStream;
    }

    private static void writeLibrary(InputStream inputStream, OutputStream outputStream) throws IOException {
        final byte[] buffer = new byte[BUFFER_SIZE];
        int readBytes;
        while ((readBytes = inputStream.read(buffer)) != -1) {
            try {
                outputStream.write(buffer);
            } catch (IOException e) {
                throw e;
            }
        }
        inputStream.close();
        outputStream.close();
    }

}
