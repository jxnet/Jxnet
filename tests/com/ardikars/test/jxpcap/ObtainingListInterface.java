package com.ardikars.test.jxpcap;

import java.util.ArrayList;
import java.util.List;

import com.ardikars.jxpcap.Jxpcap;
import com.ardikars.jxpcap.JxpcapAddr;
import com.ardikars.jxpcap.JxpcapIf;

public class ObtainingListInterface {
	public static void main(String[] args) {
		StringBuilder errbuf = new StringBuilder();

		List<JxpcapIf> alldevsp = new ArrayList<JxpcapIf>();
		int r = Jxpcap.findAllDevs(alldevsp, errbuf);
		for(JxpcapIf iface : alldevsp) {
			for(JxpcapAddr addr : iface.getAddresses()) {
				if(iface.getName().equals("lo")) {
					System.out.println(addr.addr.toString());
				}
			}
		}
 		System.out.println("\nresult = " +r );
	}
	
	public static String bytesToStringIPAddr(byte[] bytes) {
        StringBuilder sb = new StringBuilder(15);
        for(int i=0; i<bytes.length; i++) {
            if(i>0) {
                sb.append(".");
            }
            sb.append(bytes[i] & 0xFF);
        }
        return sb.toString();
	}
}