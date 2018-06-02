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

import java.nio.ByteBuffer;

/**
 * Callback function used for capturing packets.
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
//@FunctionalInterface // JAVA 8
public interface PcapHandler<T> {

	/**
	 * Next available packet.
	 * @param user arg.
	 * @param h PcapPktHdr.
	 * @param bytes packet buffer.
	 */
	void nextPacket(T user, PcapPktHdr h, ByteBuffer bytes); // Asyncronous
	
}
