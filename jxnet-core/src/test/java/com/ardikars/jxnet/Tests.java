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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.ByteBuffer;

@RunWith(JUnit4.class)
public class Tests {

	@Test
	public void ex() {
		StringBuilder errbuf = new StringBuilder();
		Pcap pcap = Pcap.dead(
				Pcap.builder()
				.source("wlp2s0")
				.errbuf(errbuf)
				.dataLinkType(DataLinkType.EN10MB)
		);


		Jxnet.PcapActivate(pcap);
//		System.out.println(pcap.isDead() + " << dead");
//		Jxnet.PcapLoop(pcap, 5, new PcapHandler<Object>() {
//			@Override
//			public void nextPacket(Object user, PcapPktHdr h, ByteBuffer bytes) {
//				System.out.println(bytes);
//			}
//		}, null);
//		BpfProgram bpfProgram = BpfProgram.bpf(
//				BpfProgram.builder()
//				.filter("icmp")
//				.pcap(pcap)
//		);
//		System.out.println(bpfProgram);
//		Jxnet.PcapFreeCode(bpfProgram);
		Jxnet.PcapClose(pcap);

	}

}
