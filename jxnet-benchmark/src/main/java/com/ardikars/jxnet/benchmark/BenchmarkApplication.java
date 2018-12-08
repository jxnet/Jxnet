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

import com.ardikars.jxnet.Context;
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

    /**
     * Run bencmarking test.
     * @param args args.
     */
    public void run(String... args) {
        int maxIteration = 10;
        int totalWin = 0;
        int totalWinWithThreadPool = 0;
        for (int i = 0; i < maxIteration; i++) {
            LOGGER.info("**********************************");
            long jxnetRunnerRes = jxnetRunner.run();
            long jxnetWithThreadPoolRunnerRes = jxnetWithThreadPoolRunner.run();
            long pcap4jRunnerRes = pcap4jRunner.run();
            long pcap4jWithThreadPoolRunnerRes = pcap4jWithThreadPoolRunner.run();
            LOGGER.info("Jxnet vs Pcap4j");
            boolean win = jxnetRunnerRes < pcap4jRunnerRes;
            boolean winWithThreadPool = jxnetWithThreadPoolRunnerRes < pcap4jWithThreadPoolRunnerRes;
            if (win) {
                totalWin++;
            }
            if (winWithThreadPool) {
                totalWinWithThreadPool++;
            }
            LOGGER.info("Is Jxnet runner win? {} : {}",
                     win ? "YES" : "NO",
                    jxnetRunnerRes + " vs " + pcap4jRunnerRes);
            LOGGER.info("Is Jxnet with thread pool runner win? {} : {}",
                    winWithThreadPool ? "YES" : "NO",
                    jxnetWithThreadPoolRunnerRes + " vs " + pcap4jWithThreadPoolRunnerRes);
            LOGGER.info("**********************************\n");
        }
        LOGGER.info("Total win                               : {}/{}", totalWin, maxIteration);
        LOGGER.info("Total win with thread pool              : {}/{}", totalWinWithThreadPool, maxIteration);
        LOGGER.info("Spring test with thread pool and future : {}", springJxnetWithThreadPoolRunner.run());
        executorService.shutdownNow();
    }

    public static void main(String[] args) {
        SpringApplication.run(BenchmarkApplication.class, args);
    }

}
