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

import static com.ardikars.jxnet.Jxnet.OK;
import static com.ardikars.jxnet.Jxnet.PcapCanSetRfMon;
import static com.ardikars.jxnet.Jxnet.PcapClose;
import static com.ardikars.jxnet.Jxnet.PcapDataLink;
import static com.ardikars.jxnet.Jxnet.PcapGetErr;
import static com.ardikars.jxnet.Jxnet.PcapOpenDead;
import static com.ardikars.jxnet.Jxnet.PcapSetDirection;
import static com.ardikars.jxnet.Jxnet.PcapSetRfMon;
import static com.ardikars.jxnet.Jxnet.PcapSnapshot;
import static com.ardikars.jxnet.Jxnet.PcapStrError;

import com.ardikars.common.net.Inet4Address;
import com.ardikars.jxnet.exception.NativeException;
import com.ardikars.jxnet.exception.PlatformNotSupportedException;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FreakTest {

    private Logger logger = Logger.getLogger(FreakTest.class.getName());

    private Pcap pcap;
    private int snaplen = 65535;
    private DataLinkType linkType = DataLinkType.LINUX_SLL;

    private int resultCode;

    /**
     * Initialize
     */
    @Before
    public void create() {
        StringBuilder errbuf = new StringBuilder();
        Pcap.Builder pcapBuilder = Pcap.builder()
                .source(LoaderTest.getDevice())
                .immediateMode(ImmediateMode.IMMEDIATE)
                .pcapType(Pcap.PcapType.LIVE)
                .errbuf(errbuf);
        Application.run(LoaderTest.Initializer.class, pcapBuilder, "");
        Application.getApplicationContext().pcapClose();
        pcap = PcapOpenDead(linkType.getValue(), snaplen);
        if (pcap == null) {
            logger.warning("create:PcapOpenDead(): ");
            return;
        }
    }

    @Test
    public void Test01_PcapGetErr() {
        System.out.println("Pcap Error: " + PcapGetErr(pcap));
    }

    @Test
    public void Test02_PcapDatalink() {
        System.out.println("Data Link Type: " + PcapDataLink(pcap));
    }

    @Test
    public void Test03_PcapSnaplen() {
        System.out.println("Snapshot: " + PcapSnapshot(pcap));
    }

    @Test
    public void Test04_PcapSetDirection() {
        try {
            try {
                if ((resultCode = PcapSetDirection(pcap, PcapDirection.PCAP_D_IN)) != OK) {
                    logger.warning("PcapSetDirection:PcapSetDirection() " + PcapStrError(resultCode));
                    return;
                }
            } catch (NativeException e) {
                logger.warning(e.getMessage());
            }
        } catch (PlatformNotSupportedException e) {
            logger.warning(e.getMessage());
        }
    }

    @Test
    public void Test25_PcapCanSetRfMonAndPcapSetRfMon() {
        try {
            if (PcapCanSetRfMon(pcap) == 1) {
                if ((resultCode = PcapSetRfMon(pcap, 1)) != OK) {
                    logger.warning("PcapCanSetRfMonAndPcapSetRfMon:PcapCanSetRfMon(): " + PcapStrError(resultCode));
                    return;
                }
            }
        } catch (NativeException e) {
            logger.warning(e.getMessage());
        }
    }

    /**
     * Destroy
     */
    @After
    public void destroy() {
        PcapClose(pcap);
    }

}
