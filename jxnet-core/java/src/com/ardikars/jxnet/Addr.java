
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
