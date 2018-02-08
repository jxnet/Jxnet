package com.test;

import com.ardikars.jxnet.Application;
import com.ardikars.jxnet.ApplicationInitializer;
import com.ardikars.jxnet.annotation.Inject;

public class AppTest {

	public static void main(String[] args) {
		ApplicationInitializer initializer = new ApplicationInitializer() {
			@Override
			public void initialize(Application.Context context) {
				context.configuration("com.test");
			}
		};
		Application.run("AppTest", "0.0.1", initializer);

		Application.Context context = Application.getApplicationContext();

		System.out.println(context.getProperty("injectTest"));


	}

}
