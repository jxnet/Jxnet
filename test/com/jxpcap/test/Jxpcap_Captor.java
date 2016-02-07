package com.jxpcap.test;

import com.jxpcap.Captor;
import com.jxpcap.Jxpcap;
import com.jxpcap.packet.Packet;
import com.jxpcap.util.JxpcapException;
import com.jxpcap.util.Message;

public class Jxpcap_Captor {
	
	public static void main(String args[]) {
		Message errmsg = new Message(null);
		Jxpcap jxpcap = null;
		try {
			jxpcap = Jxpcap.openLive("eth0", 60000,true, 2000, errmsg);
		} catch (JxpcapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Packet packet = new Packet();
		Captor captor = new Captor();
		int i=0;
		while(i < 100) {
			captor.getPacket(jxpcap, packet);
			System.out.println("Captured Length       : " + packet.getCapturedLength());
			System.out.println("Packet Length         : " + packet.getLength());
			System.out.println("Packet Data           : " + packet.getPacketData());
			System.out.println("Time in seconds       : " + packet.getTimeInSeconds());
			System.out.println("Time in micro seconds : " + packet.getTimeInMicroseconds());
			i++;
		}
	}
}
