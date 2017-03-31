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

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public class Platforms {
	
	public enum NAME {
		LINUX(1, "Linux"), WINDOWS(2, "Windows"), ANDROID(3, "Android"), UNKNOWN(0, "Unknown");
		
		private int type;
		private String name;
		
		private NAME(int type, String name) {
			this.type = type;
			this.name = name;
		}
		
		public int getType() {
			return type;
		}
		
		public String getName() {
			return name;
		}
		
	}

	public enum ARCH {
		x86(1, "32 Bit"), x86_64(2, "64 Bit");
		
		private int type;
		private String arch;
		
		private ARCH(int type, String arch) {
			this.type = type;
			this.arch = arch;
		}
		
		public int getType() {
			return type;
		}
		
		public String getName() {
			return arch;
		}
	}
	
	private static Platforms.NAME NAME;
	
	private static Platforms.ARCH ARCH;
	
	public static Platforms.NAME getName() {
		return NAME;
	}
	
	public static Platforms.ARCH getArch() {
		return ARCH;
	}
	
	public static final boolean isWindows() {
		return NAME == Platforms.NAME.WINDOWS;
	}
	
	public static final boolean isLinux() {
		return NAME == Platforms.NAME.LINUX;
	}
	
	public static final boolean isAndroid() {
		return NAME == Platforms.NAME.ANDROID;
	}
	
	public static final boolean is64Bit() {
		return ARCH == Platforms.ARCH.x86_64;
	}
	
	public static final boolean isIntel() {
		return (System.getProperty("os.arch").toLowerCase().trim().startsWith("x86") ? true : false);
	}
	
	public static final boolean isARM() {
		return (System.getProperty("os.arch").toLowerCase().trim().startsWith("arm") ? true : false);
	}
	
	public static final String getVersion() {
		String version = System.getProperty("os.version");
		if (Character.isDigit(version.charAt(version.indexOf("v") + 1))) {
			return String.valueOf("v" + version.charAt(version.indexOf("v") + 1));
		}
		return null;
	}
	
	static {
		String osName = System.getProperty("os.name");
		String osArch = System.getProperty("os.arch");
		if (osName.startsWith("Linux")) {
			if (new String("dalvik").equals(System.getProperty("java.vm.name").toLowerCase())) {
				NAME = Platforms.NAME.ANDROID;
			} else {
				NAME = Platforms.NAME.LINUX;
			}
		} else if (osName.startsWith("Windows")) {
			NAME = Platforms.NAME.WINDOWS;
		}
		osArch = osArch.toLowerCase().trim();
		if ("i386".equals(osArch) || "i686".equals(osArch)) {
			ARCH = Platforms.ARCH.x86;
		} else if ("x86_64".equals(osArch) || "amd64".equals(osArch)) {
			ARCH = Platforms.ARCH.x86_64;
		}
	}
	
}
