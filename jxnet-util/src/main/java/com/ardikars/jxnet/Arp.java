
package com.ardikars.jxnet;

import com.ardikars.jxnet.exception.NotSupportedPlatformException;
import com.ardikars.jxnet.util.Platforms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;

public final class Arp {


    public static <T> void loop(Handler<T> handler) throws IOException {
        String className = ((ParameterizedType) handler.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
    }


    public static class ArpEntry implements Builder<ArpEntry> {

        private Inet4Address inet4Address;
        private MacAddress hardwareAddress;

        public ArpEntry(Inet4Address inet4Address, MacAddress hardwareAddress) {
            this.inet4Address = inet4Address;
            this.hardwareAddress = hardwareAddress;
        }

        public Inet4Address getInet4Address() {
            return inet4Address;
        }

        public MacAddress getHardwareAddress() {
            return hardwareAddress;
        }

        @Override
        public ArpEntry build() {
            return this;
        }

    }

}
