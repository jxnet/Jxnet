/**
 * Copyright (C) 2015-2018 Jxnet
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ardikars.jxnet;

import com.ardikars.common.annotation.Immutable;

/**
 * Libpcap never guarantee, that packets will be delivered immediately,
 * the instant that they arrive, unless you enable "immediate mode"
 * by calling pcap_set_immediate_mode() between pcap_create() and pcap_activate().
 *
 * @see Jxnet PcapSetImmedateMode()
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.1.4
 */
@Immutable
public enum ImmediateMode {

    IMMEDIATE(1), NON_IMMEDIATE(0);

    private final int value;

    ImmediateMode(final int value) {
        this.value = value;
    }

    /**
     * Is immediate mode enabled?.
     * @return returns true if immediate mode, false otherwise.
     */
    public int getValue() {
        return this.value;
    }

}
