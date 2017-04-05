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

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public enum DataLinkType {

    /**
     * Ethernet (10Mb, 100Mb, 1000Mb, and up): 1
     */
    EN10MB((short) 1, "Ethernet"),

    /**
     * DOCSIS MAC frames: 143
     */
    DOCSIS((short) 143, "DOCSIS"),

    /**
     * FDDI: 10
     */
    FDDI((short) 10, "FDDI"),

    /**
     * 802.5 Token Ring: 6
     */
    IEEE802((short) 6, "802.5 Token Ring"),

    /**
     * IEEE 802.11 wireless: 105
     */
    IEEE802_11((short) 105, "IEEE 802.11 Wireless"),

    /**
     * Linux cooked-mode capture (SLL): 113
     */
    LINUX_SLL((short) 113, "LINUX_SSL"),

    /**
     * Point-to-point Protocol: 9
     */
    PPP((short) 9, "PPP"),

    /**
     * PPP over serial with HDLC encapsulation: 50
     */
    PPP_SERIAL((short) 50, "PPP_SERIAL"),

    /**
     * Null (BSD loopback encapsulation): 0
     */
    NULL((short) 0, "NULL"),

    /**
     *
     */
    WLAN((short) 127, "Wlan"),

    /**
     * Unknown
     */
    UNKNOWN((short) -1, "Unknown")
    ;

    private short value;
    private String description;

    private DataLinkType(Short value, String description) {
        this.value = value;
        this.description = description;
    }

    public short getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static DataLinkType valueOf(final short dataLinkType) {
        for(DataLinkType f : values()) {
            if(f.getValue() == dataLinkType) {
                return f;
            }
        }
        return null;
    }

}
