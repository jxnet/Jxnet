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

package com.ardikars.jxnet.spring.boot.starter.example;

import com.ardikars.common.util.Hexs;
import com.ardikars.jxnet.Context;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapPktHdr;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner  {

    public static final int MAX_PACKET = -1;

    public static final int WAIT_TIME_FOR_THREAD_TERMINATION = 10000;

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class.getName());

    @Autowired
    private Context context;

    @Override
    public void run(String... args) throws Exception {
        final ExecutorService pool = Executors.newCachedThreadPool();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                pool.shutdownNow();
            }
        });
        context.pcapLoop(MAX_PACKET, new PcapHandler<String>() {
            @Override
            public void nextPacket(String user, PcapPktHdr pktHdr, ByteBuffer buffer) {
                byte[] bytes = new byte[buffer.capacity()];
                buffer.get(bytes, 0, bytes.length);
                String hexDump = Hexs.toPrettyHexDump(bytes);
                LOGGER.info("User argument : " + user);
                LOGGER.info("Packet header : " + pktHdr);
                LOGGER.info("Packet buffer : \n" + hexDump);
            }
        }, "Jxnet!", pool);
		pool.shutdown();
		pool.awaitTermination(WAIT_TIME_FOR_THREAD_TERMINATION, TimeUnit.MICROSECONDS);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
