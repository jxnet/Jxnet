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
	
	public int getCapLen() {
		return caplen;
	}
	
	public int getLen() {
		return len;
	}
	
	public int getTvSec() {
		return tv_sec;
	}
	
	public long getTvUsec() {
		return tv_usec;
	}
	
	@Override
	public String toString() {
		return "caplen = " + caplen + ", len = " + len +
				", tv_sec = " + tv_sec + ", tv_usec = " + tv_usec;
	}
	
}
