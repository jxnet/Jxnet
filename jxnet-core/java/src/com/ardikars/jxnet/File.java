
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

import com.ardikars.jxnet.util.Pointer;

public final class File extends java.io.File{

	private static final long serialVersionUID = -7925210130734414457L;

	private Pointer pointer;

	public Pointer getPointer() {
		return pointer;
	}
	
	private File(String pathname) {
		super(pathname);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
}
