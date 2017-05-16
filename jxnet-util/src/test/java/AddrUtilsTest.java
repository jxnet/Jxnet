import com.ardikars.jxnet.Inet4Address;
import com.ardikars.jxnet.util.AddrUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by root on 16/05/17.
 */
public class AddrUtilsTest {

    @Test
    public void run() {
        try {
            Inet4Address gw = AddrUtils.GetGatewayAddress();
            System.out.println(gw);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
