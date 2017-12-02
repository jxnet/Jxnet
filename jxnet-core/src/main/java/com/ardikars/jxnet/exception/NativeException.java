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

package com.ardikars.jxnet.exception;

/**
 * Native Exception
 *
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public final class NativeException extends RuntimeException {

	public NativeException() {
		super();
	}

	public NativeException(String message) {
		super(message);
	}

	public NativeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NativeException(Throwable cause) {
		super(cause);
	}
	
}
