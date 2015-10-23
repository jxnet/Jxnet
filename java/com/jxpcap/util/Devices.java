/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jxpcap.util;

import com.jxpcap.Jxpcap;
import com.jxpcap.NetworkInterface;

import java.util.List;

public class Devices extends DeviceList {
    
    private static native Devices nativeGetAllDevices(List<NetworkInterface> all_devs, Message errmsg);
    
    public static Devices getAllDevices(DeviceList all_devs, Message errmsg) {
        return nativeGetAllDevices(all_devs.all_devs , errmsg);
    }
    
    static {
        System.loadLibrary("jxpcap");
    }
}
