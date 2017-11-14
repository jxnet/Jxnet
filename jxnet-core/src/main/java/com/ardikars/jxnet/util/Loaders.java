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

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.exception.NotSupportedPlatformException;

import java.io.*;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class Loaders {

	/**
	 * Error Buffer
	 */
	private static StringBuilder errbuf = new StringBuilder();

	/**
	 * Load library from absolute path.
	 * @param path absolute path.
	 * @throws Exception exception.
	 */
	public static void loadFromAbsolutePath(String path) throws Exception {
		Validate.nullPointer(path);
		try {
			System.load(path);
			Field isLoadedField = Jxnet.class.getDeclaredField("isLoaded");
			isLoadedField.setAccessible(true);
			isLoadedField.setBoolean(Jxnet.class, true);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	/**
	 * Load library from jar.
	 * @param path library path in jar.
	 * @throws IllegalArgumentException IllegalArgumentException.
	 * @throws IOException IOException.
	 */
	public static void loadFromInnerJar(String[] path) throws
			IllegalArgumentException, IOException {
		errbuf.setLength(0);
		Validate.nullPointer(path);
		for (String lib : path) {
			loadLibrary(lib);
		}
	}

	/**
	 * Load library.
	 * @throws UnsatisfiedLinkError UnsatisfiedLinkError.
	 * @throws IOException IOException.
	 * @throws IllegalArgumentException IllegalArgumentException.
	 */
	public static void loadSharedLibrary() throws UnsatisfiedLinkError, NotSupportedPlatformException {
		errbuf.setLength(0);
		if (Platforms.isWindows()) {
			addJavaLibarayPath("C:\\Windows\\System32\\Npcap");
		}

		try {
			System.loadLibrary("jxnet");
			return;
		} catch (UnsatisfiedLinkError e) {
			switch (Platforms.getName()) {
				case LINUX:
					if (Platforms.isArm()) {
						if (Platforms.getVersion().equals("v7") || Platforms.getVersion().equals("v6")) {
							loadLibrary("/shared/linux/lib/arm32/libjxnet.so");
						}
					} else {
						if (Platforms.is64Bit()) {
							loadLibrary("/shared/linux/lib/x64/libjxnet.so");
						} else {
							loadLibrary("/shared/linux/lib/x86/libjxnet.so");
						}
					}
					break;
				case WINDOWS:
					if (Platforms.is64Bit()) {
						loadLibrary("/shared/windows/lib/x64/jxnet.dll");
					} else {
						loadLibrary("/shared/windows/lib/x86/jxnet.dll");
					}
					break;
				case ANDROID:
					System.loadLibrary("jxnet");
					break;
				case FREEBSD:
					if (Platforms.is64Bit()) {
						loadLibrary("/shared/freebsd/lib/x64/libjxnet.so");
					} else {
						loadLibrary("/shared/freebsd/lib/x86/libjxnet.so");
					}
					break;
				default:
					throw new NotSupportedPlatformException();

			}
		}
	}

	public static void loadStaticLibrary() throws UnsatisfiedLinkError, NotSupportedPlatformException {
		errbuf.setLength(0);
		if (Platforms.isWindows()) {
			addJavaLibarayPath("C:\\Windows\\System32\\Npcap");
		}

		try {
			System.loadLibrary("jxnet");
			return;
		} catch (UnsatisfiedLinkError e) {
			switch (Platforms.getName()) {
				case LINUX:
					if (Platforms.isArm()) {
						if (Platforms.getVersion().equals("v7") || Platforms.getVersion().equals("v6")) {
							loadLibrary("/static/linux/lib/arm32/libjxnet.so");
						}
					} else {
						if (Platforms.is64Bit()) {
							loadLibrary("/static/linux/lib/x64/libjxnet.so");
						} else {
							loadLibrary("/static/linux/lib/x86/libjxnet.so");
						}
					}
					break;
				case WINDOWS:
					if (Platforms.is64Bit()) {
						loadLibrary("/static/windows/lib/x64/jxnet.dll");
					} else {
						loadLibrary("/static/windows/lib/x86/jxnet.dll");
					}
					break;
				case ANDROID:
					System.loadLibrary("jxnet");
					break;
				case FREEBSD:
					if (Platforms.is64Bit()) {
						loadLibrary("/static/freebsd/lib/x64/libjxnet.so");
					} else {
						loadLibrary("/static/freebsd/lib/x86/libjxnet.so");
					}
					break;
				default:
					throw new NotSupportedPlatformException();

			}
		}
	}

	private static void loadLibrary(String path) throws UnsatisfiedLinkError {
		Validate.nullPointer(path);
		if (!path.startsWith("/")) {
			throw new IllegalArgumentException("The path has to be absolute (start with '/').");
		}
		String[] parts = Pattern.compile("/").split(path);
		if (parts != null && parts.length > 1) {
			parts = Pattern.compile("\\.").split(parts[parts.length - 1]);
		}
		File temp = null;
		try {
			temp = File.createTempFile(parts[0], "." + parts[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		temp.deleteOnExit();
		byte[] buffer = new byte[1024];
		int readBytes;
		InputStream is = Loaders.class.getResourceAsStream(path);
		if (is == null) {
			errbuf.append("Error: " + path + " not found.\n");
		}
		OutputStream os = null;
		try {
			os = new FileOutputStream(temp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			while ((readBytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, readBytes);
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.load(temp.getAbsolutePath());
	}

	/**
	 * Add java.library.path value.
	 * @param path path.
	 */
	public static void addJavaLibarayPath(String path) {
		String paths = System.getProperty("java.library.path");
		String pathSparator = File.pathSeparator;
		String[] libraryPaths = paths.split(pathSparator);
		for (String str : libraryPaths) {
			if (str.equals(path)) {
				return;
			}
		}
		paths = paths.concat(pathSparator + path);
		System.setProperty("java.library.path", paths);
		Field sysPathsField = null;
		try {
			sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		sysPathsField.setAccessible(true);
		try {
			sysPathsField.set(null, null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
