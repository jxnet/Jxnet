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
@Deprecated
public final class File extends java.io.File {

	private long address;

	/**
	 * Create instance of File.
	 * @param pathname absolute path.
	 */
	private File(String pathname) {
		super(pathname);
	}

	public synchronized long getAddress() {
		return this.address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		File file = (File) o;

		return getAddress() == file.getAddress();
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (int) (getAddress() ^ (getAddress() >>> 32));
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("File{");
		sb.append("address=").append(address);
		sb.append('}');
		return sb.toString();
	}

}
