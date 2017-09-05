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
public final class Pcap {

	private DataLinkType dataLinkType;

	private int snapshotLength;

	private long address;

	private Pcap() {

	}

	public synchronized long getAddress() {
		return this.address;
	}

	/**
	 * Get datalink type.
	 * @return datalink type.
	 */
	public DataLinkType getDataLinkType() {
		if (!this.isClosed() && this.dataLinkType == null) {
			this.dataLinkType = DataLinkType.valueOf((short) Jxnet.PcapDataLink(this));
		}
		return this.dataLinkType;
	}

	/**
	 * Get shapshot length.
	 * @return snapshot length.
	 */
	public int getSnapshotLength() {
		if (!this.isClosed() && this.snapshotLength == 0) {
			this.snapshotLength = Jxnet.PcapSnapshot(this);
		}
		return this.snapshotLength;
	}

	/**
	 * Check pcap handle.
	 * @return true if closed.
	 */
	public boolean isClosed() {
		if (this.address == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Pcap pcap = (Pcap) o;

		return getAddress() == pcap.getAddress();
	}

	@Override
	public int hashCode() {
		return (int) (getAddress() ^ (getAddress() >>> 32));
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Pcap{");
		sb.append("address=").append(address);
		sb.append('}');
		return sb.toString();
	}

}
