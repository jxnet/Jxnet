package com.ardikars.test.jxpcap;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

import com.ardikars.jxpcap.Jxpcap;
import com.ardikars.jxpcap.JxpcapAddr;
import com.ardikars.jxpcap.JxpcapIf;
import com.ardikars.jxpcap.SockAddr;
import com.ardikars.jxpcap.util.JxpcapAddrUtils;
import com.sun.jndi.cosnaming.IiopUrl.Address;

public class ObtainingListInterface {
	public static void main(String[] args) {
		StringBuilder errbuf = new StringBuilder();
		
		System.out.println(Jxpcap.lookupDev(errbuf));
		
		
		
		String ip = null;
 		String netmask = null;
 		String broadaddr = null;
 		String dstaddr = null;
		

		List<JxpcapIf> alldevsp = new ArrayList<JxpcapIf>();
		int r = Jxpcap.findAllDevs(alldevsp, errbuf);
		for(JxpcapIf iface : alldevsp) {
			if(iface.getName().equals("eth0")) {
				for(JxpcapAddr addr : iface.getAddresses()) {
					if(addr.addr.sa_family == SockAddr.AF_INET) {
						ip = addr.addr.toString();
						netmask = addr.netmask.toString();
						broadaddr = addr.broadaddr.toString();
						dstaddr = addr.dstaddr.toString();
					}
				}
			}
		}
		System.out.println(ip+" : "+netmask+" : "+" : "+broadaddr+" : "+dstaddr);

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
	public static String bytesToStringMacAddr(byte[] bytes) {
        if(bytes == null) { return null; }
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (byte s : bytes) {
            if (!isFirst) {
                sb.append(":");
            } else {
                isFirst = false;
            }
            sb.append(String.format("%02x", s & 0xff));
        }
        return sb.toString();
    }
}