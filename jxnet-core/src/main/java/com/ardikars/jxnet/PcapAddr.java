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

import java.util.Arrays;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class PcapAddr {

	@SuppressWarnings("unused")
	private volatile PcapAddr next;
	
	private volatile SockAddr addr;
	
	private volatile SockAddr netmask;
	
	private volatile SockAddr broadaddr;
	
	private volatile SockAddr dstaddr;

	private PcapAddr() {
		//
	}

	/**
	 * Returning interface address.
	 * @return interface address.
	 */
	public SockAddr getAddr() {
		return this.addr;
	}

	/**
	 * Returning interface netmask.
	 * @return interface netmask.
	 */
	public SockAddr getNetmask() {
		return this.netmask;
	}

	/**
	 * Returning interface broadcast address.
	 * @return interface broadcast address.
	 */
	public SockAddr getBroadAddr() {
		return this.broadaddr;
	}

	/**
	 * Returning interface destination address.
	 * @return interface destination address.
	 */
	public SockAddr getDstAddr() {
		return this.dstaddr;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj.getClass() != this.getClass()) return false;
		if (!(obj instanceof PcapAddr)) return false;
		PcapAddr pcapAddr = (PcapAddr) obj;
		if (this.addr.equals(pcapAddr.getAddr()) &&
				this.netmask.equals(pcapAddr.getNetmask()) &&
				this.broadaddr.equals(pcapAddr.getBroadAddr()) &&
				this.dstaddr.equals(pcapAddr.getDstAddr()))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(toString().getBytes());
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("[Addr: ").append(this.addr.toString())
				.append(", Netmask: ").append(this.netmask.toString())
				.append(", Broadcast Addr: ").append(this.broadaddr.toString())
				.append(", Destiantion Addr: ").append(this.dstaddr.toString())
				.toString();
	}
	
}
