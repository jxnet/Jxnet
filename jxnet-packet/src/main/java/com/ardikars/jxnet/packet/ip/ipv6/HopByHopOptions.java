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

package com.ardikars.jxnet.packet.ip.ipv6;

import java.nio.ByteBuffer;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class HopByHopOptions extends Options {


    public HopByHopOptions() {
        super.type = (byte) 0x00;
    }

    public static HopByHopOptions newInstance(final ByteBuffer buffer) {
        HopByHopOptions hopByHopOptions = new HopByHopOptions();
        hopByHopOptions.setNextHeader(buffer.get());
        hopByHopOptions.setExtensionLength((byte)(buffer.get()));
        byte[] options = new byte[Options.FIXED_OPTIONS_LENGTH + Options.LENGTH_UNIT * hopByHopOptions.getExtensionLength()];
        buffer.get(options, 0, options.length);
        hopByHopOptions.setOptions(options);
        hopByHopOptions.nextPacket = buffer.slice();
        return hopByHopOptions;
    }

    public static HopByHopOptions newInstance(final byte[] bytes, final int offset, final int length) {
        return newInstance(ByteBuffer.wrap(bytes, offset, length));
    }

    public static HopByHopOptions newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

}
