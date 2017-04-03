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
public final class BpfProgram {
	
	private native void initBpfProgram();

	private long address;

	/**
	 * Create instance ob BpfProgram and initialize it.
	 */
	public BpfProgram() {
		this.initBpfProgram();
	}

	public long getAddress() {
		return this.address;
	}

	public boolean isClosed() {
		if (this.address == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj.getClass() != this.getClass())
			return false;
		if (obj instanceof BpfProgram) {
			BpfProgram bpf = (BpfProgram) obj;
			return this.address == bpf.getAddress();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 17 * 37 +
				((int) (this.address ^ (this.address >> 32)));
	}

	@Override
	public String toString() {
		return new StringBuilder().append("[Pointer Address: ")
				.append(this.address)
				.append("]").toString();
	}
	
	static {
		try {
			Class.forName("com.ardikars.jxnet.Jxnet");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
