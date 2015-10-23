package com.jxpcap.util;

import com.jxpcap.Jxpcap;
import com.jxpcap.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

public class DeviceList extends Jxpcap {
    
    protected static List<NetworkInterface> all_devs = new ArrayList<>();
    
    public NetworkInterface getIndex(int index) {
        return all_devs.get(index);
    }
    public int getLength() {
        return all_devs.size();
    }
}
