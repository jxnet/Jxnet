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

	public PcapPktHdr(int caplen, int len, int tv_sec, long tv_usec) {
		this.caplen = caplen;
		this.len = len;
		this.tv_sec = tv_sec;
		this.tv_usec = tv_usec;
	}

	/**
	 * Returning capture length.
	 * @return capture length.
	 */
	public int getCapLen() {
		return this.caplen;
	}

	/**
	 * Returning packet length.
	 * @return packet length.
	 */
	public int getLen() {
		return this.len;
	}

	/**
	 * Returning tv_sec.
	 * @return tv_sec.
	 */
	public int getTvSec() {
		return this.tv_sec;
	}

	/**
	 * Returning tv_usec.
	 * @return tv_usec.
	 */
	public long getTvUsec() {
		return this.tv_usec;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("[Capture Length: ").append(this.caplen)
				.append(", Length: ").append(this.len)
				.append(", TvSec: ").append(this.tv_sec)
				.append(", TvUSec: ").append(this.tv_usec)
				.append("]").toString();
	}
	
}
