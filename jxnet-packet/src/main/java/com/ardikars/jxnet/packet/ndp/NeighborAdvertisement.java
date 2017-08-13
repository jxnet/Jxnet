package com.ardikars.jxnet.packet.ndp;

import com.ardikars.jxnet.Inet6Address;
import com.ardikars.jxnet.packet.Packet;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class NeighborAdvertisement extends Packet {

    public static int HEADER_LENGTH = 20;

    private byte routerFlag;
    private byte solicitedFlag;
    private byte overrideFlag;
    private Inet6Address targetAddress;

    private NeighborDiscoveryOptions options;

    public byte getRouterFlag() {
        return (byte) (this.routerFlag & 0x1);
    }

    public NeighborAdvertisement setRouterFlag(final byte routerFlag) {
        this.routerFlag = (byte) (routerFlag & 0x1);
        return this;
    }

    public byte getSolicitedFlag() {
        return (byte) (this.solicitedFlag & 0x1);
    }

    public NeighborAdvertisement setSolicitedFlag(final byte solicitedFlag) {
        this.solicitedFlag = (byte) (solicitedFlag & 0x1);
        return this;
    }

    public byte getOverrideFlag() {
        return (byte) (this.overrideFlag & 0x1);
    }

    public NeighborAdvertisement setOverrideFlag(final byte overrideFlag) {
        this.overrideFlag = (byte) (overrideFlag & 0x1);
        return this;
    }

    public Inet6Address getTargetAddress() {
        return this.targetAddress;
    }

    public NeighborAdvertisement setTargetAddress(final Inet6Address targetAddress) {
        this.targetAddress = targetAddress;
        return this;
    }

    public List<NeighborDiscoveryOptions.Option> getOptions() {
        return this.options.getOptions();
    }

    public NeighborAdvertisement setOptions(final NeighborDiscoveryOptions options) {
        this.options = options;
        return this;
    }

    public NeighborAdvertisement addOption(final NeighborDiscoveryOptions.OptionType type, final byte[] data) {
        if (this.options != null) {
            this.options.addOptions(type, data);
        } else {
            this.options = new NeighborDiscoveryOptions();
        }
        return this;
    }

    public static NeighborAdvertisement newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static NeighborAdvertisement newInstance(final byte[] bytes, final int offset, final int length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        NeighborAdvertisement na = new NeighborAdvertisement();
        int iscratch;
        iscratch = buffer.getInt();
        na.setRouterFlag((byte) (iscratch >> 31 & 0x1));
        na.setSolicitedFlag((byte) (iscratch >> 30 & 0x1));
        na.setOverrideFlag((byte) (iscratch >> 29 & 0x1));
        byte[] ipv6AddrBuffer = new byte[Inet6Address.IPV6_ADDRESS_LENGTH];
        buffer.get(ipv6AddrBuffer, 0, Inet6Address.IPV6_ADDRESS_LENGTH);
        na.setTargetAddress(Inet6Address.valueOf(ipv6AddrBuffer));
        na.setOptions(NeighborDiscoveryOptions.newInstance(bytes, buffer.position(),
                buffer.limit() - buffer.position()));
        return na;
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
        byte[] data = new byte[HEADER_LENGTH + optionsLength];
        ByteBuffer buffer = ByteBuffer.wrap(data);

        buffer.putInt((this.getRouterFlag() & 0x1) << 31 |
                (this.getSolicitedFlag() & 0x1) << 30 |
                (this.getOverrideFlag() & 0x1) << 29);
        buffer.put(this.getTargetAddress().toBytes());
        if (optionsData != null) {
            buffer.put(optionsData);
        }
        return data;
    }

    @Override
    public String toString() {
        return "NeighborAdvertisement{" +
                "routerFlag=" + routerFlag +
                ", solicitedFlag=" + solicitedFlag +
                ", overrideFlag=" + overrideFlag +
                ", targetAddress=" + targetAddress +
                ", options=" + options +
                '}';
    }

}
