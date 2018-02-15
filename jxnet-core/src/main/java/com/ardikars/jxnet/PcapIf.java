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

	private volatile String name;
	
	private volatile String description;
	
	private volatile List<PcapAddr> addresses = new ArrayList<PcapAddr>();
	
	private volatile int flags;

	private PcapIf() {
		//
	}

	/**
	 * Returning interface name.
	 * @return interface name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returning interface description.
	 * @return interface description.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Returning interface flags.
	 * @return interface flags.
	 */
	public int getFlags() {
		return this.flags;
	}

	/**
	 * Returning interface addresses.
	 * @return interface addresses.
	 */
	public List<PcapAddr> getAddresses() {
		List<PcapAddr> addrs = Collections.unmodifiableList(this.addresses);
		return addresses;
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
	protected Object clone() {
		PcapIf pcapIf = new PcapIf();
		pcapIf.name = this.name;
		pcapIf.addresses = this.addresses;
		pcapIf.description = this.description;
		pcapIf.flags = this.flags;
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
