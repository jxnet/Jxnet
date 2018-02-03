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
public final class PcapPktHdr {

	// This field will be replaced by native code
	private int caplen;
	private int len;
	
	private int tv_sec;
	private long tv_usec;

	/**
	 * Create PcapPktHdr instance.
	 */
	public PcapPktHdr() {
		this.caplen = 0;
		this.len = 0;
		this.tv_sec = 0;
		this.tv_usec = 0;
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
	 * @return PcapPktHdr.
	 */
	public static PcapPktHdr newInstance(final int caplen, final int len, final int tvSec, final long tvUsec) {
		return new PcapPktHdr(caplen, len, tvSec, tvUsec);
	}

	/**
	 * Returning capture length.
	 * @return capture length.
	 */
	public int getCapLen() {
		return this.caplen;
	}

	/**
	 * Returning packet length.
	 * @return packet length.
	 */
	public int getLen() {
		return this.len;
	}

	/**
	 * Returning tv_sec.
	 * @return tv_sec.
	 */
	public int getTvSec() {
		return this.tv_sec;
	}

	/**
	 * Returning tv_usec.
	 * @return tv_usec.
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
	public String toString() {
		final StringBuilder sb = new StringBuilder(94);
		sb.append("PcapPktHdr{caplen=").append(this.caplen);
		sb.append(", len=").append(this.len);
		sb.append(", tv_sec=").append(this.tv_sec);
		sb.append(", tv_usec=").append(this.tv_usec);
		sb.append('}');
		return sb.toString();
	}

}
