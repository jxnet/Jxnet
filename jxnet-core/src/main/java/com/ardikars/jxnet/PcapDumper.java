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

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class PcapDumper {

	private long address;

	public long getAddress() {
		return address;
	}

	/**
	 * Return true if PcapDumper is closed.
	 * @return true if PcapDumper is closed, false otherwise.
	 */
	public boolean isClosed() {
		if (address == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.valueOf(address);
	}

}
