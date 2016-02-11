package com.jxpcap.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import com.jxpcap.Jxpcap;
import com.jxpcap.NetworkInterface;
import com.jxpcap.Sender;
import com.jxpcap.util.Addresses;
import com.jxpcap.util.DeviceList;
import com.jxpcap.util.Devices;
import com.jxpcap.util.JxpcapException;
import com.jxpcap.util.Message;


public class Jxpcap_Sender {
	
	public static final int ARP_LEN = 42;
	public static final short ETH_P_ARP			= 0x0806;
	public static final short ETH_P_IP			= 0x0800;
	public static final short ARPHRD_ETHER		= 1;
	public static final short ARPOP_REQUEST		= 1;
	
	public static final byte LEN_4 = 4;
	public static final byte LEN_6 = 6;
	
	public static void main(String[] args)  {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
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
			System.out.println(i);
			System.out.println("Device Name         : " + NI[i].getName());
			System.out.println("Device MAC Address  : " + NI[i].getMACAddress());
			System.out.println("Device IP Address   : " + NI[i].getIPAddress());
			System.out.println("Device Gateway      : " + NI[i].getGateway());
		}
		
		int index_device = 0;
		System.out.print("Masukan Index Interface: ");
		try {
			index_device = Integer.parseInt(input.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Jxpcap pcap = null;
		try {
			pcap = Jxpcap.openLive(NI[index_device].getName(), 60000, true, 2000, errmsg);
		} catch (JxpcapException e) {
			e.printStackTrace();
		}
		
		ByteBuffer packet = ByteBuffer.allocateDirect(ARP_LEN);
		packet.put(Addresses.stringToBytesMacAddr("FF:FF:FF:FF:FF:FF"));
		packet.put(Addresses.stringToBytesMacAddr(NI[index_device].getMACAddress()));
		packet.putShort(ETH_P_ARP);
		packet.putShort(ARPHRD_ETHER);
		packet.putShort(ETH_P_IP);
		packet.put(LEN_6);
		packet.put(LEN_4);
		packet.put(Addresses.stringToBytesMacAddr(NI[index_device].getMACAddress()));
		packet.put(Addresses.stringToBytesIPAddr(NI[index_device].getIPAddress()));
		packet.put(Addresses.stringToBytesMacAddr("FF:FF:FF:FF:FF:FF"));
		packet.put(Addresses.stringToBytesIPAddr("192.168.1.254"));
		
		try {
			int packet_number = Integer.parseInt(input.readLine());
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
		Sender sender = new Sender();
		while(true) {
			System.out.println(sender.sendPacket(pcap, packet, packet.capacity()));
			try {
			    Thread.sleep(1000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}
}