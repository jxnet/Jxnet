import com.ardikars.jxnet.Application;
import com.ardikars.jxnet.ApplicationContext;
import com.ardikars.jxnet.ApplicationInitializer;

public class App {

    static class Initializer implements ApplicationInitializer {

        @Override
        public void initialize(Application.Context context) {
            context.addProperty("MyKey", new Float("67.7"));
        }

    }

    public static void main(String[] args) {
        Application.run("App", "1", new Initializer());
        Application.Context context = Application.getApplicationContext();

        ApplicationContext context1  = (ApplicationContext) Application.getApplicationContext();
        System.out.println("App name     : " + context1.getApplicationName());
        System.out.println("App version  : " + context1.getApplicationVersion());
        System.out.println("Property     : " + context1.getProperty("MyKey"));
    }

}
