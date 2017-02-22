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

import java.util.ArrayList;
import java.util.List;

public final class PcapIf {
	
	@SuppressWarnings("unused")
	private PcapIf next;
	
	private volatile String name;
	
	private volatile String description;
	
	private volatile List<PcapAddr> addresses = new ArrayList<PcapAddr>();
	
	private volatile int flags;
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getFlags() {
		return flags;
	}
	
	public List<PcapAddr> getAddresses() {
		return addresses;
	}
	
	public MacAddress getHardwareAddress() {
		return AddrUtils.getHardwareAddress(name);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}