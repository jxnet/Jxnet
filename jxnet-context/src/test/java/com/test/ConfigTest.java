package com.test;

import com.ardikars.jxnet.annotation.Inject;
import com.ardikars.jxnet.annotation.Property;

@Property("configTest")
public class ConfigTest {

	@Property("isMale")
	public boolean isMale = true;

	@Property("nameProperty")
	public String nameProperty() {
		return new String("Ardika Rommy Sanjaya");
	}

}
