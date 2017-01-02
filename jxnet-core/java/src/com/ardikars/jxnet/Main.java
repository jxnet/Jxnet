package com.ardikars.jxnet;

import com.ardikars.jxnet.packet.protocol.datalink.Ethernet;
import com.ardikars.jxnet.packet.protocol.network.IPv4;
import com.ardikars.jxnet.util.Loader;

import java.nio.ByteBuffer;

public class Main {

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
				System.out.println("======================");
				System.out.println("Destionation MacAddr : "+ether.getDestinationMacAddress());
				System.out.println("Source MacAddr       : "+ether.getSourceMacAddress());
				System.out.println("Ethernet Type        : "+ether.getEtherType());
				if (ether.getChild() instanceof IPv4) {
					IPv4 ipv4 = (IPv4) ether.getChild();
					System.out.println("=====================");
					System.out.println("Version             : "+ipv4.getVersion());
					System.out.println("Header Length       : "+ipv4.getHeaderLength());
					System.out.println("DiffServ            : "+ipv4.getDiffServ());
					System.out.println("Total Length        : "+ipv4.getTotalLength());
					System.out.println("Identification      : "+ipv4.getIdentification());
					System.out.println("Flag                : "+ipv4.getFlag());
					System.out.println("Fragment Offset     : "+ipv4.getFragmentOffset());
					System.out.println("Ttl                 : "+ipv4.getTtl());
					System.out.println("Protocol            : "+ipv4.getProtocol());
					System.out.println("Checksum            : "+ipv4.getChecksum());
					System.out.println("Source Address      : "+ipv4.getSourceAddress());
					System.out.println("Destination Address : "+ipv4.getDestinationAddress());
					System.out.println("Option              : "+ipv4.getOption());
					System.out.println("Data                : "+ipv4.getData());
				}
			
			}
		};
		Jxnet.pcapLoop(pcap, -1, callback, "");
		
	}
	
	static {
		Loader.loadLibrary();
	}

}
