package com.ardikars.jxnet.packet.ndp;

import com.ardikars.jxnet.packet.Packet;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class RouterSolicitation extends Packet {

    public static int HEADER_LENGTH = 4;

    private NeighborDiscoveryOptions options;

    public List<NeighborDiscoveryOptions.Option> getOptions() {
        return this.options.getOptions();
    }

    public RouterSolicitation setOptions(final NeighborDiscoveryOptions options) {
        this.options = options;
        return this;
    }

    public RouterSolicitation addOption(final NeighborDiscoveryOptions.OptionType type, final byte[] data) {
        if (this.options != null) {
            this.options.addOptions(type, data);
        } else {
            this.options = new NeighborDiscoveryOptions();
        }
        return this;
    }

    public static RouterSolicitation newInstance(final ByteBuffer buffer) {
        buffer.getInt(); // reserve
        RouterSolicitation rs = new RouterSolicitation();
        rs.setOptions(NeighborDiscoveryOptions.newInstance(buffer.slice()));
        return rs;
    }

    public static RouterSolicitation newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static RouterSolicitation newInstance(final byte[] bytes, final int offset, final int length) {
        return newInstance(ByteBuffer.wrap(bytes, offset, length));
    }

    @Override
    public byte[] bytes() {
        ByteBuffer optionsData = null;
        if (this.options.hasOptions()) {
            optionsData = this.options.buffer();
        }
        int optionsLength = 0;
        if (optionsData != null) {
            optionsLength = optionsData.capacity();
        }
        final byte[] data = new byte[HEADER_LENGTH + optionsLength];
        final ByteBuffer bb = ByteBuffer.wrap(data);
        bb.putInt(0);
        if (optionsData != null) {
            bb.put(optionsData);
        }
        return data;
    }

    @Override
    public ByteBuffer buffer() {
        ByteBuffer optionsData = null;
        if (this.options.hasOptions()) {
            optionsData = this.options.buffer();
        }
        int optionsLength = 0;
        if (optionsData != null) {
            optionsLength = optionsData.capacity();
        }
        final ByteBuffer buffer = ByteBuffer.allocateDirect(HEADER_LENGTH + optionsLength);
        buffer.putInt(0);
        if (optionsData != null) {
            buffer.put(optionsData);
        }
        return buffer;
    }

    @Override
    public String toString() {
        return "RouterSolicitation{" +
                "options=" + options +
                '}';
    }

}
