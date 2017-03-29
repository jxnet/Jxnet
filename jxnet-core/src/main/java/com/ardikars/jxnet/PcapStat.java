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
		return ps_recv;
	}

	/**
	 * Returning dropped packets.
	 * @return dropped packets.
	 */
	public long getPsDrop() {
		return ps_drop;
	}

	/**
	 * Returning dropped packets by interface.
	 * @return dropped packets by interface.
	 */
	public long getPsIfdrop() {
		return ps_ifdrop;
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("[Recieved: ")
				.append(ps_recv)
				.append(", Dropped: ")
				.append(ps_drop)
				.append(", Dropped by Interface: ")
				.append(ps_ifdrop)
				.append("]").toString();
	}

}
