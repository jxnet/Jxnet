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

import com.ardikars.common.annotation.Incubating;

/**
 * Unknown Netmask Exception
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.5.0
 */
@Incubating
public class UnknownNetmaskException extends RuntimeException {

    private static final long serialVersionUID = -3436588540149037097L;

    public UnknownNetmaskException() {
        super();
    }

    public UnknownNetmaskException(final String message) {
        super(message);
    }

    public UnknownNetmaskException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnknownNetmaskException(final Throwable cause) {
        super(cause);
    }

}
