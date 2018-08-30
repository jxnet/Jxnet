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

package com.ardikars.jxnet.exception;

/**
 * Pcap Dumper Close Exception
 *
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public class PcapDumperCloseException extends RuntimeException {
	
	public PcapDumperCloseException() {
		super();
	}
	
	public PcapDumperCloseException(final String message) {
		super(message);
	}
	
	public PcapDumperCloseException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public PcapDumperCloseException(final Throwable cause) {
		super(cause);
	}
	
}
