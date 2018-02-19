/**
 * Copyright (C) 2017-2018  Ardika Rommy Sanjaya <contact@ardikars.com>
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

import com.ardikars.jxnet.util.Validate;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class Pcap implements PointerHandler {

	public static final Inet4Address PCAP_NETMASK_UNKNOWN = Inet4Address.valueOf(0xffffffff);

	private DataLinkType dataLinkType;

	private int snapshotLength;

	private long address;

	private Pcap() {

	}

	/**
	 * Create pcap instance and initialize it.
	 * @param builder builder.
	 * @return pcap handle.
	 */
	public static Pcap newInstance(Builder builder) {
		return builder.build();
	}

	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Get pointer address.
	 * @return pointer address.
	 */
	@Override
	public long getAddress() {
		synchronized (this) {
			return this.address;
		}
	}

	/**
	 * Get datalink type.
	 * @return datalink type.
	 */
	public DataLinkType getDataLinkType() {
		if (!this.isClosed() && this.dataLinkType == null) {
			this.dataLinkType = DataLinkType.valueOf((short) Jxnet.PcapDataLink(this));
		}
		return this.dataLinkType;
	}

	/**
	 * Get shapshot length.
	 * @return snapshot length.
	 */
	public int getSnapshotLength() {
		if (!this.isClosed() && this.snapshotLength == 0) {
			this.snapshotLength = Jxnet.PcapSnapshot(this);
		}
		return this.snapshotLength;
	}

	/**
	 * Check pcap handle.
	 * @return true if closed.
	 */
	public boolean isClosed() {
		if (this.getAddress() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final Pcap pcap = (Pcap) o;

		return this.getAddress() == pcap.getAddress();
	}

	@Override
	public int hashCode() {
		return (int) (this.getAddress() ^ (this.getAddress() >>> 32));
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Pcap pcap = (Pcap) super.clone();
		return pcap;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Pcap{");
		sb.append("address=").append(this.getAddress());
		sb.append('}');
		return sb.toString();
	}

	public static final class Builder implements com.ardikars.jxnet.common.Builder<Pcap> {

		private String source;
		private int snaplen = 65534;
		private PromiscuousMode promiscuousMode = PromiscuousMode.PRIMISCUOUS;
		private ImmediateMode immediateMode = ImmediateMode.IMMEDIATE;
		private PcapDirection direction = PcapDirection.PCAP_D_INOUT;
		private PcapTimeStampType timeStampType = PcapTimeStampType.HOST;
		private PcapTimeStampPrecision timeStampPrecision = PcapTimeStampPrecision.MICRO;
		private int timeout = 2000;
		private boolean enableRfMon;
		private boolean enableNonBlock;
		private StringBuilder errbuf;

		public Builder source(final String source) {
			this.source = source;
			return this;
		}

		public Builder snaplen(final int snaplen) {
			this.snaplen = snaplen;
			return this;
		}

		public Builder promiscuousMode(final PromiscuousMode promiscuousMode) {
			this.promiscuousMode = promiscuousMode;
			return this;
		}

		public Builder immediateMode(final ImmediateMode immediateMode) {
			this.immediateMode = immediateMode;
			return this;
		}

		public Builder direction(final PcapDirection pcapDirection) {
			this.direction = pcapDirection;
			return this;
		}

		public Builder timeStampType(final PcapTimeStampType timeStampType) {
			this.timeStampType = timeStampType;
			return this;
		}

		public Builder timeStampPrecision(final PcapTimeStampPrecision timeStampPrecision) {
			this.timeStampPrecision = timeStampPrecision;
			return this;
		}

		public Builder timeout(final int timeout) {
			this.timeout = timeout;
			return this;
		}

		public Builder enableRfMon(final boolean enableRfMon) {
			this.enableRfMon = enableRfMon;
			return this;
		}

		public Builder enableNonBlock(final boolean enableNonBlock) {
			this.enableNonBlock = enableNonBlock;
			return this;
		}

		public Builder errbuf(final StringBuilder errbuf) {
			this.errbuf = errbuf;
			return this;
		}

		@Override
		public Pcap build() {
			Validate.nullPointer(source);
			Validate.illegalArgument(snaplen > 0 && snaplen < 65536);
			Validate.illegalArgument(timeout > 0);
			Validate.nullPointer(errbuf);

			Pcap pcap = Jxnet.PcapCreate(source, errbuf);
			Jxnet.PcapSetImmediateMode(pcap, immediateMode);
			Jxnet.PcapSetSnaplen(pcap, snaplen);
			Jxnet.PcapSetPromisc(pcap, promiscuousMode);
			Jxnet.PcapSetTimeout(pcap, timeout);
			Jxnet.PcapSetDirection(pcap, direction);
			Jxnet.PcapSetTStampType(pcap, timeStampType.getValue());
			Jxnet.PcapSetTStampPrecision(pcap, timeStampPrecision.getValue());
			Jxnet.PcapActivate(pcap);
			if (enableRfMon) {
				if (Jxnet.PcapCanSetRfMon(pcap) == Jxnet.OK) {
					Jxnet.PcapSetRfMon(pcap, RadioFrequencyMonitorMode.RFMON.getValue());
				}
			} else {
				Jxnet.PcapSetRfMon(pcap, RadioFrequencyMonitorMode.NON_RFMON.getValue());
			}
			if (enableNonBlock) {
				Jxnet.PcapSetNonBlock(pcap, 1, errbuf);
			} else {
				Jxnet.PcapSetNonBlock(pcap, 0, errbuf);
			}
			return pcap;
		}

	}

	/**
	 * Result code from pcap functions.
	 */
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
		PCAP_WARNING_TSTAMP_TYPE_NOTSUP(3);         /* the requested time stamp type is not supported */

		private final int value;

		PcapCode(final int value) {
			this.value = value;
		}

		/**
		 * Get error code.
		 * @return error code.
		 */
		public int getValue() {
			return value;
		}

	}

}
