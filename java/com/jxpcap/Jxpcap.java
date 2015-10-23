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

import com.jxpcap.util.Message;

public class Jxpcap {
        
    protected long jxpcap;

    private static native Jxpcap nativeOpenLive(String device_name, int snaplen, int promisc, int timeout, Message errmsg);
    
    public static Jxpcap openLive(String device_name, int snaplen, boolean promisc, int timeout, Message errmsg) {
        return nativeOpenLive(device_name, snaplen, (promisc ? 1 : 0), timeout, errmsg);
    }
        
    static {
        System.loadLibrary("jxpcap");
    }
}
