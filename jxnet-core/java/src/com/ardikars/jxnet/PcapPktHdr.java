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
public final class PcapPktHdr {
	
	private int caplen;
	private int len;
	
	private int tv_sec;
	private long tv_usec;
	
	public PcapPktHdr() {
		this.caplen = 0;
		this.len = 0;
		this.tv_sec = 0;
		this.tv_usec = 0;
	}

	/**
	 * Returning capture length.
	 * @return capture length.
	 */
	public int getCapLen() {
		return caplen;
	}

	/**
	 * Returning packet length.
	 * @return packet length.
	 */
	public int getLen() {
		return len;
	}

	/**
	 * Returning tv_sec.
	 * @return tv_sec.
	 */
	public int getTvSec() {
		return tv_sec;
	}

	/**
	 * Returning tv_usec.
	 * @return tv_usec.
	 */
	public long getTvUsec() {
		return tv_usec;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("[Capture Length: ").append(caplen)
				.append(", Length: ").append(len)
				.append(", TvSec: ").append(tv_sec)
				.append(", TvUSec: ").append(tv_usec)
				.append("]").toString();
	}
	
}
