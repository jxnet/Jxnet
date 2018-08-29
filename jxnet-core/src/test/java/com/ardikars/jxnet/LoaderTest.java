package com.ardikars.jxnet;

import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.net.MacAddress;
import com.ardikars.common.net.NetworkInterface;
import com.ardikars.common.util.Loader;
import com.ardikars.jxnet.util.DefaultLibraryLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.SocketException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RunWith(JUnit4.class)
public class LoaderTest {

	public static final String KEY = "jxnet";
	public static final String VALUE = "ROCK!!!...";

	public static class Initializer implements ApplicationInitializer<Map<String, Object>> {

		@Override
		public Loader<Void> initialize(Map<String, Object> additionalInformation) {
			additionalInformation.put(KEY, VALUE);
			return new DefaultLibraryLoader();
		}

	}

	@Test
	public void test01LoadLibrary() {
		Map<String, Object> parameter = new HashMap<>();
		StringBuilder errbuf = new StringBuilder();
		Pcap.Builder pcapBuilder = Pcap.builder()
				.source(getDevice())
				.immediateMode(ImmediateMode.IMMEDIATE)
				.errbuf(errbuf)
				.pcapType(Pcap.PcapType.LIVE);
		Application.run(Initializer.class, pcapBuilder, parameter);
		Context context = Application.getApplicationContext();
		if (Application.getAdditionalInformation() instanceof Map) {
			Map additionalInformation = (Map) Application.getAdditionalInformation();
			assert additionalInformation.get(KEY).equals(VALUE);
		}
		assert true;
	}

	@Test
	public void getDeviceTest() {
		System.out.println("DEVICE" + getDevice());
	}

	public static String getDevice() {
		Collection<NetworkInterface> networkInterfaces = null;
		try {
			networkInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			return null;
		}
		for (NetworkInterface networkInterface : networkInterfaces) {
			if (networkInterface.getHardwareAddress() != null && networkInterface.getHardwareAddress() instanceof MacAddress) {
				MacAddress macAddress = (MacAddress) networkInterface.getHardwareAddress();
				if (!macAddress.equals(MacAddress.ZERO)) {
					for (NetworkInterface.Address inetAddress : networkInterface.getAddresses()) {
						if (inetAddress.getInetAddress() instanceof Inet4Address) {
							Inet4Address inet4Address = (Inet4Address) inetAddress.getInetAddress();
							if (!inet4Address.equals(Inet4Address.LOCALHOST) && !inet4Address.equals(Inet4Address.ZERO)) {
								return networkInterface.getName();
							}
						}
					}
				}
			}
		}
		return null;
	}

}
