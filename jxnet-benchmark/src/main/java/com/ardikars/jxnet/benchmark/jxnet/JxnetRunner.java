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

import com.ardikars.jxnet.Application;
import com.ardikars.jxnet.Context;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.PcapTimestampPrecision;
import com.ardikars.jxnet.benchmark.Runner;
import java.nio.ByteBuffer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
 */
@Component("jxnetRunner")
public class JxnetRunner implements Runner {

    @Value("${jxnet.file}")
    private String pcapFile;

    @Override
    public long run() {
        final StringBuilder errbuf = new StringBuilder();
        final long before = System.currentTimeMillis();
        final Pcap.Builder builder = new Pcap.Builder()
                .timestampPrecision(PcapTimestampPrecision.NANO)
                .fileName(pcapFile)
                .errbuf(errbuf)
                .pcapType(Pcap.PcapType.OFFLINE);

        Application.run("application", "Application", "", builder);
        Context context = Application.getApplicationContext();
        context.pcapLoop(-1, new PcapHandler<String>() {
            @Override
            public void nextPacket(String user, PcapPktHdr h, ByteBuffer bytes) {
                // do nothing
            }
        }, "");
        long now = System.currentTimeMillis();
        context.pcapClose();
        return now - before;
    }

}
