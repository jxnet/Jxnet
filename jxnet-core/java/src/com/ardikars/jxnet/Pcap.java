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

package com.ardikars.jxnet;

import com.ardikars.jxnet.util.Pointer;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 * @version 1.0.0
 */
public final class Pcap {

	private static native void initIDs();
	
	private Pointer pointer;

	/**
	 * Return Pcap pointer.
	 * @return Pcap pointer;
	 */
	public Pointer getPointer() {
		return pointer;
	}
	
	public boolean isClosed() {
		if (pointer.getAddress() == 0 || pointer == null) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return pointer.toString();
	}

	static {
		try {
			Class.forName("com.ardikars.jxnet.Jxnet");
			initIDs();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}