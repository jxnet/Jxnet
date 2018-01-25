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
public final class PcapStat {

	private long ps_recv;
	
	private long ps_drop;
	
	private long ps_ifdrop;

	/**
	 * Returning recieved packets.
	 * @return recieved packets.
	 */
	public long getPsRecv() {
		return this.ps_recv;
	}

	/**
	 * Returning dropped packets.
	 * @return dropped packets.
	 */
	public long getPsDrop() {
		return this.ps_drop;
	}

	/**
	 * Returning dropped packets by interface.
	 * @return dropped packets by interface.
	 */
	public long getPsIfdrop() {
		return this.ps_ifdrop;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		PcapStat pcapStat = (PcapStat) o;

		if (ps_recv != pcapStat.ps_recv) {
			return false;
		}
		if (ps_drop != pcapStat.ps_drop) {
			return false;
		}
		return ps_ifdrop == pcapStat.ps_ifdrop;
	}

	@Override
	public int hashCode() {
		int result = (int) (ps_recv ^ (ps_recv >>> 32));
		result = 31 * result + (int) (ps_drop ^ (ps_drop >>> 32));
		result = 31 * result + (int) (ps_ifdrop ^ (ps_ifdrop >>> 32));
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("PcapStat{");
		sb.append("ps_recv=").append(ps_recv);
		sb.append(", ps_drop=").append(ps_drop);
		sb.append(", ps_ifdrop=").append(ps_ifdrop);
		sb.append('}');
		return sb.toString();
	}

}
