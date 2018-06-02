/**
 * Copyright (C) 2017-2018  Ardika Rommy Sanjaya <contact@ardikars.com>
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

package com.ardikars.jxnet;

import com.ardikars.jxnet.exception.DeviceNotFoundException;
import com.ardikars.jxnet.exception.NativeException;
import com.ardikars.jxnet.util.Validate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.4
 */
abstract class Core {

    protected Core() {

    }

    /**
     * Return the first connected device to the network.
     * @return returns PcapIf instance.
     * @since 1.1.5
     * @throws NativeException native exception.
     * @throws DeviceNotFoundException device with network connection not found.
     */
    public static PcapIf LookupNetworkInterface() throws NativeException, DeviceNotFoundException {
        final StringBuilder errbuf = new StringBuilder();
        final List<PcapIf> pcapIfs = new ArrayList<>();
        PcapIf result = null;
        if (Jxnet.PcapFindAllDevs(pcapIfs, errbuf) != Jxnet.OK) {
            throw new NativeException(errbuf.toString());
        }
        for (final PcapIf pcapIf : pcapIfs) {
            for (final PcapAddr pcapAddr : pcapIf.getAddresses()) {
                if (pcapAddr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
                    Inet4Address address = Inet4Address.valueOf(0);
                    Inet4Address netmask = Inet4Address.valueOf(0);
                    Inet4Address bcastaddr = Inet4Address.valueOf(0);
                    try {
                        address = Inet4Address.valueOf(pcapAddr.getAddr().getData());
                        netmask = Inet4Address.valueOf(pcapAddr.getNetmask().getData());
                        bcastaddr = Inet4Address.valueOf(pcapAddr.getBroadAddr().getData());
                        //Inet4Address dstaddr = Inet4Address.valueOf(pcapAddr.getDstAddr().getData());;
                    } catch (Exception e) {
                        errbuf.append(e.getMessage() + '\n');
                    }
                    if (!address.equals(Inet4Address.ZERO) && !address.equals(Inet4Address.LOCALHOST)
                            && !netmask.equals(Inet4Address.ZERO) && !bcastaddr.equals(Inet4Address.ZERO)) {
                        result = pcapIf;
                        break;
                    }
                }
            }
        }
        if (result != null) {
            return result;
        }
        errbuf.append("Device with network connention not found.\n");
        throw new DeviceNotFoundException(errbuf.toString());
    }

    /**
     * Select network interface.
     * @return returns selected PcapIf.
     * @since 1.1.5
     * @throws NativeException native exception.
     * @throws IOException IO exception.
     */
    public static PcapIf SelectNetowrkInterface() throws NativeException, IOException {
        final StringBuilder errbuf = new StringBuilder();
        final List<PcapIf> pcapIfs = new ArrayList<PcapIf>();
        if (Jxnet.PcapFindAllDevs(pcapIfs, errbuf) != Jxnet.OK) {
            throw new NativeException(errbuf.toString());
        }
        int index = 0;
        final StringBuilder sb = new StringBuilder(1000);
        for (final PcapIf pcapIf : pcapIfs) {
            sb.append("NO[").append(++index).append("]\t=> ");
            sb.append("NAME: ").append(pcapIf.getName()).append(" (").append(pcapIf.getDescription()).append(" )\n");
            for (final PcapAddr pcapAddr : pcapIf.getAddresses()) {
                sb.append("\t\tADDRESS: ").append(pcapAddr.getAddr().toString()).append('\n');
            }
        }
        System.out.println(sb.toString());
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
        PcapIf pcapIf = null;
        index = 0;
        while (index == 0) {
            System.out.print("Select a device number, or enter 'q' to quit -> ");
            try {
                final String input = reader.readLine();
                index = Integer.parseInt(input);
                if (index > pcapIfs.size() || index <= 0) {
                    index = 0;
                } else {
                    pcapIf = pcapIfs.get(index - 1);
                    reader.close();
                }
            } catch (NumberFormatException e) {
                index = 0;
            } catch (IOException e) {
                index = -1;
                errbuf.append(e.getMessage());
                try {
                    reader.close();
                } catch (IOException e1) {
                    errbuf.append(e1.getMessage());
                    throw new IOException(errbuf.toString());
                }
            }
        }
        return pcapIf;
    }

}
