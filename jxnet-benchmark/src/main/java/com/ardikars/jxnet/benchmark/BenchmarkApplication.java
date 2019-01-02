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

package com.ardikars.jxnet.benchmark;

import com.ardikars.jxnet.context.Context;
import java.util.concurrent.ExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
@SpringBootApplication
public class BenchmarkApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BenchmarkApplication.class);

    @Autowired
    Context context;

    @Autowired
    ExecutorService executorService;

    @Autowired
    @Qualifier("jxnetRunner")
    Runner jxnetRunner;

    @Autowired
    @Qualifier("jxnetWithThreadPoolRunner")
    Runner jxnetWithThreadPoolRunner;

    @Autowired
    @Qualifier("springJxnetWithThreadPoolRunner")
    Runner springJxnetWithThreadPoolRunner;

    @Autowired
    @Qualifier("pcap4jRunner")
    Runner pcap4jRunner;

    @Autowired
    @Qualifier("pcap4jWithThreadPoolRunner")
    Runner pcap4jWithThreadPoolRunner;

    @Autowired
    @Qualifier("pcap4jPacketWithThreadPoolRunner")
    Runner pcap4jPacketWithThreadPoolRunner;

    /**
     * Run bencmarking test.
     * @param args args.
     */
    public void run(String... args) {
        int maxIteration = 10;
        int totalMoreFast = 0;
        int totalMoreFastWithThreadPool = 0;
        int totalMoreFastPacketThreadPool = 0;
        for (int i = 0; i < maxIteration; i++) {
            LOGGER.info("**********************************");
            long jxnetRunnerRes = jxnetRunner.run();
            long jxnetWithThreadPoolRunnerRes = jxnetWithThreadPoolRunner.run();
            long jxnetPacketThreadPoolRunnerRes = springJxnetWithThreadPoolRunner.run();
            long pcap4jRunnerRes = pcap4jRunner.run();
            long pcap4jWithThreadPoolRunnerRes = pcap4jWithThreadPoolRunner.run();
            long pcap4jPacketThreadPoolRunnerRes = pcap4jPacketWithThreadPoolRunner.run();
            LOGGER.info("Jxnet x Pcap4j");
            boolean moreFast = jxnetRunnerRes < pcap4jRunnerRes;
            boolean moreFastWithThreadPool = jxnetWithThreadPoolRunnerRes < pcap4jWithThreadPoolRunnerRes;
            boolean moreFastPacketThreadPool = jxnetPacketThreadPoolRunnerRes < pcap4jPacketThreadPoolRunnerRes;
            if (moreFast) {
                totalMoreFast++;
            }
            if (moreFastWithThreadPool) {
                totalMoreFastWithThreadPool++;
            }
            if (moreFastPacketThreadPool) {
                totalMoreFastPacketThreadPool++;
            }
            LOGGER.info("Is Jxnet runner more fast? {} : {}",
                     moreFast ? "YES" : "NO",
                    jxnetRunnerRes + " and " + pcap4jRunnerRes);
            LOGGER.info("Is Jxnet with thread pool runner more fast? {} : {}",
                    moreFastWithThreadPool ? "YES" : "NO",
                    jxnetWithThreadPoolRunnerRes + " and " + pcap4jWithThreadPoolRunnerRes);
            LOGGER.info("IS Jxnet packet with thread pool runner more fast? {} : {}",
                    moreFastPacketThreadPool ? "YES" : "NO",
                    jxnetPacketThreadPoolRunnerRes + " and " + pcap4jPacketThreadPoolRunnerRes);
            LOGGER.info("**********************************\n");
        }
        LOGGER.info("Total jxnet more fast                                 : {}/{}", totalMoreFast, maxIteration);
        LOGGER.info("Total jxnet more fast with thread pool                : {}/{}", totalMoreFastWithThreadPool, maxIteration);
        LOGGER.info("Total jxnet more fast packet decoder with thread pool : {}/{}", totalMoreFastPacketThreadPool, maxIteration);
        executorService.shutdownNow();
    }

    public static void main(String[] args) {
        SpringApplication.run(BenchmarkApplication.class, args);
    }

}
