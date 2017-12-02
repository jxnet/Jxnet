import com.ardikars.jxnet.Application;
import com.ardikars.jxnet.ApplicationInitializer;
import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.StaticLibrary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Teststests {

    @Test
    public void test() {
        ApplicationInitializer applicationInitializer = new ApplicationInitializer() {
            @Override
            public void initialize(Application.Context context) {
                context.addLibrary(new StaticLibrary());
            }
        };
        Application.run("Apls", "1", applicationInitializer);
        System.out.println(Jxnet.PcapLibVersion());
    }

}
