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

package com.ardikars.jxnet.packet.ndp;

import com.ardikars.jxnet.packet.Packet;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class NeighborDiscoveryOptions extends Packet {

    private List<Option> options = new ArrayList<Option>();

    public enum OptionType {

        SOURCE_LINK_LAYER_ADDRESS((byte)1),
        TARGET_LINK_LAYER_ADDRESS((byte)2),
        PREFIX_INFORMATION((byte)3),
        REDIRECT_HEADER((byte)4),
        MTU((byte)5);

        private byte type;

        private OptionType(byte type) {
            this.type = type;
        }

        public byte getValue() {
            return this.type;
        }

        public static OptionType valueOf(final byte type) {
            for (OptionType optionType : values()) {
                if (optionType.type == type) {
                    return optionType;
                }
            }
            return null;
        }

    }

    public final class Option {

        private OptionType type;
        private byte length;
        private byte[] data;

        private Option(OptionType type, byte[] data) {
            this.type = type;
            this.data = data;
            this.length = (byte) ((this.data.length + 2 + 7) >> 3);
        }

        public OptionType getType() {
            return this.type;
        }

        public byte getLength() {
            return this.length;
        }

        public byte[] getData() {
            return this.data;
        }

        @Override
        public String toString() {
            return new StringBuilder("[")
                    .append("Type: ")
                    .append(this.getType())
                    .append(", Data: ")
                    .append(Arrays.toString(this.getData()))
                    .append("]").toString();
        }

    }

    public NeighborDiscoveryOptions addOptions(OptionType type, byte[] data) {
        options.add(new Option(type, data));
        return this;
    }

    public static NeighborDiscoveryOptions newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static NeighborDiscoveryOptions newInstance(final byte[] bytes, final int offset, final int length) {
        NeighborDiscoveryOptions neighborDiscoveryOptions = new NeighborDiscoveryOptions();
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        while (buffer.hasRemaining()) {
            OptionType type = OptionType.valueOf(buffer.get());
            if (!buffer.hasRemaining()) {
                break;
            }
            byte lengthField = buffer.get();
            int dataLength = lengthField * 8;
            if (dataLength < 2) {
                break;
            }
            dataLength -= 2;
            if (buffer.remaining() < dataLength) {
                break;
            }
            byte[] data = new byte[dataLength];
            buffer.get(data, 0, dataLength);
            neighborDiscoveryOptions.addOptions(type, data);
        }
        return neighborDiscoveryOptions;
    }

    public boolean hasOptions() {
        return !this.options.isEmpty();
    }

    public List<Option> getOptions() {
        return this.options;
    }

    @Override
    public byte[] toBytes() {
        int length = 0;
        for (Option option : this.options) {
            length += (option.getLength() << 3);
        }
        byte[] data = new byte[length];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        for (Option option : this.options) {
            buffer.put(option.getType().getValue());
            buffer.put(option.getLength());
            buffer.put(option.getData());
            int paddingLength = (option.getLength() << 3) - (option.getData().length + 2);
            for (int i=0; i<paddingLength; i++) {
                buffer.put((byte) 0);
            }
        }
        return data;
    }

}
