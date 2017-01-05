import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.PacketHandler;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.protocol.datalink.Ethernet;
import com.ardikars.jxnet.util.Loader;

/**
 * Created by root on 04/01/17.
 */
public class Main {
	
	public static void main(String[] args) {
		StringBuilder errbuf = new StringBuilder();
		Pcap pcap = Jxnet.pcapOpenLive("eth0"
			, 65535, 1, 2000, errbuf);
		if (pcap == null) {
			System.out.println(errbuf.toString());
			System.exit(1);
		}
		PacketHandler<Pcap> callback = new PacketHandler<Pcap>() {
			@Override
			protected void recievedPacket(Pcap user, PcapPktHdr pktHdr, Packet packet) {
				Ethernet eth = (Ethernet) packet;
				System.out.println(eth);
				System.out.println("User : " + user);
			}
		};
		System.out.println(pcap);
		Jxnet.pcapLoop(pcap, 3, callback, pcap);
		Jxnet.pcapClose(pcap);
	}
	
	static {
		Loader.loadLibrary();
	}
	
}
