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

import com.ardikars.jxnet.util.Pointer;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class File extends java.io.File {

	private Pointer pointer;

	/**
	 * Pointer of File.
	 * @return pointer of File.
	 */
	public Pointer getPointer() {
		return pointer;
	}

	/**
	 * Create instance of File.
	 * @param pathname absolute path.
	 */
	private File(String pathname) {
		super(pathname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		if (obj instanceof BpfProgram) {
			BpfProgram bpf = (BpfProgram) obj;
			return pointer.equals(bpf.getPointer());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 17 * 37 + super.hashCode();
	}

	@Override
	public String toString() {
		return new StringBuilder().append("[Pointer Address: ")
				.append(getPointer().getAddress())
				.append("]").toString();
	}

}
