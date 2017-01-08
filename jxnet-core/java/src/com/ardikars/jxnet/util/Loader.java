
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet.util;

import java.io.*;
import java.util.regex.Pattern;

public class Loader {
	
	public static void loadFromInnerJar(String[] path) throws
			IllegalArgumentException, IOException, FileNotFoundException {
		for (String lib : path) {
			loadLibrary(lib);
		}
	}
	
	public static void loadLibrary() throws
			UnsatisfiedLinkError, IOException, FileNotFoundException, IllegalArgumentException {
		if (load()) {
			return;
		}
		switch (Platform.getNAME().getType()) {
			case 1:
				if (Platform.isARM()) {
					if (Platform.getVersion().equals("v7")) {
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
			case 2:
				if (Platform.is64Bit()) {
					loadLibrary("/lib/x86_64/jxnet.dll");
				} else {
					loadLibrary("/lib/x86/jxnet.dll");
				}
				break;
			case 3:
				System.loadLibrary("jxnet");
				break;
			default:
				break;
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
