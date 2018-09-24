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

package com.ardikars.jxnet.exception;

/**
 * Platform Not Supported Exception
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.1.0
 */
public class PlatformNotSupportedException extends RuntimeException {

    public PlatformNotSupportedException() {
        super();
    }

    public PlatformNotSupportedException(final String message) {
        super(message);
    }

    public PlatformNotSupportedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PlatformNotSupportedException(final Throwable cause) {
        super(cause);
    }

}
