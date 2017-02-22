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

public final class SockAddr {
    
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
        
        public short getFamily() {
            return family;
        }
        
        public String getDescription() {
            return description;
        }
        
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
    
    public byte[] getData() {
        return data;
    }

    public InetAddress toInetAddress() {
        switch (getSaFamily()) {
            case AF_INET: return Inet4Address.valueOf(data);
            case AF_INET6: return Inet6Address.valueOf(data);
            default: return null;
        }
    }

    @Override
    public String toString() {
        return toInetAddress().toString();
    }
    
}