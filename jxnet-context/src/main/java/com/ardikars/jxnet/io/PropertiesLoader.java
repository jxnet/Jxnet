package com.ardikars.jxnet.io;

import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader extends ResourceLoader<Properties> {

	private Properties properties;

	public PropertiesLoader(String path) {
		super(path);
	}

	public PropertiesLoader(String path, Class aClass) {
		super(path, aClass);
	}

	public PropertiesLoader(String path, ClassLoader classLoader) {
		super(path, classLoader);
	}

	public PropertiesLoader(String path, Class aClass, ClassLoader classLoader) {
		super(path, aClass, classLoader);
	}

	@Override
	public Properties getData() throws IOException{
		if (properties == null) {
			properties = new Properties();
			properties.load(getInputStream());
		}
		return properties;
	}

}
