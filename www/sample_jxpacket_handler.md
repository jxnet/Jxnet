
## Jxpacket Handler Configuration

```java
@EnablePacket
@Configuration
public class DefaultJxpacketHandler implements JxpacketHandler<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultJxpacketHandler.class.getName());

    private static final String PRETTY_FOOTER = "+---------------------------------------------------"
            + "--------------------------------------------------+";

    private void print(Pair<PcapPktHdr, Packet> packet) {
        Iterator<Packet> iterator = packet.getRight().iterator();
        LOGGER.info("Pcap packet header : {}", packet.getLeft());
        LOGGER.info("Packet header      : ");
        while (iterator.hasNext()) {
            LOGGER.info("{}", iterator.next());
        }
        LOGGER.info(PRETTY_FOOTER);
    }

    @Override
    public void next(String argument, Future<Pair<PcapPktHdr, Packet>> packet) throws ExecutionException, InterruptedException {
        LOGGER.info("User argument      : {}", argument);
        print(packet.get());
    }

}
```