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

import com.ardikars.jxnet.util.FormatUtils;

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

        private Family(Short value, String description) {
            this.value = value;
            this.description = description;
        }

        /**
         * Returning address value.
         * @return address value.
         */
        public short getValue() {
            return value;
        }

        /**
         * Returning address value description.
         * @return address value description.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Getting value type.
         * @param family address value type.
         * @return value type.
         */
        public static Family valueOf(short family) {
            for(Family f : values()) {
                if(f.getValue() == family) {
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
        switch(sa_family) {
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
        return data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;
        if (obj instanceof Inet4Address) {
            final Inet4Address addr = (Inet4Address) obj;
            return Arrays.equals(this.data, addr.toBytes());
        } else if (obj instanceof Inet6Address) {
            final Inet6Address addr = (Inet6Address) obj;
            return Arrays.equals(this.data, addr.toBytes());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(FormatUtils.toLong(this.data));
    }

    @Override
    public String toString() {
        Family family = getSaFamily();
        if (family == null) return null;
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
