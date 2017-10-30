package com.ardikars.jxnet.packet.mld;

import com.ardikars.jxnet.packet.Packet;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MulticastListenerReportV2 extends Packet {

    //reserved 2 bytes
    private short numberOfMulticastAddressRecords;
    private List<MulticastAddressRecord> multicastAddressRecords;

    public short getNumberOfMulticastAddressRecords() {
        return numberOfMulticastAddressRecords;
    }

    public void setNumberOfMulticastAddressRecords(short numberOfMulticastAddressRecords) {
        this.numberOfMulticastAddressRecords = numberOfMulticastAddressRecords;
    }

    public List<MulticastAddressRecord> getMulticastAddressRecords() {
        return multicastAddressRecords;
    }

    public void setMulticastAddressRecords(List<MulticastAddressRecord> multicastAddressRecords) {
        this.multicastAddressRecords = multicastAddressRecords;
    }

    public static MulticastListenerReportV2 newInstance(final ByteBuffer buffer) {
        MulticastListenerReportV2 multicastListenerReportV2 = new MulticastListenerReportV2();
        buffer.getShort(); // reserved
        multicastListenerReportV2.setNumberOfMulticastAddressRecords(buffer.getShort());
        List<MulticastAddressRecord> multicastAddressRecords = new ArrayList<>();
        for (int i=0; i<multicastListenerReportV2.getNumberOfMulticastAddressRecords(); i++) {
            byte[] bytes = new byte[MulticastAddressRecord.FIXED_HEADER_LENGTH];
            buffer.get(bytes, 0, bytes.length);
            multicastAddressRecords
                    .add(MulticastAddressRecord.newInstance(bytes));
        }
        multicastListenerReportV2.setMulticastAddressRecords(multicastAddressRecords);
        return multicastListenerReportV2;
    }

    public static MulticastListenerReportV2 newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static MulticastListenerReportV2 newInstance(final byte[] bytes, final int offset, final int length) {
        return newInstance(ByteBuffer.wrap(bytes, offset, length));
    }

    @Override
    public byte[] bytes() {
        byte[] data = new byte[this.multicastAddressRecords.size() * MulticastAddressRecord.FIXED_HEADER_LENGTH + 4];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.putShort((short)0x0); // reserved
        buffer.putShort(this.getNumberOfMulticastAddressRecords());
        for (MulticastAddressRecord multicastAddressRecord: this.getMulticastAddressRecords()) {
            buffer.put(multicastAddressRecord.buffer());
        }
        return data;
    }

    @Override
    public ByteBuffer buffer() {
        ByteBuffer buffer = ByteBuffer
                .allocate(this.multicastAddressRecords.size() * MulticastAddressRecord.FIXED_HEADER_LENGTH + 4);
        buffer.putShort((short)0x0); // reserved
        buffer.putShort(this.getNumberOfMulticastAddressRecords());
        for (MulticastAddressRecord multicastAddressRecord: this.getMulticastAddressRecords()) {
            buffer.put(multicastAddressRecord.buffer());
        }
        return buffer;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MulticastListenerReportV2{");
        sb.append("numberOfMulticastAddressRecords=").append(numberOfMulticastAddressRecords);
        sb.append(", multicastAddressRecords=").append(multicastAddressRecords);
        sb.append('}');
        return sb.toString();
    }

}
