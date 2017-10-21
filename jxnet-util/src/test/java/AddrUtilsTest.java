import com.ardikars.jxnet.Inet4Address;
import com.ardikars.jxnet.MacAddress;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by root on 16/05/17.
 */
public class AddrUtilsTest {

    @Test
    public void run() {
        Inet4Address address = Inet4Address.valueOf(0);
        Inet4Address netmask = Inet4Address.valueOf(0);
        Inet4Address netaddr = Inet4Address.valueOf(0);
        Inet4Address broadaddr = Inet4Address.valueOf(0);
        Inet4Address dstaddr = Inet4Address.valueOf(0);
        MacAddress macAddress = MacAddress.valueOf(0);
        StringBuilder description = new StringBuilder();

        System.out.println("Source              : " + AddrUtils.LookupNetworkInterface(
                address, netmask, netaddr, broadaddr, dstaddr, macAddress, description
        ));
        System.out.println("Address             : " + address);
        System.out.println("Netmask Address     : " + netmask);
        System.out.println("Network Address     : " + netaddr);
        System.out.println("Broadcast Address   : " + broadaddr);
        System.out.println("Destination Address : " + dstaddr);
        System.out.println("Mac Address         : " + macAddress);
        System.out.println("Description         : " + description);
        try {
            System.out.println("Gateway Address     : " + AddrUtils.GetGatewayAddress());
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
