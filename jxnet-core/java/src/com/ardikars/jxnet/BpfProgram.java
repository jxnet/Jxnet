
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

import com.ardikars.jxnet.util.Pointer;

public final class BpfProgram {

	private native void initBpfProgram();

	private Pointer pointer;

	public BpfProgram() {
		initBpfProgram();
	}

	public Pointer getPointer() {
		return pointer;
	}

	@Override
	public String toString() {
		return pointer.toString();
	}

}