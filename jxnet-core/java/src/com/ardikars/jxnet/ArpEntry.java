
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

public class ArpEntry {

    private volatile Addr arp_pa;
    private volatile Addr arp_ha;

    public Addr getArpProtoalAddress() {
        return arp_pa;
    }

    public Addr getArpHardwareAddress() {
        return arp_ha;
    }
}
