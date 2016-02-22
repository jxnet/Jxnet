package com.ardikars.test.jxpcap;

import java.util.ArrayList;
import java.util.List;

import com.ardikars.jxpcap.Jxpcap;
import com.ardikars.jxpcap.JxpcapAddr;
import com.ardikars.jxpcap.JxpcapIf;

public class ObtainingListInterface {
	public static void main(String[] args) {
		String errbuf = new String();

		List<JxpcapIf> alldevsp = new ArrayList<JxpcapIf>();
		int r = Jxpcap.findAllDevs(alldevsp, errbuf);
		for(JxpcapIf iface : alldevsp) {
			System.out.println(iface.getName()+": ");
			for(JxpcapAddr addr : iface.getAddresses()) {
				System.out.print (addr.addr);
			}
		}
 		System.out.println("result = " +r );
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