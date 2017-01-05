import com.ardikars.jxnet.PacketHandler;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.protocol.datalink.Ethernet;

public class Handler extends PacketHandler<String> {
	
	@Override
	protected void recievedPacket(String user, PcapPktHdr pktHdr, Packet packet) {
		Ethernet eth = (Ethernet) packet;
		System.out.println(eth);
		System.out.println(user);
	}
	
}
