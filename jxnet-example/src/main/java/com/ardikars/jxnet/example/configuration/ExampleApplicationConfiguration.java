package com.ardikars.jxnet.example.configuration;

import com.ardikars.jxnet.PromiscuousMode;
import com.ardikars.jxnet.annotation.Configuration;
import com.ardikars.jxnet.annotation.Order;
import com.ardikars.jxnet.annotation.Property;

@Order(1)
@Configuration("exampleApplicationConfigutaion")
public class ExampleApplicationConfiguration {

	@Property("errbuf")
	public StringBuilder errbuf() {
		return new StringBuilder();
	}

	@Property("filter")
	public String filter() {
		return "icmp";
	}

	@Property("snaplen")
	public int snaplen() {
		return 65535;
	}

	@Property("promisc")
	public int promisc() {
		return PromiscuousMode.PRIMISCUOUS.getValue();
	}

	@Property("timeout")
	public int timeout() {
		return 2000;
	}

}
