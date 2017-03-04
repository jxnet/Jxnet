/**
 * Copyright (C) 2017  Ardika Rommy Sanjaya
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

import com.ardikars.jxnet.exception.NotSupportedPlatformException;

import java.io.*;
import java.util.regex.Pattern;
import static com.ardikars.jxnet.util.Preconditions.CheckNotNull;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public class Loader {

	/**
	 * Load library from jar /lib.
	 * @param path library path.
	 */
	public static void loadFromInnerJar(String[] path) throws
			IllegalArgumentException, IOException, FileNotFoundException {
		//checkNotNull(path);
		for (String lib : path) {
			loadLibrary(lib);
		}
	}

	/**
	 * Load library.
	 */
	public static void loadLibrary() throws
			UnsatisfiedLinkError, IOException, FileNotFoundException, IllegalArgumentException {
		if (load()) {
			return;
		}
		switch (Platform.getNAME()) {
			case LINUX:
				if (Platform.isARM()) {
					if (Platform.getVersion().equals("v7") || Platform.getVersion().equals("v6")) {
						loadLibrary("/lib/armeabi-v7l/libjxnet-linux.so");
					}
				} else {
					if (Platform.is64Bit()) {
						loadLibrary("/lib/x86_64/libjxnet-linux.so");
					} else {
						loadLibrary("/lib/x86/libjxnet-linux.so");
					}
				}
				break;
			case WINDOWS:
				if (Platform.is64Bit()) {
					loadLibrary("/lib/x86_64/jxnet.dll");
				} else {
					loadLibrary("/lib/x86/jxnet.dll");
				}
				break;
			case ANDROID:
				System.loadLibrary("jxnet");
				break;
			default:
				throw new NotSupportedPlatformException();

		}
	}
	
	private static boolean load() {
		boolean success = false;
		try {
			System.loadLibrary("jxnet");
			success = true;
		} catch (UnsatisfiedLinkError e) {
			success = false;
		}
		return success;
	}
	
	private static void loadLibrary(String path) throws IllegalArgumentException, IOException, FileNotFoundException {
		//CheckNotNull(path);
		if (!path.startsWith("/")) {
			throw new IllegalArgumentException("The path has to be absolute (start with '/').");
		}
		String[] parts = Pattern.compile("/").split(path);
		if (parts != null && parts.length > 1) {
			parts = Pattern.compile("\\.").split(parts[parts.length - 1]);
		}
		File temp = File.createTempFile(parts[0], "." + parts[1]);
		temp.deleteOnExit();
		byte[] buffer = new byte[1024];
		int readBytes;
		InputStream is = Loader.class.getResourceAsStream(path);
		if (is == null) {
			throw new FileNotFoundException(path + " not found.");
		}
		OutputStream os = new FileOutputStream(temp);
		while ((readBytes = is.read(buffer)) != -1) {
			os.write(buffer, 0, readBytes);
		}
		is.close();
		os.close();
		System.load(temp.getAbsolutePath());
	}


}
