package com.ardikars.test;

import org.junit.Assert;
import org.junit.Test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.util.Loader;

public class PcapLookupDev {

	@Test
	public void run() {
		StringBuilder errbuf = new StringBuilder();
        String dev = Jxnet.pcapLookupDev(errbuf);
        if(dev == null) {
            System.err.println(errbuf.toString());
        } else {
            System.out.println(dev);
        }
        Assert.assertNotEquals(null, dev);
	}
	
	static {
		Loader.loadLibrary();
	}
}
