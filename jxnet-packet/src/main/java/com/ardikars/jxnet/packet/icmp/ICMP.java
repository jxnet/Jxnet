package com.ardikars.jxnet.packet.icmp;

import com.ardikars.jxnet.packet.Packet;

import java.nio.ByteBuffer;

public class ICMP extends Packet {

    public static int ICMPV4_HEADER_LENGTH = 4;

    private ICMPTypeAndCode typeAndCode;
    private short checksum;

    /**
     * ICMP payload
     */
    private byte[] payload;

    public ICMPTypeAndCode getTypeAndCode() {
        return this.typeAndCode;
    }

    public ICMP setTypeAndCode(ICMPTypeAndCode typeAndCode) {
        this.typeAndCode = typeAndCode;
        return this;
    }

    public short getChecksum() {
        return (short) (this.checksum & 0xffff);
    }

    public ICMP setChecksum(short checksum) {
        this.checksum = (short) (checksum & 0xffff);
        return this;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public ICMP setPayload(byte[] payload) {
        this.payload = payload;
        return this;
    }

    @Override
    public Packet setPacket(Packet packet) {
        return this.setPayload(packet.toBytes());
    }

    @Override
    public Packet getPacket() {
        return null;
    }

    @Override
    public byte[] toBytes() {
        byte[] data = new byte[ICMPV4_HEADER_LENGTH + ((this.getPayload() == null) ? 0 : this.getPayload().length)];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.put(this.getTypeAndCode().getType());
        buffer.put(this.getTypeAndCode().getCode());
        buffer.putShort(this.getChecksum());
        return data;
    }
}
