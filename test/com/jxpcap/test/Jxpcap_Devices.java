package com.jxpcap.test;

import com.jxpcap.NetworkInterface;
import com.jxpcap.util.DeviceList;
import com.jxpcap.util.Devices;
import com.jxpcap.util.JxpcapException;
import com.jxpcap.util.Message;

public class Jxpcap_Devices {
	public static void main(String[] args) {
		Message errmsg = new Message(null);
		DeviceList all_devs = new DeviceList();
		Devices devs = null;
		try {
			devs = Devices.getAllDevices(all_devs, errmsg);
		} catch (JxpcapException e) {
			e.printStackTrace();
		}
		NetworkInterface[] NI = new NetworkInterface[all_devs.getLength()];
		for(int i=0; i<all_devs.getLength(); i++) {
			NI[i] = all_devs.getIndex(i);
		}
		for(NetworkInterface IFACE : NI) {
			System.out.println("Device Name         : " + IFACE.getName());
			System.out.println("Device MAC Address  : " + IFACE.getMACAddress());
			System.out.println("Device IP Address   : " + IFACE.getIPAddress());
			System.out.println("Device Gateway      : " + IFACE.getGateway());
		}
	}
}
