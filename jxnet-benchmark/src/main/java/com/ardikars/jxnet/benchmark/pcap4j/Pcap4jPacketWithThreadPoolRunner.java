/**
 * Copyright (C) 2015-2018 Jxnet
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

package com.ardikars.jxnet.benchmark.pcap4j;

import com.ardikars.jxnet.benchmark.Runner;
import java.util.concurrent.ExecutorService;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author jxnet 2018/12/09
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
 */
@Component("pcap4jPacketWithThreadPoolRunner")
public class Pcap4jPacketWithThreadPoolRunner implements Runner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Pcap4jRunner.class);

    @Autowired
    private ExecutorService executorService;

    @Value("${jxnet.file}")
    private String pcapFile;

    @Override
    public long run() {
        long before = System.currentTimeMillis();
        PcapHandle handle = null;
        try {
            handle = Pcaps.openOffline(pcapFile, PcapHandle.TimestampPrecision.NANO);
            handle.loop(-1, new PacketListener() {
                @Override
                public void gotPacket(Packet packet) {
                    // do nothing
                }
            }, executorService);
        } catch (PcapNativeException e) {
            LOGGER.error(e.getMessage());
        } catch (NotOpenException e) {
            LOGGER.error(e.getMessage());
        } catch (InterruptedException e) {
            if (handle != null) {
                handle.close();
            }
            LOGGER.warn(e.getMessage());
            Thread.currentThread().interrupt();
        }
        long now = System.currentTimeMillis();
        if (handle != null) {
            handle.close();
        }
        return now - before;
    }

}
