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

import com.ardikars.jxnet.Inet4Address;
import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.MacAddress;
import com.ardikars.jxnet.PcapIf;
import com.ardikars.jxnet.PcapAddr;
import com.ardikars.jxnet.SockAddr;
import static com.ardikars.jxnet.Validate.CheckNotNull;
import com.ardikars.jxnet.exception.NotSupportedPlatformException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.2
 */
public class AddrUtils {

    /**
     * Get getway address.
     * @return getway address.
     * @throws IOException not connected to the network.
     */
    public static Inet4Address GetGatewayAddress() throws IOException {
        Process process = null;
        BufferedReader stdIn = null;
        String str = null;
        if (Platforms.isWindows()) {
            process = Runtime.getRuntime().exec("route PRINT -4");
            stdIn = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            str = stdIn.lines().filter(s -> s.contains("0.0.0.0"))
                    .findFirst().orElse(null);
        } else if (Platforms.isLinux()) {
            process = Runtime.getRuntime().exec("route -n");
            stdIn = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            str = stdIn.lines().filter(s -> s.contains("0.0.0.0"))
                    .filter(s -> s.contains("UG"))
                    .findFirst().orElse(null);
        } else {
            throw new NotSupportedPlatformException();
        }
        if (str == null) return null;
        String[] strings = str.replaceAll("0.0.0.0", "").split(" ");
        for (String s : strings) {
            if (!s.equals("")) {
                str = s;
                break;
            }
        }
        return Inet4Address.valueOf(str);
    }

    /**
     * Get network interface information.
     * @param address ipv4 address.
     * @param netmask netmask address.
     * @param netaddr network address.
     * @param broadaddr broadcast address.
     * @param dstaddr destination address.
     * @param macAddress mac address.
     * @param description description.
     * @return interface name.
     */
    @Deprecated
    public static String LookupNetworkInterface(Inet4Address address,
            Inet4Address netmask,
            Inet4Address netaddr,
            Inet4Address broadaddr,
            Inet4Address dstaddr,
            MacAddress macAddress,
            StringBuilder description) {

        CheckNotNull(address);
        CheckNotNull(netmask);
        CheckNotNull(netaddr);
        CheckNotNull(broadaddr);
        CheckNotNull(dstaddr);
        CheckNotNull(description);

        StringBuilder errbuf = new StringBuilder();

        List<PcapIf> ifs = new ArrayList<PcapIf>();
        if (Jxnet.PcapFindAllDevs(ifs, errbuf) != Jxnet.OK) {
            return null;
        }

        description.setLength(0);

        for (PcapIf dev : ifs) {
            for (PcapAddr addr : dev.getAddresses()) {
                if (addr.getAddr().getData() == null || addr.getBroadAddr().getData() == null ||
                        addr.getNetmask().getData() == null) {
                    continue;
                }
                if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET &&
                        !Inet4Address.valueOf(addr.getAddr().getData()).equals(Inet4Address.ZERO) &&
                        !Inet4Address.valueOf(addr.getAddr().getData()).equals(Inet4Address.LOCALHOST) &&
                        !Inet4Address.valueOf(addr.getBroadAddr().getData()).equals(Inet4Address.ZERO) &&
                        !Inet4Address.valueOf(addr.getNetmask().getData()).equals(Inet4Address.ZERO)
                        ) {
                    address.update(Inet4Address.valueOf(addr.getAddr().getData()));
                    netmask.update(Inet4Address.valueOf(addr.getNetmask().getData()));
                    netaddr.update(Inet4Address.valueOf(address.toInt() & netmask.toInt()));
                    broadaddr.update(Inet4Address.valueOf(addr.getBroadAddr().getData()));
                    if (addr.getDstAddr().getData() != null) {
                        dstaddr.update(Inet4Address.valueOf(addr.getDstAddr().getData()));
                    } else {
                        dstaddr.update(Inet4Address.ZERO);
                    }
                    macAddress.update(MacAddress.fromNicName(dev.getName()));
                    if (dev.getDescription() != null) {
                        description.append(dev.getDescription());
                    }
                    return dev.getName();
                }
            }
        }
        return null;
    }

    /**
     * Get network interface information.
     * @param source interface name.
     * @param address ipv4 address.
     * @param netmask netmask address.
     * @param netaddr network address.
     * @param broadaddr broadcast address.
     * @param dstaddr destination address.
     * @param macAddress mac address.
     * @param description description.
     * @return interface name.
     */
    @Deprecated
    public static String LookupNetworkInterface(String source, Inet4Address address,
                                                Inet4Address netmask,
                                                Inet4Address netaddr,
                                                Inet4Address broadaddr,
                                                Inet4Address dstaddr,
                                                MacAddress macAddress,
                                                StringBuilder description) {

        CheckNotNull(source);
        CheckNotNull(address);
        CheckNotNull(netmask);
        CheckNotNull(netaddr);
        CheckNotNull(broadaddr);
        CheckNotNull(dstaddr);
        CheckNotNull(description);

        StringBuilder errbuf = new StringBuilder();

        List<PcapIf> ifs = new ArrayList<PcapIf>();
        if (Jxnet.PcapFindAllDevs(ifs, errbuf) != Jxnet.OK) {
            return null;
        }

        description.setLength(0);

        for (PcapIf dev : ifs) {
            if (dev.getName().equals(source)) {
                for (PcapAddr addr : dev.getAddresses()) {
                    if (addr.getAddr().getData() == null || addr.getBroadAddr().getData() == null ||
                            addr.getNetmask().getData() == null) {
                        continue;
                    }
                    if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET &&
                            !Inet4Address.valueOf(addr.getAddr().getData()).equals(Inet4Address.ZERO) &&
                            !Inet4Address.valueOf(addr.getAddr().getData()).equals(Inet4Address.LOCALHOST) &&
                            !Inet4Address.valueOf(addr.getBroadAddr().getData()).equals(Inet4Address.ZERO) &&
                            !Inet4Address.valueOf(addr.getNetmask().getData()).equals(Inet4Address.ZERO)
                            ) {
                        address.update(Inet4Address.valueOf(addr.getAddr().getData()));
                        netmask.update(Inet4Address.valueOf(addr.getNetmask().getData()));
                        netaddr.update(Inet4Address.valueOf(address.toInt() & netmask.toInt()));
                        broadaddr.update(Inet4Address.valueOf(addr.getBroadAddr().getData()));
                        if (addr.getDstAddr().getData() != null) {
                            dstaddr.update(Inet4Address.valueOf(addr.getDstAddr().getData()));
                        } else {
                            dstaddr.update(Inet4Address.ZERO);
                        }
                        macAddress.update(MacAddress.fromNicName(dev.getName()));
                        if (dev.getDescription() != null) {
                            description.append(dev.getDescription());
                        }
                        return dev.getName();
                    }
                }
            }
        }
        return null;
    }

}
