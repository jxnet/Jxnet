package com.ardikars.jxnet.example.configuration;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.annotation.Configuration;
import com.ardikars.jxnet.annotation.Order;
import com.ardikars.jxnet.annotation.Property;

@Order(1)
@Configuration("applicationConfiguration")
public class ApplicationConfiguration {

	public ApplicationConfiguration() {

	}

	@Order(3)
	@Property("errbuf")
	public StringBuilder errbuf() {
		return new StringBuilder();
	}

	@Order(5)
	public String libraryVersion () {
		return new String("DFDSF");
	}

}
