/**
 * Copyright (C) 2017  Ardika Rommy Sanjaya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ardikars.jxnet;

public class ArpEntry {

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

    @Override
    public String toString() {
        return Inet4Address.valueOf(arp_pa.getData()).toString() + " is at "
                + MacAddress.valueOf(arp_ha.getData());
    }

}
