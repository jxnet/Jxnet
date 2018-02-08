package com.test;

import com.ardikars.jxnet.annotation.Inject;
import com.ardikars.jxnet.annotation.Property;

@Property("injectTest")
public class InjectTest {

	@Inject("configTest")
	public ConfigTest configTest;

	@Inject("nameProperty")
	public String name;

	@Inject("isMale")
	public boolean isMale;

	@Override
	public String toString() {
		return "InjectTest{" +
				"name='" + name + '\'' +
				", isMale=" + isMale +
				'}';
	}

}
