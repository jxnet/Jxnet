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
public final class Platforms {

	/**
	 * Operating System Name
	 */
	public enum NAME {

		LINUX, WINDOWS, ANDROID, FREEBSD, UNKNOWN;

	}

	/**
	 * Architecture
	 */
	public enum ARCHITECTURE {

		_32_BIT, _64_BIT;

	}

	/**
	 * Operating System Name
	 */
	private static Platforms.NAME NAME;

	/**
	 * CPU Architecture (32 bit/64 bit)
	 */
	private static Platforms.ARCHITECTURE ARCHITECTURE;

	/**
	 * Get Operating System Name
	 * @return Operating System Name
	 */
	public static Platforms.NAME getName() {
		return NAME;
	}

	/**
	 * Get CPU Architecture
	 * @return CPU Architecture
	 */
	public static Platforms.ARCHITECTURE getArchitecture() {
		return ARCHITECTURE;
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

	public static final boolean isFreeBSD() {
		return NAME == Platforms.NAME.FREEBSD;
	}

	public static final boolean is32Bit() {
		return ARCHITECTURE == Platforms.ARCHITECTURE._32_BIT;
	}

	public static final boolean is64Bit() {
		return ARCHITECTURE == Platforms.ARCHITECTURE._64_BIT;
	}

	public static final boolean isArm() {
		return (System.getProperty("os.arch").toLowerCase().trim().startsWith("arm"));
	}

	public static final boolean isIntel() {
		String arch = System.getProperty("os.arch").toLowerCase().trim();
		return (arch.startsWith("x86") || arch.startsWith("x64"));
	}

	public static final boolean isAmd() {
		return System.getProperty("os.arch").toLowerCase().trim().startsWith("amd");
	}

	/**
	 * Get CPU Version
	 * @return CPU Version
	 */
	public static final String getVersion() {
		String version = System.getProperty("os.version");
		if (Character.isDigit(version.charAt(version.indexOf("v") + 1))) {
			return String.valueOf("v" + version.charAt(version.indexOf("v") + 1));
		}
		return null;
	}
	
	static {
		String osName = System.getProperty("os.name").toUpperCase().trim();
		String osArch = System.getProperty("os.arch").toLowerCase().trim();
		if (osName.startsWith("LINUX")) {
			if (new String("DALVIK").equals(System.getProperty("java.vm.name").toUpperCase().trim())) {
				NAME = Platforms.NAME.ANDROID;
			} else {
				NAME = Platforms.NAME.LINUX;
			}
		} else if (osName.startsWith("WINDOWS")) {
			NAME = Platforms.NAME.WINDOWS;
		} else if (osName.startsWith("FREEBSD")) {
			NAME = Platforms.NAME.FREEBSD;
		}
		if ("i386".equals(osArch) || "i686".equals(osArch) || "i586".equals(osArch)) {
			ARCHITECTURE = Platforms.ARCHITECTURE._32_BIT;
		} else if ("x86_64".equals(osArch) || "amd64".equals(osArch) || "x64".equals(osArch)) {
			ARCHITECTURE = Platforms.ARCHITECTURE._64_BIT;
		}
	}
	
}
