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

import java.util.Arrays;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class SockAddr {

    public enum Family {

        AF_INET((short) 2, "AF_INET"),
        AF_INET6((short) 10, "AF_INET6"),
        ;

        private short value;
        private String description;

        Family(Short value, String description) {
            this.value = value;
            this.description = description;
        }

        /**
         * Returning address value.
         * @return address value.
         */
        public short getValue() {
            return this.value;
        }

        /**
         * Returning address value description.
         * @return address value description.
         */
        public String getDescription() {
            return this.description;
        }

        /**
         * Getting value type.
         * @param family address value type.
         * @return value type.
         */
        public static Family valueOf(final short family) {
            for (Family f : values()) {
                if (f.getValue() == family) {
                    return f;
                }
            }
            return null;
        }
    }

    private volatile short sa_family;

    private volatile byte[] data;

    private SockAddr() {
        //
    }

    /**
     * Returning address value type.
     * @return address value type.
     */
    public Family getSaFamily() {
        switch (sa_family) {
            case 2:
                return Family.AF_INET;
            case 10:
                return Family.AF_INET6;
            default:
                return null;
        }
    }

    /**
     * Returning bytes address
     * @return bytes address.
     */
    public byte[] getData() {
        return this.data.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SockAddr sockAddr = (SockAddr) o;

        if (sa_family != sockAddr.sa_family) {
            return false;
        }
        return Arrays.equals(getData(), sockAddr.getData());
    }

    @Override
    public int hashCode() {
        int result = (int) sa_family;
        result = 31 * result + Arrays.hashCode(getData());
        return result;
    }

    @Override
    public String toString() {
        Family family = getSaFamily();
        if (family == null) {
            return null;
        }
        switch (family) {
            case AF_INET:
                return Inet4Address.valueOf(data).toString();
            case AF_INET6:
                return Inet6Address.valueOf(data).toString();
            default:
                return null;
        }
    }

}
