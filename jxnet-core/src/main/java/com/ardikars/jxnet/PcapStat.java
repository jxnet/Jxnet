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
 * Class that keeps statistical values on an interface.
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class PcapStat implements Cloneable {

	private long ps_recv;
	
	private long ps_drop;
	
	private long ps_ifdrop;

	/**
	 * Returns recieved packets.
	 * @return returns number of packets received;
	 */
	public long getPsRecv() {
		return this.ps_recv;
	}

	/**
	 * Returns number of packets dropped because there was no room in the operating
	 * system`s buffer when they arrived, because packets weren`t being read fast enough.
	 * @return returns number of packets dropped because there was no room in the operating system's buffer.
	 */
	public long getPsDrop() {
		return this.ps_drop;
	}

	/**
	 * Returns dropped packets by interface.
	 * @return returns number of packets dropped by the network interface or its driver.
	 */
	public long getPsIfdrop() {
		return this.ps_ifdrop;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final PcapStat pcapStat = (PcapStat) o;

		if (this.ps_recv != pcapStat.ps_recv) {
			return false;
		}
		if (this.ps_drop != pcapStat.ps_drop) {
			return false;
		}
		return this.ps_ifdrop == pcapStat.ps_ifdrop;
	}

	@Override
	public int hashCode() {
		int result = (int) (this.ps_recv ^ (this.ps_recv >>> 32));
		result = 31 * result + (int) (this.ps_drop ^ (this.ps_drop >>> 32));
		result = 31 * result + (int) (this.ps_ifdrop ^ (this.ps_ifdrop >>> 32));
		return result;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		PcapStat pcapStat = (PcapStat) super.clone();
		return pcapStat;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(100);
		sb.append("PcapStat{ps_recv=").append(this.ps_recv);
		sb.append(", ps_drop=").append(this.ps_drop);
		sb.append(", ps_ifdrop=").append(this.ps_ifdrop);
		sb.append('}');
		return sb.toString();
	}

}
