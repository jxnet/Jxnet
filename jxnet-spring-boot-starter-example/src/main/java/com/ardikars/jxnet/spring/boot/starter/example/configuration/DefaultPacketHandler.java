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

import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.spring.boot.autoconfigure.PacketHandler;
import com.ardikars.jxpacket.common.Packet;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author jxnet 2018/11/29
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
 */
@Configuration
public class DefaultPacketHandler implements PacketHandler<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPacketHandler.class.getName());

    private static final String PRETTY_FOOTER = "+---------------------------------------------------"
            + "--------------------------------------------------+";

    @Override
    public void next(String argument, PcapPktHdr header, Future<Packet> packet) throws ExecutionException, InterruptedException {
        Iterator<Packet> iterator = packet.get().iterator();
        while (iterator.hasNext()) {
            LOGGER.info(iterator.next().toString());
        }
        LOGGER.info(PRETTY_FOOTER);
    }

}
