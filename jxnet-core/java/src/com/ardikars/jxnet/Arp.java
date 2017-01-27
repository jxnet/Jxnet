
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

import com.ardikars.jxnet.util.Pointer;

public class Arp {

    private Pointer pointer;

    public Pointer getPointer() {
        return pointer;
    }

    public boolean isClosed() {
        if (pointer.getAddress() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return pointer.toString();
    }

}
