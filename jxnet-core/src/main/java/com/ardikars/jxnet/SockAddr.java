/**
 * Copyright (C) 2015-2018 Jxnet
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

import com.ardikars.common.annotation.Mutable;
import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.net.Inet6Address;
import com.ardikars.common.util.NamedNumber;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Socket address.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Mutable(volatiles = { "sa_family", "data" })
public final class SockAddr implements Cloneable {

    private volatile short sa_family;

    private volatile byte[] data = new byte[0];

    protected SockAddr() {
        this((short) 0, new byte[] {});
    }

    protected SockAddr(short saFamily, byte[] data) {
        if (data == null) {
            data = new byte[0];
        }
        this.sa_family = saFamily;
        this.data = Arrays.copyOf(data, data.length);
    }

    /**
     * Returns SockAddr family type.
     * @return returns family type.
     */
    public Family getSaFamily() {
        return Family.valueOf(this.sa_family);
    }

    /**
     * Returns bytes address of SockAddr.
     * @return returns bytes address.
     */
    public byte[] getData() {
        byte[] data = new byte[this.data.length];
        if (data.length == 0) {
            Family family = getSaFamily();
            if (family == Family.AF_INET6) {
                this.data = new byte[Inet6Address.IPV6_ADDRESS_LENGTH];
            } else {
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
    public SockAddr clone() throws CloneNotSupportedException {
        return (SockAddr) super.clone();
    }

    @Override
    public String toString() {
        final Family family = this.getSaFamily();
        if (family.equals(Family.AF_INET)) {
            return Inet4Address.valueOf(this.getData()).toString();
        } else if (family.equals(Family.AF_INET6)) {
            return Inet6Address.valueOf(this.getData()).toString();
        } else {
            return "";
        }
    }

    /**
     * This class represents an Socket Address Family.
     */
    public static final class Family extends NamedNumber<Short, Family> {

        private static final long serialVersionUID = 3311056956556965869L;

        private static final Map<Short, Family> registry = new HashMap<Short, Family>();

        public static final Family AF_INET = new Family((short) 2, "Internet IP Protocol");

        public static final Family AF_INET6 = new Family((short) 10, "IP version 6");

        public static final Family UNKNOWN = new Family((short) -1, "Unknown");

        private short value;
        private String description;

        public Family(Short value, String name) {
            super(value, name);
        }

        /**
         * Get {@link Family} object from given sock addr family.
         * @param family sock addr family.
         * @return returns {@link Family} object.
         */
        public static Family valueOf(Short family) {
            Family result = registry.get(family);
            if (result == null) {
                return new Family(family, "Unknown");
            }
            return result;
        }

        /**
         * Add new sock addr family into registry.
         * @param family {@link Family} object.
         */
        public static void register(Family family) {
            registry.put(family.getValue(), family);
        }

        @Override
        public String toString() {
            return new StringBuilder("Family{")
                    .append("value=").append(value)
                    .append(", description='").append(description).append('\'')
                    .append('}').toString();
        }

        static {
            registry.put(AF_INET.getValue(), AF_INET);
            registry.put(AF_INET6.getValue(), AF_INET6);
        }

    }

}
