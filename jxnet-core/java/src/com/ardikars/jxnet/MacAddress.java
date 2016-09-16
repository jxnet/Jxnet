
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

import java.util.Arrays;

import com.ardikars.jxnet.util.FormatUtils;

public final class MacAddress {
	
	public static final int MAC_ADDRESS_LENGTH = 6;
	
	public static final MacAddress ZERO = valueOf("00:00:00:00:00:00");
	
	public static final MacAddress BROADCAST = valueOf("ff:ff:ff:ff:ff:ff");
	
	public static final MacAddress IPV4_MULTICAST = valueOf("01:00:5e:00:00:00");
	
	public static final MacAddress IPV4_MULTICAST_MASK = valueOf("ff:ff:ff:80:00:00");
	
	private byte[] address = new byte[MacAddress.MAC_ADDRESS_LENGTH];

	private MacAddress(final byte[] address) {
		this.address = Arrays.copyOf(address, MacAddress.MAC_ADDRESS_LENGTH);
	}
	
	public static MacAddress valueOf(final String address) {
		final String[] elements = address.split(":");
		if (elements.length != MacAddress.MAC_ADDRESS_LENGTH) {
			throw new IllegalArgumentException(
					"Specified MAC Address must contain 12 hex digits" + " separated pairwise by :'s.");
		}
		final byte[] b = new byte[MacAddress.MAC_ADDRESS_LENGTH];
		for (int i = 0; i < MacAddress.MAC_ADDRESS_LENGTH; i++) {
			final String element = elements[i];
			b[i] = (byte) Integer.parseInt(element, 16);
		}
		return new MacAddress(b);
	}

	public static MacAddress valueOf(final byte[] address) {
        if (address.length != MacAddress.MAC_ADDRESS_LENGTH) {
            throw new IllegalArgumentException("the length is not "
                                                       + MacAddress.MAC_ADDRESS_LENGTH);
        }
        return new MacAddress(address);
    }
	
	public byte[] toByteArray() {
		return Arrays.copyOf(this.address, this.address.length);
	}
	
	public boolean isBroadcast() {
        for (final byte b : this.address) {
            if (b != -1) {
                return false;
            }
        }
        return true;
    }
	
	public boolean isMulticast() {
        if (this.isBroadcast()) {
            return false;
        }
        return (this.address[0] & 0x01) != 0;
    }
	
	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for(final byte b : this.address) {
        	if(sb.length() > 0) {
        		sb.append(":");
        	}
        	sb.append(FormatUtils.toHexString(b));
        }
        return sb.toString();
    }
	
}