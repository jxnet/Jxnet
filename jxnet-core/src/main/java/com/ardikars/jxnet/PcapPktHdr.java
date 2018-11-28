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

/**
 * Header of a packet in the dump file.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Mutable(volatiles = { "caplen", "len", "tv_sec", "tv_usec" })
public final class PcapPktHdr implements Cloneable {

	// This field value will be replaced by native code
	private volatile int caplen;
	private volatile int len;
	
	private volatile int tv_sec;
	private volatile long tv_usec;

	/**
	 * Create PcapPktHdr instance.
	 */
	public PcapPktHdr() {
		//
	}

	/**
	 * Create PcapPktHdr instance.
	 * @param caplen capture length.
	 * @param len length.
	 * @param tvSec tv_sec.
	 * @param tvUsec tv_usec.
	 */
	public PcapPktHdr(final int caplen, final int len, final int tvSec, final long tvUsec) {
		this.caplen = caplen;
		this.len = len;
		this.tv_sec = tvSec;
		this.tv_usec = tvUsec;
	}

	/**
	 * Create new PcapPktHdr instance.
	 * @param caplen capture length.
	 * @param len length.
	 * @param tvSec tv_sec.
	 * @param tvUsec tv_usec.
	 * @return returns PcapPktHdr.
	 */
	public static PcapPktHdr newInstance(final int caplen, final int len, final int tvSec, final long tvUsec) {
		return new PcapPktHdr(caplen, len, tvSec, tvUsec);
	}

	/**
	 * Getting capture length.
	 * @return returns capture length.
	 */
	public int getCapLen() {
		return this.caplen;
	}

	/**
	 * Getting packet length.
	 * @return returns packet length.
	 */
	public int getLen() {
		return this.len;
	}

	/**
	 * Getting tv_sec.
	 * @return returns tv_sec.
	 */
	public int getTvSec() {
		return this.tv_sec;
	}

	/**
	 * Getting tv_usec.
	 * @return returns tv_usec.
	 */
	public long getTvUsec() {
		return this.tv_usec;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final PcapPktHdr that = (PcapPktHdr) o;

		if (this.caplen != that.caplen) {
			return false;
		}
		if (this.len != that.getLen()) {
			return false;
		}
		if (this.tv_sec != that.tv_sec) {
			return false;
		}
		return this.tv_usec == that.tv_usec;
	}

	@Override
	public int hashCode() {
		int result = this.caplen;
		result = 31 * result + this.getLen();
		result = 31 * result + this.getTvSec();
		result = 31 * result + (int) (this.getTvSec() ^ (this.getTvUsec() >>> 32));
		return result;
	}

	@Override
	public PcapPktHdr clone() throws CloneNotSupportedException {
		return  (PcapPktHdr) super.clone();
	}

	/**
	 * Copy this instance.
	 * @return returns new {@link PcapPktHdr} instance.
	 */
	public PcapPktHdr copy() {
		PcapPktHdr pktHdr = new PcapPktHdr();
		pktHdr.caplen = this.caplen;
		pktHdr.len = this.len;
		pktHdr.tv_sec = this.tv_sec;
		pktHdr.tv_usec = this.tv_usec;
		return pktHdr;
	}

	@Override
	public String toString() {
		return new StringBuilder(94)
				.append("PcapPktHdr{caplen=").append(this.caplen)
				.append(", len=").append(this.len)
				.append(", tv_sec=").append(this.tv_sec)
				.append(", tv_usec=").append(this.tv_usec)
				.append('}')
				.toString();
	}

}
