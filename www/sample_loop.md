
## Simple Packet Handler

```java
@Component
public class DefaultNextPacketLoop {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultJxpacketHandler.class.getName());

    private static final String PRETTY_FOOTER = "+---------------------------------------------------"
            + "--------------------------------------------------+";

    @Autowired
    private JxpacketContext context;

    @Autowired
    @Qualifier("com.ardikars.jxnet.executorService")
    private ExecutorService executorService;

    private void print(Future<Pair<PcapPktHdr, Packet>> future) throws ExecutionException, InterruptedException {
        Pair<PcapPktHdr, Packet> packet = future.get();
        Iterator<Packet> iterator = packet.getRight().iterator();
        LOGGER.info("Pcap packet header : {}", packet.getLeft());
        LOGGER.info("Packet header      : ");
        while (iterator.hasNext()) {
            LOGGER.info("{}", iterator.next());
        }
        LOGGER.info(PRETTY_FOOTER);
    }

    public void loop(int count) throws ExecutionException, InterruptedException {
        if (count == -1) {
            while (true) {
                print(context.nextPacket());
            }
        } else {
            for (int i = 0; i < count; i++) {
                print(context.nextPacket());
            }
            executorService.shutdownNow();
        }
    }

}
```