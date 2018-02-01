/**
 * Copyright (C) 2017-2018  Ardika Rommy Sanjaya <contact@ardikars.com>
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
public final class PcapAddr {
	
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
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final PcapAddr pcapAddr = (PcapAddr) o;

		if (!getAddr().equals(pcapAddr.getAddr())) {
			return false;
		}
		if (!getNetmask().equals(pcapAddr.getNetmask())) {
			return false;
		}
		if (!getBroadAddr().equals(pcapAddr.getBroadAddr())) {
			return false;
		}
		return getDstAddr().equals(pcapAddr.getDstAddr());
	}

	@Override
	public int hashCode() {
		int result = getAddr().hashCode();
		result = 31 * result + getNetmask().hashCode();
		result = 31 * result + getBroadAddr().hashCode();
		result = 31 * result + getDstAddr().hashCode();
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("PcapAddr{");
		sb.append("addr=").append(addr);
		sb.append(", netmask=").append(netmask);
		sb.append(", broadaddr=").append(broadaddr);
		sb.append(", dstaddr=").append(dstaddr);
		sb.append('}');
		return sb.toString();
	}

}
