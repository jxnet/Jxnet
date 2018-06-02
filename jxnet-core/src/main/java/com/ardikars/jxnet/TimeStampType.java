/**
 * Copyright (C) 2017-2018  Ardika Rommy Sanjaya <contact@ardikars.com>
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
 * @since 1.1.5
 */
public enum TimeStampType {

    PCAP_TSTAMP_HOST(0),
    /* host-provided, unknown characteristics */
    PCAP_TSTAMP_HOST_LOWPREC(1),
    /* host-provided, high precision */
    PCAP_TSTAMP_HOST_HIPREC(2),
    /* device-provided, synced with the system clock */
    PCAP_TSTAMP_ADAPTER(3),
    /* device-provided, not synced with the system clock */
    PCAP_TSTAMP_ADAPTER_UNSYNCED(4);

    private final int type;

    TimeStampType(final int type) {
        this.type = type;
    }

    /**
     * Returns a time stamp type.
     * @return returns time stamp type.
     */
    public int getType() {
        return this.type;
    }

    /**
     * Get time stamp type from value.
     * @param type time stamp value.
     * @return returns time stamp type.
     */
    public static TimeStampType valueOf(final int type) {
        for (final TimeStampType timeStampType : values()) {
            if (timeStampType.getType() == type) {
                return timeStampType;
            }
        }
        return null;
    }

}
