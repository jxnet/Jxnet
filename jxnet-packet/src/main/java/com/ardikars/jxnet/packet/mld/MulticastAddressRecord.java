package com.ardikars.jxnet.packet.mld;

import com.ardikars.jxnet.Inet6Address;

import java.nio.ByteBuffer;

public class MulticastAddressRecord {

    public static final int FIXED_HEADER_LENGTH = 4 + Inet6Address.IPV6_ADDRESS_LENGTH;

    private byte recordType;
    private byte auxDataLength;
    private short numberOfSources;
    private Inet6Address multicastAddress;

    public byte getRecordType() {
        return this.recordType;
    }

    public MulticastAddressRecord setRecordType(final byte recordType) {
        this.recordType = recordType;
        return this;
    }

    public byte getAuxDataLength() {
        return auxDataLength;
    }

    public MulticastAddressRecord setAuxDataLength(final byte auxDataLength) {
        this.auxDataLength = auxDataLength;
        return this;
    }

    public short getNumberOfSources() {
        return numberOfSources;
    }

    public MulticastAddressRecord setNumberOfSources(final short numberOfSources) {
        this.numberOfSources = numberOfSources;
        return this;
    }

    public Inet6Address getMulticastAddress() {
        return multicastAddress;
    }

    public MulticastAddressRecord setMulticastAddress(final Inet6Address multicastAddress) {
        this.multicastAddress = multicastAddress;
        return this;
    }

    public static MulticastAddressRecord newInstance(final ByteBuffer buffer) {
        MulticastAddressRecord multicastAddressRecord = new MulticastAddressRecord();
        multicastAddressRecord.setRecordType(buffer.get());
        multicastAddressRecord.setAuxDataLength(buffer.get());
        multicastAddressRecord.setNumberOfSources(buffer.getShort());
        byte[] ipv6 = new byte[Inet6Address.IPV6_ADDRESS_LENGTH];
        buffer.get(ipv6, 0, ipv6.length);
        multicastAddressRecord.setMulticastAddress(Inet6Address.valueOf(ipv6));
        return multicastAddressRecord;
    }

    public static MulticastAddressRecord newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static MulticastAddressRecord newInstance(final byte[] bytes, final int offset, final int length) {
        return newInstance(ByteBuffer.wrap(bytes, offset, length));
    }

    public byte[] bytes() {
        byte[] data = new byte[FIXED_HEADER_LENGTH];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.put(this.getRecordType());
        buffer.put(this.getAuxDataLength());
        buffer.putShort(this.getNumberOfSources());
        buffer.put(this.getMulticastAddress().toBytes());
        return data;
    }

    public ByteBuffer buffer() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(FIXED_HEADER_LENGTH);
        buffer.put(this.getRecordType());
        buffer.put(this.getAuxDataLength());
        buffer.putShort(this.getNumberOfSources());
        buffer.put(this.getMulticastAddress().toBytes());
        return buffer;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MulticastAddressRecord{");
        sb.append("recordType=").append(recordType);
        sb.append(", auxDataLength=").append(auxDataLength);
        sb.append(", numberOfSources=").append(numberOfSources);
        sb.append(", multicastAddress=").append(multicastAddress);
        sb.append('}');
        return sb.toString();
    }

}
