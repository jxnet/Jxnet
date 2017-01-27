
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

public class Addr {

    private short addr_type;
    private short addr_bits;
    private byte[] data;

    private Addr(short addr_type, short addr_bits, byte[] data) {
        this.addr_type = addr_type;
        this.addr_bits = addr_bits;
        this.data = data;
    }

    public static Addr initialize(short addr_type, short addr_bits, byte[] data) {
        return new Addr(addr_type, addr_bits, data);
    }

    public void setAddrType(short addr_type) {
        this.addr_type = addr_type;
    }

    public void setAddrBits(short addr_bits) {
        this.addr_bits = addr_bits;
    }

    public short getAddrType() {
        return addr_type;
    }

    public short getAddrBits() {
        return addr_bits;
    }

    public byte[] getData() {
        return data;
    }

}
