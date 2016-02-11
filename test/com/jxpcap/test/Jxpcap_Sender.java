// Not supported on ARM proccessor


package com.jxpcap.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import com.jxpcap.*;
import com.jxpcap.protocol.lan.Ethernet;
import com.jxpcap.protocol.net.ARP;
import com.jxpcap.util.*;

class Converter {
    public static String byteToStringMAC(byte[] b) {
        if(b == null) { return null; }
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (byte s : b) {
            if (!isFirst) {
                sb.append(":");
            } else {
                isFirst = false;
            }
            sb.append(String.format("%02x", s & 0xff));
        }
        return sb.toString();
    }
    public static byte[] stringToByteMAC(String s) {
        String[] bytes = s.split(":");
        byte[] b = new byte[bytes.length];
        for (int i=0; i<bytes.length; i++) {
            BigInteger temp = new BigInteger(bytes[i], 16);
            byte[] raw = temp.toByteArray();
            b[i] = raw[raw.length - 1];
        }
        return b;
    }

    public static String byteToStringIP(byte[] b) {
        StringBuilder sb = new StringBuilder(15);
        for(int i=0; i<b.length; i++) {
            if(i>0) {
                sb.append(".");
            }
            sb.append(b[i] & 0xFF);
        }
        return sb.toString();
    }
    public static InetAddress stringToByteIP(String s) {
        InetAddress ip = null;
        try {
            ip = InetAddress.getByName(s);
        } catch (UnknownHostException ex) {
            System.out.println("Invalid IP Address.");
        }
        return ip;
    }
}

public class Jxpcap_Sender {
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
		
		int in = 0;
		System.out.print("Masukan Index Interface: ");
		try {
			in = Integer.parseInt(input.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Jxpcap pcap = null;
		try {
			pcap = Jxpcap.openLive(NI[in].getName(), 60000, true, 2000, errmsg);
		} catch (JxpcapException e) {
			e.printStackTrace();
		}
		
		Ethernet ether_hdr = new Ethernet();
		ether_hdr.setPacket(Converter.stringToByteMAC("FF:FF:FF:FF:FF:FF"), Converter.stringToByteMAC(NI[in].getMACAddress()), Ethernet.ETH_P_ARP);
		
		ARP arp_hdr = new ARP();
		arp_hdr.setPacket(ARP.ARPHRD_ETHER, Ethernet.ETH_P_IP, ARP.LEN_6, ARP.LEN_4, ARP.ARPOP_REQUEST,
				Converter.stringToByteMAC(NI[in].getMACAddress()), Converter.stringToByteIP(NI[in].getIPAddress()).getAddress(),
				Converter.stringToByteMAC("ff:ff:ff:ff:ff:ff"), Converter.stringToByteIP("192.168.1.254").getAddress());
		
		
		ByteBuffer packet = ByteBuffer.allocateDirect(14+28);
		packet.put(ether_hdr.getPacket());
		packet.put(arp_hdr.getPacket());
				
		Sender sender = new Sender();
		while(true) {
			System.out.println(sender.sendPacket(pcap, arp_hdr.getPacket(), 42));
			try {
			    Thread.sleep(1000);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}
}