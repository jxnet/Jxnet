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
public final class BpfProgram implements Cloneable {

	/**
	 * Bpf compile mode
	 */
	public enum BpfCompileMode {

		OPTIMIZE(1), NON_OPTIMIZE(0);

		private final int value;

		BpfCompileMode(final int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}

	}
	
	private native void initBpfProgram();

	private long address;

	/**
	 * Create instance of BpfProgram and initialize it.
	 */
	public BpfProgram() {
		this.initBpfProgram();
	}

	/**
	 * Get Bpf Program pointer address.
	 * @return pointer address.
	 */
	public long getAddress() {
		synchronized (this) {
			return this.address;
		}
	}

	/**
	 * Check bpf handle.
	 * @return true if closed.
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

		final BpfProgram that = (BpfProgram) o;

		return this.getAddress() == that.getAddress();
	}

	@Override
	public int hashCode() {
		return (int) (this.getAddress() ^ (this.getAddress() >>> 32));
	}

	@Override
	protected Object clone() {
		BpfProgram bpfProgram = new BpfProgram();
		bpfProgram.address = this.address;
		return bpfProgram;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BpfProgram{");
		sb.append("address=").append(this.getAddress());
		sb.append('}');
		return sb.toString();
	}

	static {
		try {
			Class.forName("com.ardikars.jxnet.Jxnet");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
