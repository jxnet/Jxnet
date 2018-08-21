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
 * Savefile descriptor.
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class PcapDumper implements Cloneable {

	private long address;

	private PcapDumper() {
		//
	}

	/**
	 * Getting pointer address
	 * @return returns pointer address.
	 */
	public long getAddress() {
		synchronized (this) {
			return this.address;
		}
	}

	/**
	 * Returns true if PcapDumper is closed.
	 * @return returns true if PcapDumper is closed, false otherwise.
	 */
	public boolean isClosed() {
		return this.getAddress() == 0;
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
	public PcapDumper clone() throws CloneNotSupportedException {
		return (PcapDumper) super.clone();
	}

	@Override
	public String toString() {
		return new StringBuilder("PcapDumper{")
				.append("address=").append(this.getAddress())
				.append('}')
				.toString();
	}

}
