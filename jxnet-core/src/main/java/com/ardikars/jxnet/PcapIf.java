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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class PcapIf implements Cloneable {

	/**
	 * Interface is loopback.
	 */
	private static final int PCAP_IF_LOOPBACK = 0x00000001;

	/**
	 * Interface is up.
	 */
	private static final int PCAP_IF_UP = 0x00000002;

	/**
	 * Interface is running.
	 */
	private static final int PCAP_IF_RUNNING = 0x00000004;

	private volatile String name;
	
	private volatile String description;
	
	private volatile List<PcapAddr> addresses = new ArrayList<PcapAddr>();
	
	private volatile int flags;

	private PcapIf() {
		//
	}

	/**
	 * Getting interface name.
	 * @return returns interface name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getting interface description.
	 * @return returns interface description.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Getting interface flags.
	 * @return returns interface flags.
	 */
	public int getFlags() {
		return this.flags;
	}

	/**
	 * Getting interface addresses.
	 * @return returns interface addresses.
	 */
	public List<PcapAddr> getAddresses() {
		List<PcapAddr> addrs = Collections.unmodifiableList(this.addresses);
		return addrs;
	}

	/**
	 * Is loopback interface?
	 * @return true if loopback interface, false otherwise.
	 */
	public boolean isLoopback() {
		return (this.flags & PCAP_IF_LOOPBACK) != 0;
	}

	/**
	 * Is interface is up?
	 * @return true if interface is up, false otherwise.
	 */
	public boolean isUp() {
		return (this.flags & PCAP_IF_UP) != 0;
	}

	/**
	 * Is interface is running?
	 * @return true if interface is running, false otherwise.
	 */
	public boolean isRunning() {
		return (this.flags & PCAP_IF_RUNNING) != 0;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final PcapIf pcapIf = (PcapIf) o;

		if (this.getFlags() != pcapIf.getFlags()) {
			return false;
		}
		if (!this.getName().equals(pcapIf.getName())) {
			return false;
		}
		if (!this.getDescription().equals(pcapIf.getDescription())) {
			return false;
		}
		return this.getAddresses().equals(pcapIf.getAddresses());
	}

	@Override
	public int hashCode() {
		int result = this.getName().hashCode();
		result = 31 * result + this.getDescription().hashCode();
		result = 31 * result + this.getAddresses().hashCode();
		result = 31 * result + this.getFlags();
		return result;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		PcapIf pcapIf = (PcapIf) super.clone();
		return pcapIf;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(500);
		sb.append("PcapIf{name='").append(this.name);
		sb.append(", description='").append(this.description);
		sb.append(", addresses=").append(this.addresses);
		sb.append(", flags=").append(this.flags);
		sb.append('}');
		return sb.toString();
	}

}
