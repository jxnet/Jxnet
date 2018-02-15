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
public final class PcapDumper implements Cloneable {

	private long address;

	private PcapDumper() {
		//
	}

	/**
	 * Get pointer address
	 * @return pointer address.
	 */
	public long getAddress() {
		synchronized (this) {
			return this.address;
		}
	}

	/**
	 * Return true if PcapDumper is closed.
	 * @return true if PcapDumper is closed, false otherwise.
	 */
	public boolean isClosed() {
		if (this.getAddress() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final PcapDumper that = (PcapDumper) o;

		return this.getAddress() == that.getAddress();
	}

	@Override
	public int hashCode() {
		return (int) (this.getAddress() ^ (this.getAddress() >>> 32));
	}

	@Override
	protected Object clone() {
		PcapDumper pcapDumper = new PcapDumper();
		pcapDumper.address = this.address;
		return pcapDumper;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("PcapDumper{");
		sb.append("address=").append(this.getAddress());
		sb.append('}');
		return sb.toString();
	}

}
