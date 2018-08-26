package com.ardikars.jxnet;

import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.net.NetworkInterface;
import com.ardikars.common.util.Loader;
import com.ardikars.jxnet.util.DefaultLibraryLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.SocketException;
import java.util.Collection;

@RunWith(JUnit4.class)
public class LoaderTest {

	public static class Initializer implements ApplicationInitializer<String> {

		@Override
		public Loader<Void> initialize(String additionalInformation) {
			return new DefaultLibraryLoader();
		}

	}

	@Test
	public void test01LoadLibrary() throws SocketException {

		StringBuilder errbuf = new StringBuilder();
		Pcap.Builder pcapBuilder = Pcap.builder()
				.source(getDevice())
				.immediateMode(ImmediateMode.IMMEDIATE)
				.errbuf(errbuf)
				.pcapType(Pcap.PcapType.LIVE);
		BpfProgram.Builder bpfProgramBuilder = BpfProgram.builder()
				.bpfCompileMode(BpfProgram.BpfCompileMode.OPTIMIZE)
				.filter("tcp")
				.netmask(Inet4Address.valueOf("255.255.255.0").toInt());
		Application.run("TestApp", "0.0.1", Initializer.class, pcapBuilder, bpfProgramBuilder, "");
		Application.getApplicationContext().pcapClose();
		assert true;
	}

	public static String getDevice() {
		Collection<NetworkInterface> networkInterfaces = null;
		try {
			networkInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			return null;
		}
		for (NetworkInterface networkInterface : networkInterfaces) {
			if (networkInterface.getMtu() == 1500 && networkInterface.getHardwareAddress() != null && networkInterface.getAddresses().size() > 0) {
				return networkInterface.getName();
			}
		}
		return null;
	}

}
