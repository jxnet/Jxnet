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
 * Operation Not Supported Exception
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.1.5
 */
public class OperationNotSupportedException extends RuntimeException {

    private static final long serialVersionUID = 4918064360828376142L;

    public OperationNotSupportedException() {
        this("");
    }

    public OperationNotSupportedException(final String s) {
        this(s, new RuntimeException(s));
    }

    public OperationNotSupportedException(final Throwable throwable) {
        this(throwable.getMessage(), throwable);
    }

    public OperationNotSupportedException(final String s, final Throwable throwable) {
        super(s, throwable);
    }

}
