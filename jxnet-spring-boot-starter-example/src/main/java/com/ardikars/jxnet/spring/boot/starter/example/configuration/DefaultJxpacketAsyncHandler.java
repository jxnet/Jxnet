package com.ardikars.jxnet.spring.boot.starter.example.configuration;

import com.ardikars.common.tuple.Pair;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.spring.boot.autoconfigure.JxpacketAsyncHandler;
import com.ardikars.jxnet.spring.boot.autoconfigure.annotation.EnablePacket;
import com.ardikars.jxnet.spring.boot.autoconfigure.constant.PacketHandlerType;
import com.ardikars.jxpacket.common.Packet;
import org.springframework.context.annotation.Configuration;

import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@EnablePacket(packetHandlerType = PacketHandlerType.JXPACKET_ASYNC)
@Configuration
public class DefaultJxpacketAsyncHandler implements JxpacketAsyncHandler<String> {

    @Override
    public void next(String argument, CompletableFuture<Pair<PcapPktHdr, Packet>> packet) throws ExecutionException, InterruptedException {
        packet.thenAccept(pcapPktHdrPacketPair -> {
            Iterator<Packet> packetIterator = pcapPktHdrPacketPair.getRight().iterator();
            while (packetIterator.hasNext()) {
                Packet p = packetIterator.next();
                System.out.println(p);
            }
        });
    }

}
