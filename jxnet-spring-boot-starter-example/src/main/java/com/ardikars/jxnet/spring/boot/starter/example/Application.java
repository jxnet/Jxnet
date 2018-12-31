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

package com.ardikars.jxnet.spring.boot.starter.example;

import com.ardikars.jxnet.spring.boot.autoconfigure.AbstractJxnetApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import com.ardikars.jxnet.spring.boot.starter.example.configuration.DefaultNextPacketLoop;

@SpringBootApplication
public class Application extends AbstractJxnetApplicationRunner {

    public static final int MAX_PACKET = -1; // infinite loop

    @Override
    public void run(String... args) throws Exception {
        showSystemInfo();
        showNetworkInfo();
        context.pcapLoop(MAX_PACKET, pcapHandler, "Jxnet!");
        //nextPacketLoop.loop(MAX_PACKET);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
