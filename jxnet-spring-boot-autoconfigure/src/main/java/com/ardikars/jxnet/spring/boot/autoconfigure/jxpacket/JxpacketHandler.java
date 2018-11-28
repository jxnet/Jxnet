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

package com.ardikars.jxnet.spring.boot.autoconfigure.jxpacket;

import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.spring.boot.autoconfigure.PacketHandler;
import com.ardikars.jxpacket.common.Packet;
import com.ardikars.jxpacket.common.UnknownPacket;
import com.ardikars.jxpacket.core.ethernet.Ethernet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.nio.ByteBuffer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * Jxpacket handler.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.4.8
 */
@ConditionalOnClass(Packet.class)
@ConditionalOnBean(PacketHandler.class)
@Configuration("com.ardikars.jxnet.spring.boot.autoconfigure.jxpacket.jxpacketHandler")
public class JxpacketHandler<T> implements PcapHandler<T> {

    private final int rawDataLinkType;
    private final PacketHandler<T> packetHandler;

    public JxpacketHandler(DataLinkType dataLinkType, PacketHandler<T> packetHandler) {
        this.rawDataLinkType = dataLinkType != null ? dataLinkType.getValue() : 1;
        this.packetHandler = packetHandler;
    }

    @Override
    public void nextPacket(T user, PcapPktHdr h, ByteBuffer bytes) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.directBuffer(bytes.capacity());
        buffer.setBytes(0, bytes);
        Packet packet;
        if (rawDataLinkType == 1) {
            packet = Ethernet.newPacket(buffer);
        } else {
            packet = UnknownPacket.newPacket(buffer);
        }
        packetHandler.next(user, h, packet);
    }

}
