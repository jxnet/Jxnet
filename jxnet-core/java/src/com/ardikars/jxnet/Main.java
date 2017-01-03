package com.ardikars.jxnet;

import com.ardikars.jxnet.packet.protocol.datalink.Ethernet;
import com.ardikars.jxnet.packet.protocol.network.IPv4;
import com.ardikars.jxnet.packet.protocol.transport.TCP;
import com.ardikars.jxnet.util.Loader;

import java.nio.ByteBuffer;

public class Main {

	/*
	public static void main(String[] args) {
		
		StringBuilder errbuf = new StringBuilder();
		Pcap pcap = Jxnet.pcapOpenLive("eth0", 65535, 1, 2000, errbuf);
		if (pcap == null) {
			System.out.println(errbuf.toString());
			System.exit(1);
		}
		
		PcapHandler<String> callback = new PcapHandler<String>() {
			@Override
			public void nextPacket(String user, PcapPktHdr h, ByteBuffer buffer) {
				byte[] bytes = new byte[buffer.capacity()];
				buffer.get(bytes);
				Ethernet ether = Ethernet.wrap(bytes);
				if (ether.getChild() instanceof IPv4) {
					IPv4 ipv4 = (IPv4) ether.getChild();
					if (ipv4.getChild() instanceof TCP) {
						TCP tcp = (TCP) ipv4.getChild();
						System.out.println(ether);
						System.out.println(ipv4);
						System.out.println(tcp);
						System.out.println("============================================");
					}
				}
				
			}
		};
		Jxnet.pcapLoop(pcap, -1, callback, "");
		
	}
	*/
	
	public static void main(String[] args) {
		
		StringBuilder errbuf = new StringBuilder();
		Pcap pcap = Jxnet.pcapOpenOffline("/home/pi/Downloads/vlan.cap", errbuf);
		if(pcap == null) {
			System.err.println(errbuf.toString());
			return;
		}
		PcapPktHdr h = new PcapPktHdr();
		ByteBuffer b = null;
		while((b = Jxnet.pcapNext(pcap, h)) != null) {
			byte[] data = new byte[b.capacity()];
			b.get(data);
			Ethernet ethernet = Ethernet.wrap(data);
			//System.out.println(ethernet);
			if (ethernet.getChild() instanceof IPv4) {
				IPv4 ipv4 = (IPv4) ethernet.getChild();
				System.out.println(ipv4);
			}
		}
		Jxnet.pcapClose(pcap);
	}
	
	static {
		Loader.loadLibrary();
	}

}
