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

package com.ardikars.jxnet.benchmark.jxnet;

import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.benchmark.Runner;
import com.ardikars.jxnet.context.Context;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
@Component("springJxnetWithThreadPoolRunner")
public class SpringJxnetWithThreadPoolRunner implements Runner {

    private Context context;
    private final PcapHandler<String> pcapHandler;
    private final Pcap.Builder builder;

    /**
     * Constractor.
     * @param context pcap context.
     * @param pcapHandler pcap handler.
     * @param builder pcap builder.
     */
    public SpringJxnetWithThreadPoolRunner(Context context, PcapHandler<String> pcapHandler, Pcap.Builder builder) {
        this.context = context;
        this.pcapHandler = pcapHandler;
        this.builder = builder;
    }

    @Override
    public long run() {
        long before = System.currentTimeMillis();
        context.pcapLoop(-1, pcapHandler, "Jxnet!.");
        long now = System.currentTimeMillis();
        long epoch = now - before;
        context.pcapClose();
        context = context.newInstance(builder);
        return epoch;
    }

}
