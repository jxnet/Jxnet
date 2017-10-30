package com.ardikars.jxnet.packet.ndp;

import com.ardikars.jxnet.packet.Packet;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class RouterAdvertisement extends Packet {

    public static final byte HEADER_LENGTH = 12;

    private byte currentHopLimit;
    private byte manageFlag;
    private byte otherFlag;
    private short routerLifetime;
    private int reachableTime;
    private int retransmitTimer;

    private NeighborDiscoveryOptions options;

    public byte getCurrentHopLimit() {
        return this.currentHopLimit;
    }

    public RouterAdvertisement setCurrentHopLimit(final byte currentHopLimit) {
        this.currentHopLimit = currentHopLimit;
        return this;
    }

    public byte getManageFlag() {
        return (byte) (this.manageFlag & 0x1);
    }

    public RouterAdvertisement setManageFlag(final byte manageFlag) {
        this.manageFlag = (byte) (this.manageFlag & 0x1);
        return this;
    }

    public byte getOtherFlag() {
        return (byte) (this.otherFlag & 0x1);
    }

    public RouterAdvertisement setOtherFlag(final byte otherFlag) {
        this.otherFlag = (byte) (this.otherFlag & 0x1);
        return this;
    }

    public short getRouterLifetime() {
        return this.routerLifetime;
    }

    public RouterAdvertisement setRouterLifetime(final short routerLifetime) {
        this.routerLifetime = routerLifetime;
        return this;
    }

    public int getReachableTime() {
        return this.reachableTime;
    }

    public RouterAdvertisement setReachableTime(final int reachableTime) {
        this.reachableTime = reachableTime;
        return this;
    }

    public int getRetransmitTimer() {
        return this.retransmitTimer;
    }

    public RouterAdvertisement setRetransmitTimer(final int retransmitTimer) {
        this.retransmitTimer = retransmitTimer;
        return this;
    }

    public List<NeighborDiscoveryOptions.Option> getOptions() {
        return this.options.getOptions();
    }

    public RouterAdvertisement setOptions(final NeighborDiscoveryOptions options) {
        this.options = options;
        return this;
    }

    public RouterAdvertisement addOption(final NeighborDiscoveryOptions.OptionType type, final byte[] data) {
        if (this.options != null) {
            this.options.addOptions(type, data);
        } else {
            this.options = new NeighborDiscoveryOptions();
        }
        return this;
    }

    public static RouterAdvertisement newInstance(final ByteBuffer buffer) {
        int bscratch;
        RouterAdvertisement ra = new RouterAdvertisement();
        ra.setCurrentHopLimit(buffer.get());
        bscratch = buffer.get();
        ra.setManageFlag((byte) ((bscratch >> 7) & 0x1));
        ra.setOtherFlag((byte) ((bscratch >> 6) & 0x1));
        ra.setRouterLifetime(buffer.getShort());
        ra.setReachableTime(buffer.getInt());
        ra.setRetransmitTimer(buffer.getInt());
        ra.setOptions(NeighborDiscoveryOptions.newInstance(buffer.slice()));
        return ra;
    }

    public static RouterAdvertisement newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static RouterAdvertisement newInstance(final byte[] bytes, final int offset, final int length) {
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

        bb.put(this.getCurrentHopLimit());
        bb.put((byte) ((this.getManageFlag() & 0x1) << 7 | (this.getOtherFlag() & 0x1) << 6));
        bb.putShort(getRouterLifetime());
        bb.putInt(getReachableTime());
        bb.putInt(getRetransmitTimer());
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

        buffer.put(this.getCurrentHopLimit());
        buffer.put((byte) ((this.getManageFlag() & 0x1) << 7 | (this.getOtherFlag() & 0x1) << 6));
        buffer.putShort(getRouterLifetime());
        buffer.putInt(getReachableTime());
        buffer.putInt(getRetransmitTimer());
        if (optionsData != null) {
            buffer.put(optionsData);
        }
        return buffer;
    }

    @Override
    public String toString() {
        return "RouterAdvertisement{" +
                "currentHopLimit=" + currentHopLimit +
                ", manageFlag=" + manageFlag +
                ", otherFlag=" + otherFlag +
                ", routerLifetime=" + routerLifetime +
                ", reachableTime=" + reachableTime +
                ", retransmitTimer=" + retransmitTimer +
                ", options=" + options +
                '}';
    }

}
