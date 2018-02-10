package com.ardikars.jxnet.example;

import com.ardikars.jxnet.Application;
import com.ardikars.jxnet.ApplicationInitializer;
import com.ardikars.jxnet.DynamicLibrary;
import com.ardikars.jxnet.StaticLibrary;
import com.ardikars.jxnet.annotation.Configuration;
import com.ardikars.jxnet.annotation.Order;

@Order(3)
@Configuration("exampleAppicationInitializer")
public class ExampleApplicationInitializer implements ApplicationInitializer {



	@Override
	public void initialize(Application.Context context) {
//		context.addLibrary(new DynamicLibrary());
//		context.addLibrary(new StaticLibrary());
		context.configuration("com.ardikars.jxnet.example");
	}

}
