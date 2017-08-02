package com.ardikars.jxnet.packet.ndp;

import com.ardikars.jxnet.Inet6Address;
import com.ardikars.jxnet.packet.Packet;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class Redirect extends Packet {

    public static final byte HEADER_LENGTH = 36;

    private Inet6Address targetAddress;
    private Inet6Address destinationAddress;

    private NeighborDiscoveryOptions options;

    public Inet6Address getTargetAddress() {
        return this.targetAddress;
    }

    public Redirect setTargetAddress(final Inet6Address targetAddress) {
        this.targetAddress = targetAddress;
        return this;
    }

    public Inet6Address getDestinationAddress() {
        return this.destinationAddress;
    }

    public Redirect setDestinationAddress(final Inet6Address destinationAddress) {
        this.destinationAddress = destinationAddress;
        return this;
    }

    public List<NeighborDiscoveryOptions.Option> getOptions() {
        return this.options.getOptions();
    }

    public Redirect setOptions(final NeighborDiscoveryOptions options) {
        this.options = options;
        return this;
    }

    public Redirect addOption(final NeighborDiscoveryOptions.OptionType type, final byte[] data) {
        if (this.options != null) {
            this.options.addOptions(type, data);
        } else {
            this.options = new NeighborDiscoveryOptions();
        }
        return this;
    }

    public static Redirect newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static Redirect newInstance(final byte[] bytes, final int offset, final int length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        Redirect redirect = new Redirect();
        buffer.getInt();
        byte[] target = new byte[Inet6Address.IPV6_ADDRESS_LENGTH];
        buffer.get(target, 0, Inet6Address.IPV6_ADDRESS_LENGTH);
        redirect.setTargetAddress(Inet6Address.valueOf(target));
        byte[] destination = new byte[Inet6Address.IPV6_ADDRESS_LENGTH];
        buffer.get(destination, 0, Inet6Address.IPV6_ADDRESS_LENGTH);
        redirect.setDestinationAddress(Inet6Address.valueOf(destination));
        redirect.setOptions(NeighborDiscoveryOptions.newInstance(bytes, buffer.position(),
                buffer.limit() - buffer.position()));
        return redirect;
    }

    @Override
    public byte[] toBytes() {
        byte[] optionsData = null;
        if (this.options.hasOptions()) {
            optionsData = this.options.toBytes();
        }
        int optionsLength = 0;
        if (optionsData != null) {
            optionsLength = optionsData.length;
        }
        final byte[] data = new byte[HEADER_LENGTH + optionsLength];
        final ByteBuffer bb = ByteBuffer.wrap(data);
        bb.putInt(0);
        bb.put(this.targetAddress.toBytes());
        bb.put(this.destinationAddress.toBytes());
        if (optionsData != null) {
            bb.put(optionsData);
        }
        return data;
    }

}
