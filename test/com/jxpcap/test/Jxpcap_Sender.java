// Not supported on ARM proccessor


package com.jxpcap.test;

import com.jxpcap.*;
import com.jxpcap.util.*;

public class Jxpcap_Sender {
	public static void main(String[] args)  {
	  // Error message
	  Message errmsg = new Message(null);
		Jxpcap pcap = null;
		try {
		  // openLive(device name, snaplen, promisc, timeout, error message);
			pcap = Jxpcap.openLive("eth0", 60000, false, 2000, errmsg);
		} catch (JxpcapException e) {
		  // Print out error message
			e.printStackTrace();
		}
		// Example packet
		byte[] packet = new byte[] {(byte) 0xff};
		Sender send = new Sender();
		// Sending packets (return "OK" || "errmsg")
		System.out.println(send.sendPacket(pcap, packet, packet.length));
	}
}