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

import com.ardikars.common.annotation.Mutable;
import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.util.Platforms;
import com.ardikars.common.util.Validate;
import com.ardikars.jxnet.exception.NativeException;
import com.ardikars.jxnet.exception.PlatformNotSupportedException;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class storing pointer address of pcap handle and used for dereferencing the pointer.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Mutable(blocking = true)
public final class Pcap implements PointerHandler {

	/**
	 * Default unknown netmask for PcapLookupNet function in {@link Jxnet}.
	 */
	public static final Inet4Address PCAP_NETMASK_UNKNOWN = Inet4Address.valueOf(0xffffffff);

	/**
	 * Maximum snapshot length.
	 */
	public static final int MAXIMUM_SNAPLEN = 262144;

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

	/**
	 * Indentify if pcap handle is dead and used for prevent user to make a SIGSEGV.
	 */
	private boolean isDead;

	private long address;

	private Pcap() {

	}

	/**
	 * Create pcap instance and initialize it.
	 * @param builder builder.
	 * @return returns pcap handle.
	 */
	public static Pcap live(Builder builder) {
		return builder.buildLive();
	}

	public static Pcap dead(Builder builder) {
		return builder.buildDead();
	}

	public static Pcap offline(Builder builder) {
		return builder.buildOffline();
	}

	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Getting pointer address.
	 * Please use {@link Pcap#address()}.
	 * @return returns pointer address.
	 */
	@Deprecated
	@Override
	public long getAddress() {
		return address();
	}

	/**
	 * Getting pointer address.
	 * @return returns pointer address.
	 */
	@Override
	public long address() {
		if (this.lock.readLock().tryLock()) {
			try {
				return this.address;
			} finally {
				this.lock.readLock().unlock();
			}
		}
		return 0;
	}

	@Override
	public Pcap clone() throws CloneNotSupportedException {
		return (Pcap) super.clone();
	}

	/**
	 * Check pcap handle.
	 * @return returns true if closed, false otherwise.
	 */
	public boolean isClosed() {
		return this.getAddress() == 0;
	}

	/**
	 * Identify handle is dead.
	 * @return returns true if pcap handle opened by PcapOpenDead*.
	 */
	public boolean isDead() {
		return this.isDead;
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
	public String toString() {
		return new StringBuilder("Pcap{")
				.append("address=").append(this.getAddress())
				.append('}')
				.toString();
	}

	@Override
	public void close() throws IOException {
		if (!isClosed()) {
			Jxnet.PcapClose(this);
		}
	}

	/**
	 */
	public enum PcapType {
		LIVE, DEAD, OFFLINE
	}

	public static final class Builder implements com.ardikars.common.util.Builder<Pcap, Void> {

		private String source;
		private int snaplen = 65535;
		private PromiscuousMode promiscuousMode = PromiscuousMode.PROMISCUOUS;
		private ImmediateMode immediateMode = ImmediateMode.IMMEDIATE;
		private PcapDirection direction = PcapDirection.PCAP_D_INOUT;
		private PcapTimestampType timestampType = PcapTimestampType.HOST;
		private PcapTimestampPrecision timestampPrecision = PcapTimestampPrecision.MICRO;
		private int timeout = 2000;
		private boolean enableRfMon;
		private boolean enableNonBlock;
		private PcapType pcapType;

		private StringBuilder errbuf;

		private DataLinkType dataLinkType;

		private String fileName;

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

		public Builder timestampType(final PcapTimestampType timeStampType) {
			this.timestampType = timeStampType;
			return this;
		}

		public Builder timestampPrecision(final PcapTimestampPrecision timeStampPrecision) {
			this.timestampPrecision = timeStampPrecision;
			return this;
		}

		public Builder timeout(final int timeout) {
			this.timeout = timeout;
			return this;
		}

		public Builder rfmon(final RadioFrequencyMonitorMode radioFrequencyMonitorMode) {
			this.enableRfMon = radioFrequencyMonitorMode.getValue() == 1 ? true : false;
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

		public Builder dataLinkType(final DataLinkType dataLinkType) {
			this.dataLinkType = dataLinkType;
			return this;
		}

		public Builder fileName(final String fileName) {
			this.fileName = fileName;
			return this;
		}

		public Builder pcapType(PcapType pcapType) {
			this.pcapType = pcapType;
			return this;
		}

		/**
		 * Build a live pcap handle.
		 * @return pcap handle.
		 */
		private Pcap buildLive() {
			Validate.notIllegalArgument(source != null,
					new IllegalArgumentException("Device name should be not null."));
			Validate.notIllegalArgument(snaplen > 0 && snaplen < 65536,
					new IllegalArgumentException("Snaplen should be greater then 0 and less then 65536."));
			Validate.notIllegalArgument(timeout > 0,
					new IllegalArgumentException("Timeout should be greater then 0."));
			Validate.notIllegalArgument(errbuf != null,
					new IllegalArgumentException("Error buffer should be not null."));

			Pcap pcap = Jxnet.PcapCreate(source, errbuf);
			if (Jxnet.PcapSetSnaplen(pcap, snaplen) < Jxnet.OK) {
				throw new NativeException(Jxnet.PcapGetErr(pcap));
			}
			if (Jxnet.PcapSetPromisc(pcap, promiscuousMode.getValue()) < Jxnet.OK) {
				throw new NativeException(Jxnet.PcapGetErr(pcap));
			}
			if (Jxnet.PcapSetTimeout(pcap, timeout) < Jxnet.OK) {
				throw new NativeException(Jxnet.PcapGetErr(pcap));
			}
			setImmediateModeAndTimeStamp(pcap);
			setEnableRfMon(pcap);
			if (Jxnet.PcapActivate(pcap) < Jxnet.OK) {
				throw new NativeException(Jxnet.PcapGetErr(pcap));
			}
			if (!Platforms.isWindows() && Jxnet.PcapSetDirection(pcap, direction) < Jxnet.OK) {
				throw new PlatformNotSupportedException();
			}
			setEnableNonBlock(pcap);
			return pcap;
		}

		/**
		 * Build non live pcap handle.
		 * @return pcap handle.
		 */
		private Pcap buildDead() {
			Validate.notIllegalArgument(dataLinkType != null,
					new IllegalArgumentException("Datalink type should be not null."));
			Pcap pcap;
			if (Platforms.isWindows()) {
				pcap = Jxnet.PcapOpenDead(dataLinkType.getValue(), snaplen);
			} else {
				pcap = Jxnet.PcapOpenDeadWithTStampPrecision(dataLinkType.getValue(), snaplen, timestampPrecision.getValue());
			}
			if (pcap == null) {
				throw new NativeException();
			}
			return pcap;
		}

		/**
		 * Build a pcap handle for reading pcap file.
		 * @return pcap handle.
		 */
		private Pcap buildOffline() {
			Validate.notIllegalArgument(fileName != null && !fileName.equals(""),
					new IllegalArgumentException("File name should be not null or empty."));
			Validate.notIllegalArgument(errbuf != null,
					new IllegalArgumentException("Error buffer should be not null."));
			Pcap pcap;
			if (Platforms.isWindows()) {
				pcap = Jxnet.PcapOpenOffline(fileName, errbuf);
			} else {
				pcap = Jxnet.PcapOpenOfflineWithTStampPrecision(fileName, timestampPrecision.getValue(), errbuf);
			}
			if (pcap == null) {
				throw new NativeException(errbuf.toString());
			}
			return pcap;
		}

		@Override
		public Pcap build() {
			Validate.notIllegalArgument(pcapType != null,
					new IllegalArgumentException("Pcap type should be not null."));
			switch (pcapType) {
				case OFFLINE:
					return buildOffline();
				case LIVE:
					return buildLive();
				default:
					return buildDead();
			}
		}

		@Override
		public Pcap build(Void value) {
			throw new UnsupportedOperationException();
		}

		private void setImmediateModeAndTimeStamp(Pcap pcap) throws NativeException {
			if (!Platforms.isWindows()) {
				if (Jxnet.PcapSetImmediateMode(pcap, immediateMode.getValue()) < Jxnet.OK) {
					throw new NativeException(Jxnet.PcapGetErr(pcap));
				}
				if (Jxnet.PcapSetTStampType(pcap, timestampType.getValue()) < Jxnet.OK) {
					throw new NativeException(Jxnet.PcapGetErr(pcap));
				}
				if (Jxnet.PcapSetTStampPrecision(pcap, timestampPrecision.getValue()) < Jxnet.OK) {
					throw new NativeException(Jxnet.PcapGetErr(pcap));
				}
			}
		}

		private void setEnableRfMon(Pcap pcap) throws NativeException {
			if (enableRfMon) {
				if (Jxnet.PcapCanSetRfMon(pcap) == 1) {
					int mode = RadioFrequencyMonitorMode.RFMON.getValue();
					if (Jxnet.PcapSetRfMon(pcap, mode) < Jxnet.OK) {
						throw new NativeException(Jxnet.PcapGetErr(pcap));
					}
				}
			} else {
				if (Jxnet.PcapSetRfMon(pcap, RadioFrequencyMonitorMode.NON_RFMON.getValue()) < Jxnet.OK) {
					throw new NativeException(Jxnet.PcapGetErr(pcap));
				}
			}
		}

		/**
		 * Only set the {@link Pcap} handle into non-blocking mode if we have a timeout greater than zero
		 * and enbaleNonBlock property is true.
		 * @param pcap pcap handle.
		 * @throws NativeException native exception.
		 */
		private void setEnableNonBlock(Pcap pcap) throws NativeException {
			if (enableNonBlock && timeout > 0) {
				if (Jxnet.PcapSetNonBlock(pcap, 1, errbuf) < Jxnet.OK) {
					throw new NativeException(Jxnet.PcapGetErr(pcap));
				}
			} else {
				if (Jxnet.PcapSetNonBlock(pcap, 0, errbuf) < Jxnet.OK) {
					throw new NativeException(Jxnet.PcapGetErr(pcap));
				}
			}
		}

		@Override
		public String toString() {
			return new StringBuilder("Builder{")
					.append("source='").append(source).append('\'')
					.append(", snaplen=").append(snaplen)
					.append(", promiscuousMode=").append(promiscuousMode)
					.append(", immediateMode=").append(immediateMode)
					.append(", direction=").append(direction)
					.append(", timestampType=").append(timestampType)
					.append(", timestampPrecision=").append(timestampPrecision)
					.append(", timeout=").append(timeout)
					.append(", enableRfMon=").append(enableRfMon)
					.append(", enableNonBlock=").append(enableNonBlock)
					.append(", pcapType=").append(pcapType)
					.append(", errbuf=").append(errbuf)
					.append(", dataLinkType=").append(dataLinkType)
					.append(", fileName='").append(fileName).append('\'')
					.append('}').toString();
		}

	}

}
