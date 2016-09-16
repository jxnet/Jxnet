
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

public class Loader {
	
	public static void load(String[] path) {
		for(String lib : path) {
			System.load(lib);
		}
	}

	public static void loadLibrary(String[] name) {
		for(String lib : name) {
			System.loadLibrary(lib);
		}
	}
	
	public static void loadFromJar(String[] path) {
		for(String lib : path) {
			loadLibrary(lib);
		}
	}
	
	public static void loadLibrary() {
		if(load()) {
			return;
		}
		switch (Platform.getNAME().getType()) {
		case 1:
			if(Platform.isARM()) {
				if(Platform.getVersion().equals("v7")) {
					loadLibrary("/lib/armeabi-v7l/libjxnet-linux.so");
				}
			} else {
				if(Platform.is64Bit()) {
					loadLibrary("/lib/x86_64/libjxnet-linux.so");
				} else {
					loadLibrary("/lib/x86/libjxnet-linux.so");
				}
			}
			break;
		case 2:
			if(Platform.is64Bit()) {
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
		try {
			System.loadLibrary("jxnet");
			return true;
		} catch (UnsatisfiedLinkError e) {
			return false;
		}
	}
	
	private static boolean loadLibrary(String path) {
		if (!path.startsWith("/")) {
			throw new IllegalArgumentException("The path has to be absolute (start with '/').");
        }
		String[] parts = Pattern.compile("/").split(path);
		if(parts != null && parts.length > 1) {
			parts = Pattern.compile("\\.").split(parts[parts.length - 1]);
		} 
		File temp = null;
		try {
			temp = File.createTempFile(parts[0], "."+parts[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		temp.deleteOnExit();
		byte[] buffer = new byte[1024];
		int readBytes;
		InputStream is = Loader.class.getResourceAsStream(path);
		if(is == null) {
			System.err.println(path + " not found.");
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
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.load(temp.getAbsolutePath());
		return false;
	}
	
}
