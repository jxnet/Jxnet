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

public final class PcapAddr {
	
	@SuppressWarnings("unused")
	private PcapAddr next;
	
	private volatile SockAddr addr;
	
	private volatile SockAddr netmask;
	
	private volatile SockAddr broadaddr;
	
	private volatile SockAddr dstaddr;
	
	public SockAddr getAddr() {
		return addr;
	}
	
	public SockAddr getNetmask() {
		return netmask;
	}
	
	public SockAddr getBroadAddr() {
		return broadaddr;
	}
	
	public SockAddr getDstAddr() {
		return dstaddr;
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append(String.valueOf(addr));
		out.append(String.valueOf(netmask));
		out.append(String.valueOf(broadaddr));
		out.append(String.valueOf(dstaddr));
		return out.toString();
	}
	
}