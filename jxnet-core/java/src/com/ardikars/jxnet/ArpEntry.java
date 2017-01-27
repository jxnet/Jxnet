
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

public class ArpEntry {
cd
    private volatile Addr arp_pa;
    private volatile Addr arp_ha;

    private ArpEntry(Addr arp_pa, Addr arp_ha) {
        this.arp_pa = arp_pa;
        this.arp_ha = arp_ha;
    }

    public static ArpEntry initialize(Addr arp_pa, Addr arp_ha) {
        return new ArpEntry(arp_pa, arp_ha);
    }

    public void setArpPa(Addr arp_pa) {
        this.arp_pa = arp_pa;
    }

    public void setArpHa(Addr arp_ha) {
        this.arp_ha = arp_ha;
    }

    public Addr getArpPa() {
        return arp_pa;
    }

    public Addr getArpHa() {
        return arp_ha;
    }

    public Addr getArpProtoalAddress() {
        return arp_pa;
    }

    public Addr getArpHardwareAddress() {
        return arp_ha;
    }

}
