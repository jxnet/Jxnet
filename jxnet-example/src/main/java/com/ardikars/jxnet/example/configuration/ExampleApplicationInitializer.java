package com.ardikars.jxnet.example.configuration;

import com.ardikars.jxnet.Application;
import com.ardikars.jxnet.ApplicationInitializer;
import com.ardikars.jxnet.DynamicLibrary;
import com.ardikars.jxnet.StaticLibrary;

public class ExampleApplicationInitializer implements ApplicationInitializer {

	@Override
	public void initialize(Application.Context context) {
//		context.addLibrary(new DynamicLibrary());
//		context.addLibrary(new StaticLibrary());
		context.addPropertyFiles("classpath:application.properties");
		context.addPropertyPackages("com.ardikars.jxnet.example");
	}

}
