package com.ardikars.jxnet.spring.boot.autoconfigure.jxpacket;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.tuple.Pair;
import com.ardikars.common.tuple.Tuple;
import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.spring.boot.autoconfigure.JxpacketAsyncHandler;
import com.ardikars.jxpacket.common.Packet;
import com.ardikars.jxpacket.common.UnknownPacket;
import com.ardikars.jxpacket.core.ethernet.Ethernet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.*;

@ConditionalOnClass({Packet.class, ByteBuf.class})
@Configuration(JXPACKET_ASYNC_HANDLER_CONFIGURATION_BEAN_NAME)
public class JxpacketAsyncHandlerConfiguration<T> implements PcapHandler<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JxpacketHandlerConfiguration.class);

    private final int rawDataLinkType;
    private final JxpacketAsyncHandler<T> packetHandler;
    private final ExecutorService executorService;

    public JxpacketAsyncHandlerConfiguration(@Qualifier(EXECUTOR_SERVICE_BEAN_NAME) ExecutorService executorService,
                                             @Qualifier(DATALINK_TYPE_BEAN_NAME) DataLinkType dataLinkType,
                                             JxpacketAsyncHandler<T> packetHandler) {
        this.rawDataLinkType = dataLinkType != null ? dataLinkType.getValue() : 1;
        this.packetHandler = packetHandler;
        this.executorService = executorService;
    }

    @Override
    public void nextPacket(T user, PcapPktHdr h, ByteBuffer bytes) {
        CompletableFuture<Pair<PcapPktHdr, Packet>> packet = CompletableFuture.supplyAsync(new Supplier<Pair<PcapPktHdr, Packet>>() {
            @Override
            public Pair<PcapPktHdr, Packet> get() {
                ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
                Packet packet;
                if (rawDataLinkType == 1) {
                    packet = Ethernet.newPacket(buffer);
                } else {
                    packet = UnknownPacket.newPacket(buffer);
                }
                return Tuple.of(h, packet);
            }
        }, executorService);
        try {
            packetHandler.next(user, packet);
        } catch (ExecutionException | InterruptedException e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(e.getMessage());
            }
        }
    }

}
