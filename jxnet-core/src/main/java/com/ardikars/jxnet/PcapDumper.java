/**
 * Copyright (C) 2015-2018 Jxnet
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

import com.ardikars.common.annotation.Mutable;
import com.ardikars.common.util.Validate;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Savefile descriptor.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Mutable(blocking = true)
public final class PcapDumper implements Cloneable {

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

	private long address;

	private PcapDumper() {
		//
	}

	/**
	 * Getting pointer address
	 * @return returns pointer address.
	 */
	public long getAddress() {
		if (this.lock.readLock().tryLock()) {
			try {
				return this.address;
			} finally {
				this.lock.readLock().unlock();
			}
		}
		return 0;
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

	public static final class Builder implements com.ardikars.common.util.Builder<PcapDumper, Void> {

		private Pcap pcap;
		private String fileName;

		/**
		 * Pcap handle.
		 * @param pcap pcap handle.
		 * @return resturns PcapDumper builder.
		 */
		public Builder pcap(Pcap pcap) {
			this.pcap = pcap;
			return this;
		}

		/**
		 * File name.
		 * @param fileName file name.
		 * @return resturns PcapDumper builder.
		 */
		public Builder fileName(String fileName) {
			this.fileName = fileName;
			return this;
		}

		@Override
		public PcapDumper build() {
			Validate.notIllegalArgument(pcap != null,
					new IllegalArgumentException("Pcap should be not null."));
			Validate.notIllegalArgument(fileName != null && fileName.equals(""),
					new IllegalArgumentException("File name should be not null or empty."));
			return Jxnet.PcapDumpOpen(pcap, fileName);
		}

		@Override
		public PcapDumper build(Void value) {
			throw new UnsupportedOperationException();
		}

	}

}
