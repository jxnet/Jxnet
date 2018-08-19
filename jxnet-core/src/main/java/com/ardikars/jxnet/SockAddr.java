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

import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.net.Inet6Address;

import java.util.Arrays;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class SockAddr implements Cloneable {

    public enum Family {

        AF_INET((short) 2, "AF_INET"),
        AF_INET6((short) 10, "AF_INET6"),
        UNKNOWN((short) -1, "UNKNOWN");

        private short value;
        private String description;

        Family(final Short value, final String description) {
            this.value = value;
            this.description = description;
        }

        /**
         * Returns short address value of SockAddr.
         * @return returns short address value.
         */
        public short getValue() {
            return this.value;
        }

        /**
         * Returns SockAddr description.
         * @return returns SockAddr description.
         */
        public String getDescription() {
            return this.description;
        }

        /**
         * Getting value type.
         * @param family address value type.
         * @return returns value type.
         */
        public static Family valueOf(final short family) {
            for (final Family f : values()) {
                if (f.getValue() == family) {
                    return f;
                }
            }
            return Family.UNKNOWN;
        }
    }

    private volatile short sa_family;

    private volatile byte[] data = new byte[0];

    protected SockAddr() {
        //
    }

    /**
     * Returns SockAddr family type.
     * @return returns family type.
     */
    public Family getSaFamily() {
        Family result = Family.UNKNOWN;
        if (this.sa_family == 2) {
            result = Family.AF_INET;
        } else if (this.sa_family == 10) {
            result = Family.AF_INET6;
        }
        return result;
    }

    /**
     * Returns bytes address of SockAddr.
     * @return returns bytes address.
     */
    public byte[] getData() {
        byte[] data;
        if (this.data != null) {
            data = new byte[this.data.length];
        } else {
            data = new byte[0];
        }
        if (data.length == 0) {
            Family family = getSaFamily();
            switch (family) {
                case AF_INET:
                    this.data = new byte[Inet4Address.IPV4_ADDRESS_LENGTH];
                    break;
                case AF_INET6:
                    this.data = new byte[Inet6Address.IPV6_ADDRESS_LENGTH];
                    break;
                default:
                    this.data = new byte[Inet4Address.IPV4_ADDRESS_LENGTH];
            }
        }
        System.arraycopy(this.data, 0, data, 0, data.length);
        return data;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final SockAddr sockAddr = (SockAddr) o;

        if (this.sa_family != sockAddr.sa_family) {
            return false;
        }
        return Arrays.equals(this.data, sockAddr.getData());
    }

    @Override
    public int hashCode() {
        int result = (int) this.sa_family;
        result = 31 * result + Arrays.hashCode(this.data);
        return result;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        SockAddr sockAddr = (SockAddr) super.clone();
        return sockAddr;
    }

    @Override
    public String toString() {
        final Family family = this.getSaFamily();
        switch (family) {
            case AF_INET:
                return Inet4Address.valueOf(this.getData()).toString();
            case AF_INET6:
                return Inet6Address.valueOf(this.getData()).toString();
            default:
                return "";
        }
    }

}
