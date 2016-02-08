/*
 * Copyright (C) 2015 Langkuy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jxpcap;

import com.jxpcap.util.JxpcapException;
import com.jxpcap.util.Message;

public class Jxpcap {
        
    protected long jxpcap;

    private static native Jxpcap nativeOpenLive(String device_name, int snaplen, int promisc, int timeout, Message errmsg);
    
    private static native void nativeClose(long jxpcap);
    
    private static native Jxpcap nativeCreate(String device_name, Message errmsg);
    
    private static native int nativeActive(long jxpcap);
    
    public static Jxpcap openLive(String device_name, int snaplen, boolean promisc, int timeout, Message errmsg) throws JxpcapException {
        Jxpcap pcap = nativeOpenLive(device_name, snaplen, (promisc ? 1 : 0), timeout, errmsg);
        if(pcap == null) {
        	throw new JxpcapException(errmsg.getMessage());
        }
        return pcap;
    }
    
    public static void close(long jxpcap) {
    	 nativeClose(jxpcap);
    }
    
    public static Jxpcap create(String device_name, Message errmsg) throws JxpcapException {
    	Jxpcap pcap = nativeCreate(device_name, errmsg);
    	if(pcap == null) {
    		throw new JxpcapException(errmsg.getMessage());
    	}
    	return pcap;
    }
    
    public static int active(long jxpcap) {
    	return nativeActive(jxpcap);
    }
    
    static {
        System.loadLibrary("jxpcap");
    }
}
