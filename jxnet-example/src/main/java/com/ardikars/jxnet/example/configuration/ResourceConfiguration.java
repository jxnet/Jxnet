package com.ardikars.jxnet.example.configuration;

import com.ardikars.jxnet.Inet4Address;
import com.ardikars.jxnet.PcapAddr;
import com.ardikars.jxnet.PcapIf;
import com.ardikars.jxnet.SockAddr;
import com.ardikars.jxnet.annotation.Configuration;
import com.ardikars.jxnet.annotation.Inject;
import com.ardikars.jxnet.annotation.Order;
import com.ardikars.jxnet.annotation.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.ardikars.jxnet.Jxnet.PcapFreeAllDevs;
import static com.ardikars.jxnet.Jxnet.OK;
import static com.ardikars.jxnet.Jxnet.PcapFindAllDevs;

@Order(2)
@Configuration("resourceConfiguration")
public class ResourceConfiguration {

	private final Logger logger = Logger.getLogger(ResourceConfiguration.class.getName());

	public ResourceConfiguration() {

	}

	@Property("test")
	public String test () {
		return new String("fdsfds");
	}
//
//	@Inject("errbuf")
//	public StringBuilder errbuf;

	@Order(4)
	@Property(value = "libraryVersion", replaced = true)
	public String version () {
		return new String("fsdfsd");
	}

//	@Order(2)
	@Property("source")
	public String source() {
//		String source = null;
//		List<PcapIf> alldevsp = new ArrayList<>();
//		int resultCode;
//		if ((resultCode = PcapFindAllDevs(alldevsp, errbuf)) != OK) {
//			logger.warning("create:PcapFindAllDevs(): " + errbuf.toString());
//		}
//		String firstDev = null;
//		for (PcapIf dev : alldevsp) {
//			if (firstDev == null) {
//				firstDev = dev.getName();
//			}
//			for (PcapAddr addr : dev.getAddresses()) {
//				if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
//					if (addr.getAddr().getData() != null) {
//						Inet4Address d = Inet4Address.valueOf(addr.getAddr().getData());
//						if (!d.equals(Inet4Address.LOCALHOST) && !d.equals(Inet4Address.ZERO)) {
//							source = dev.getName();
//						}
//					}
//				}
//			}
//		}
//		PcapFreeAllDevs(alldevsp);
//		if (source == null) {
//			source = firstDev;
//		}
		return "";
	}

}
