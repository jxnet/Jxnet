
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

import java.nio.ByteBuffer;

public interface PcapHandler<T> {

	void nextPacket(T user, PcapPktHdr h, ByteBuffer bytes);

}