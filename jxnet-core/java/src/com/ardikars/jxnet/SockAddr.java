
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
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
        }
        return null;
    }
    
    public byte[] getData() {
        return data;
    }
    
    @Override
    public String toString() {
        switch(getSaFamily()) {
            case AF_INET:
                return Inet4Address.valueOf(data).toString();
            case AF_INET6:
                return Inet6Address.valueOf(data).toString();
            default:
                return "Unknown Family : "+ sa_family;
        }
    }
    
}