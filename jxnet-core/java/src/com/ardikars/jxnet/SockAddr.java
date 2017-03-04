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
 * @version 1.1.0
 */
public final class SockAddr {

    private static native void initIDs();
    
    public enum Family {
        
        AF_INET((short) 2, "AF_INET"),
        AF_INET6((short) 10, "AF_INET6"),
        ;
        
        private short family;
        private String description;
        
        private Family(short family, String description) {
            this.family = family;
            this.description = description;
        }

        /**
         * Returning address family.
         * @return address family.
         */
        public short getFamily() {
            return family;
        }

        /**
         * Returning address family description.
         * @return address family description.
         */
        public String getDescription() {
            return description;
        }

        /**
         * Getting family type.
         * @param family address family type.
         * @return family type.
         */
        public static Family valueOf(short family) {
            for(Family f : values()) {
                if(f.getFamily() == family) {
                    return f;
                }
            }
            
            return null;
        }
        
    }
    
    private short sa_family;
    
    private byte[] data;

    /**
     * Returning address family type.
     * @return address family type.
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

    /**
     * Create Inet4Address or Inet6Address from SockAddr.
     * @return Inet4Address or Inet6Address.
     */
    public InetAddress toInetAddress() {
        switch (getSaFamily()) {
            case AF_INET: return Inet4Address.valueOf(data);
            case AF_INET6: return Inet6Address.valueOf(data);
            default: return null;
        }
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
        return toInetAddress().toString();
    }

    static {
        try {
            Class.forName("com.ardikars.jxnet.Jxnet");
            initIDs();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
