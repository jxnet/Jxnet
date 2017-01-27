
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

public class ARPEntry {

    private volatile byte[] arp_pa;
    private volatile byte[] arp_ha;

    public byte[] getProtocolAddress() {
        return arp_pa;
    }

    public byte[] getHardwareAddress() {
        return arp_ha;
    }

    @Override
    public String toString() {
        return Inet4Address.valueOf(arp_pa).toString() + " is at "
                + MacAddress.valueOf(arp_ha).toString();
    }

}
