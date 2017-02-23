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

import com.ardikars.jxnet.util.AddrUtils;
import com.ardikars.jxnet.util.FormatUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 * @version 1.0.0
 */
public final class PcapIf {
	
	@SuppressWarnings("unused")
	private PcapIf next;
	
	private volatile String name;
	
	private volatile String description;
	
	private volatile List<PcapAddr> addresses = new ArrayList<PcapAddr>();
	
	private volatile int flags;

	/**
	 * Returning interface name.
	 * @return interface name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returning interface description.
	 * @return interface description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returning interface flags.
	 * @return interface flags.
	 */
	public int getFlags() {
		return flags;
	}

	/**
	 * Returning interface addresses.
	 * @return interface addresses.
	 */
	public List<PcapAddr> getAddresses() {
		return addresses;
	}

	/**
	 * Returning interface MAC Address.
	 * @return interface MAC Address.
	 */
	public MacAddress getHardwareAddress() {
		return AddrUtils.getHardwareAddress(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj.getClass() != getClass()) return false;
		if (!(obj instanceof PcapIf)) return false;
		PcapIf pcapIf = (PcapIf) obj;
		if (name.equals(pcapIf.getName()) &&
				description.equals(pcapIf.getDescription()) &&
				flags == pcapIf.getFlags())
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return Long.hashCode(FormatUtils.toLong(
				toString().getBytes()
		));
	}

	@Override
	public String toString() {
		return name;
	}
	
}