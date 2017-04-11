/**
 * Copyright (C) 2017  Ardika Rommy Sanjaya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ardikars.jxnet.util;

import com.ardikars.jxnet.*;

import java.util.ArrayList;
import java.util.List;

import static com.ardikars.jxnet.Jxnet.PcapFindAllDevs;
import static com.ardikars.jxnet.util.Preconditions.CheckNotNull;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class AddrUtils {

	/**
	 * Get bytes MAC Address.
	 * @param dev_name interface name.
	 * @return bytes MacAddress;
	 */
	public static native byte[] GetMACAddress(String dev_name);
	
	private static native String GetGatewayAddress(String dev_name);

	@Deprecated
	public static MacAddress getHardwareAddress(String dev_name) {
		CheckNotNull(dev_name, "");
		byte[] macAddr = GetMACAddress(dev_name);
		return (macAddr == null ? null : MacAddress.valueOf(macAddr));
	}

	@Deprecated
	public static Inet4Address getGatewayAddress(String dev_name) {
		CheckNotNull(dev_name, "");
		String gwAddr = GetGatewayAddress(dev_name);
		return (gwAddr == null ? null : Inet4Address.valueOf(gwAddr));
	}

	public static String LookupDev(StringBuilder errbuf) {
		CheckNotNull(errbuf);
		List<PcapIf> alldevsp = new ArrayList<PcapIf>();
		if (PcapFindAllDevs(alldevsp, errbuf) == 0) {
			for (PcapIf dev : alldevsp) {
				for (PcapAddr address : dev.getAddresses()) {
					if (address.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
						if (address.getAddr().getData() != null && address.getNetmask().getData() != null) {
							return dev.getName();
						}
					}
				}
			}
		}
		return null;
	}

	public static String LookupDev(Inet4Address addr, Inet4Address mask, Inet4Address netaddr,
								Inet4Address broadaddr, Inet4Address dstaddr,
								MacAddress macaddr, StringBuilder errbuf) {
		CheckNotNull(netaddr);
		CheckNotNull(addr);
		CheckNotNull(mask);
		CheckNotNull(broadaddr);
		CheckNotNull(dstaddr);
		CheckNotNull(macaddr);
		CheckNotNull(errbuf);
		List<PcapIf> alldevsp = new ArrayList<PcapIf>();
		if (PcapFindAllDevs(alldevsp, errbuf) == 0) {
			for (PcapIf dev : alldevsp) {
				for (PcapAddr address : dev.getAddresses()) {
					if (address.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
						if (address.getAddr().getData() != null && address.getNetmask().getData() != null) {
							addr.update(Inet4Address.valueOf(address.getAddr().getData()));
							mask.update(Inet4Address.valueOf(address.getNetmask().getData()));
							broadaddr.update(Inet4Address.valueOf(address.getBroadAddr().getData()));
							dstaddr.update(Inet4Address.valueOf(address.getDstAddr().getData()));
							netaddr.update(Inet4Address.valueOf(addr.toInt() & mask.toInt()));
							macaddr.update(getHardwareAddress(dev.getName()));
							return dev.getName();
						}
					}
				}
			}
		}
		return null;
	}

	public static String LookupDev(Inet4Address addr, Inet4Address mask, Inet4Address netaddr,
								   MacAddress macaddr, StringBuilder errbuf) {
		CheckNotNull(netaddr);
		CheckNotNull(addr);
		CheckNotNull(mask);
		CheckNotNull(macaddr);
		CheckNotNull(errbuf);
		List<PcapIf> alldevsp = new ArrayList<PcapIf>();
		if (PcapFindAllDevs(alldevsp, errbuf) == 0) {
			for (PcapIf dev : alldevsp) {
				for (PcapAddr address : dev.getAddresses()) {
					if (address.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
						if (address.getAddr().getData() != null && address.getNetmask().getData() != null) {
							addr.update(Inet4Address.valueOf(address.getAddr().getData()));
							mask.update(Inet4Address.valueOf(address.getNetmask().getData()));
							netaddr.update(Inet4Address.valueOf(addr.toInt() & mask.toInt()));
							macaddr.update(getHardwareAddress(dev.getName()));
							return dev.getName();
						}
					}
				}
			}
		}
		return null;
	}

	public static int LookupNet(String source, Inet4Address addr, Inet4Address mask, Inet4Address netaddr,
								Inet4Address broadaddr, Inet4Address dstaddr,
								MacAddress macaddr, StringBuilder errbuf) {
		CheckNotNull(netaddr);
		CheckNotNull(addr);
		CheckNotNull(mask);
		CheckNotNull(broadaddr);
		CheckNotNull(dstaddr);
		CheckNotNull(macaddr);
		CheckNotNull(errbuf);
		List<PcapIf> alldevsp = new ArrayList<PcapIf>();
		if (PcapFindAllDevs(alldevsp, errbuf) == 0) {
			for (PcapIf dev : alldevsp) {
				if (dev.getName().equals(source)) {
					for (PcapAddr address : dev.getAddresses()) {
						if (address.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
							if (address.getAddr().getData() != null && address.getNetmask().getData() != null) {
								addr.update(Inet4Address.valueOf(address.getAddr().getData()));
								mask.update(Inet4Address.valueOf(address.getNetmask().getData()));
								broadaddr.update(Inet4Address.valueOf(address.getBroadAddr().getData()));
								dstaddr.update(Inet4Address.valueOf(address.getDstAddr().getData()));
								netaddr.update(Inet4Address.valueOf(addr.toInt() & mask.toInt()));
								macaddr.update(getHardwareAddress(dev.getName()));
								return 0;
							}
						}
					}
				}
			}
		}
		return -1;
	}

	public static int LookupNet(String source, Inet4Address addr, Inet4Address mask, Inet4Address netaddr,
								MacAddress macaddr, StringBuilder errbuf) {
		CheckNotNull(addr);
		CheckNotNull(mask);
		CheckNotNull(netaddr);
		CheckNotNull(macaddr);
		CheckNotNull(errbuf);
		List<PcapIf> alldevsp = new ArrayList<PcapIf>();
		if (PcapFindAllDevs(alldevsp, errbuf) == 0) {
			for (PcapIf dev : alldevsp) {
				if (dev.getName().equals(source)) {
					for (PcapAddr address : dev.getAddresses()) {
						if (address.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
							if (address.getAddr().getData() != null && address.getNetmask().getData() != null) {
								addr.update(Inet4Address.valueOf(address.getAddr().getData()));
								mask.update(Inet4Address.valueOf(address.getNetmask().getData()));
								netaddr.update(Inet4Address.valueOf(addr.toInt() & mask.toInt()));
								macaddr.update(getHardwareAddress(dev.getName()));
								return 0;
							}
						}
					}
				}
			}
		}
		return -1;
	}

	static {
		try {
			Class.forName("com.ardikars.jxnet.Jxnet");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
