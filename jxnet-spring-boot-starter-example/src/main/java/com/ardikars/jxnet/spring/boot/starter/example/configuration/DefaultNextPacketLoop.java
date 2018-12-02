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

package com.ardikars.jxnet.spring.boot.starter.example.configuration;

import com.ardikars.common.tuple.Pair;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.spring.boot.autoconfigure.jxpacket.JxpacketContext;
import com.ardikars.jxpacket.common.Packet;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Default next packet loop.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.4.9
 */
@Component
public class DefaultNextPacketLoop {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultJxpacketHandler.class.getName());

    private static final String PRETTY_FOOTER = "+---------------------------------------------------"
            + "--------------------------------------------------+";

    @Autowired
    private JxpacketContext context;

    @Autowired
    @Qualifier("com.ardikars.jxnet.executorService")
    private ExecutorService executorService;

    private void print(Future<Pair<PcapPktHdr, Packet>> future) throws ExecutionException, InterruptedException {
        Pair<PcapPktHdr, Packet> packet = future.get();
        Iterator<Packet> iterator = packet.getRight().iterator();
        LOGGER.info("Pcap packet header : {}", packet.getLeft());
        LOGGER.info("Packet header      : ");
        while (iterator.hasNext()) {
            LOGGER.info("{}", iterator.next());
        }
        LOGGER.info(PRETTY_FOOTER);
    }

    /**
     * Loop.
     * @param count count.
     * @throws ExecutionException execution exception.
     * @throws InterruptedException intterrupted exception.
     */
    public void loop(int count) throws ExecutionException, InterruptedException {
        if (count == -1) {
            while (true) {
                print(context.nextPacket());
            }
        } else {
            for (int i = 0; i < count; i++) {
                print(context.nextPacket());
            }
            executorService.shutdownNow();
        }
    }

}
