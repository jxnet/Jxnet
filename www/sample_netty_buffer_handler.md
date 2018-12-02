
## Netty Buffer Handler Configuration

```java
@EnablePacket(packetHandlerType = PacketHandlerType.NETTY_BUFFER)
@Configuration
public class DefaultNettyBufferHandler implements NettyBufferHandler<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultJxpacketHandler.class.getName());

    private static final String PRETTY_FOOTER = "+---------------------------------------------------"
            + "--------------------------------------------------+";

    private void print(Pair<PcapPktHdr, ByteBuf> packet) {
        LOGGER.info("Pcap packet header : {}", packet.getLeft());
        LOGGER.info("Packet header      : {}", packet.getRight());
        LOGGER.info(PRETTY_FOOTER);
    }

    @Override
    public void next(String argument, Future<Pair<PcapPktHdr, ByteBuf>> packet) throws ExecutionException, InterruptedException {
        LOGGER.info("User argument      : {}", argument);
        print(packet.get());
    }

}
```