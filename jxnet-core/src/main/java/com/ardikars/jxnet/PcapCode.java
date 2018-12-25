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
 * Status code from pcap functions.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Immutable
public enum PcapCode {

	PCAP_ERROR(-1),                             /* generic error code */
	PCAP_ERROR_BREAK(-2),                       /* loop terminated by pcap_breakloop */
	PCAP_ERROR_NOT_ACTIVATED(-3),               /* the capture needs to be activated */
	PCAP_ERROR_ACTIVATED(-4),                   /* the operation can't be performed on already activated captures */
	PCAP_ERROR_NO_SUCH_DEVICE(-5),              /* no such device exists */
	PCAP_ERROR_RFMON_NOTSUP(-6),                /* this device doesn't support rfmon (monitor) mode */
	PCAP_ERROR_NOT_RFMON(-7),                   /* operation supported only in monitor mode */
	PCAP_ERROR_PERM_DENIED(-8),                 /* no permission to open the device */
	PCAP_ERROR_IFACE_NOT_UP(-9),                /* interface isn't up */
	PCAP_ERROR_CANSET_TSTAMP_TYPE(-10),         /* this device doesn't support setting the time stamp type */
	PCAP_ERROR_PROMISC_PERM_DENIED(-11),        /* you don't have permission to capture in promiscuous mode */
	PCAP_ERROR_TSTAMP_PRECISION_NOTSUP(-12),    /* the requested time stamp precision is not supported */
	PCAP_OK(0),                                 /* success code */
	PCAP_WARNING(1),                            /* generic warning code */
	PCAP_WARNING_PROMISC_NOTSUP(2),             /* this device doesn't support promiscuous mode */
	PCAP_WARNING_TSTAMP_TYPE_NOTSUP(3),         /* the requested time stamp type is not supported */
	PCAP_FALSE(-100),                           /* false value */
	PCAP_TRUE(-101);                            /* true value */

	private final int value;

	private static final PcapCode[] VALUES = values();

	PcapCode(final int value) {
		this.value = value;
	}

	/**
	 * Get error code.
	 * @return returns error code.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Parse {@link PcapCode} from status code.
	 * @param statusCode status code.
	 * @return returns {@link PcapCode} object.
	 */
	public PcapCode fromStatusCode(int statusCode) {
		for (PcapCode pcapCode : VALUES) {
			if (pcapCode.value == statusCode) {
				return pcapCode;
			}
		}
		return null;
	}

}
