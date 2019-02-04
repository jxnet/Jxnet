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
import com.ardikars.common.util.Validate;
import com.ardikars.jxnet.exception.NativeException;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The Berkeley Packet Filter (BPF) allows a user-space program to attach a filter onto any socket and
 * allow or disallow certain types of data to come through the socket to
 * avoid useless packet copies from kernel to userspace.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Mutable(blocking = true)
public final class BpfProgram implements PointerHandler {

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

	private long address;

	/**
	 * Bpf compile mode.
	 */
	public enum BpfCompileMode {

		OPTIMIZE(1), NON_OPTIMIZE(0);

		private final int value;

		BpfCompileMode(final int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}

	}
	
	private native void initBpfProgram();

	/**
	 * Create instance of BpfProgram and initialize it.
	 */
	public BpfProgram() {
		this.initBpfProgram();
	}

	/**
	 * Create instance of BpfProgram and initialize it.
	 * @param builder filter builder.
	 * @return returns BpfProgram (filter handle).
	 */
	public static BpfProgram bpf(Builder builder) {
		return builder.buildBpf();
	}

	/**
	 * Bpf program builder.
	 * @return returns Bilder instance.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Get Bpf Program pointer address.
	 * Please use {@link BpfProgram#address()}
	 * @return returns pointer address.
	 */
	@Override
	public long getAddress() {
		return address();
	}

	/**
	 * Get Bpf Program pointer address.
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
	public BpfProgram clone() throws CloneNotSupportedException {
		return (BpfProgram) super.clone();
	}

	/**
	 * Check bpf handle.
	 * @return returns true if closed, false otherwise.
	 */
	public boolean isClosed() {
		return this.getAddress() == 0;
	}

	@Override
	public void close() throws IOException {
		if (!isClosed()) {
			Jxnet.PcapFreeCode(this);
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final BpfProgram that = (BpfProgram) o;

		return this.getAddress() == that.getAddress();
	}

	@Override
	public int hashCode() {
		return (int) (this.getAddress() ^ (this.getAddress() >>> 32));
	}

	@Override
	public String toString() {
		return new StringBuilder("BpfProgram{")
				.append("address=").append(this.getAddress())
				.append('}')
				.toString();
	}

	/**
	 * Builder class for BpfProgram.
	 */
	public static final class Builder implements com.ardikars.common.util.Builder<BpfProgram, Void> {

		private Pcap pcap;
		private String filter;
		private int netmask = 0xffffff00;
		private BpfCompileMode bpfCompileMode = BpfCompileMode.OPTIMIZE;

		/**
		 * Pcap handle.
		 * @param pcap pcap handle.
		 * @return returns BpfProgram Builder.
		 */
		public Builder pcap(final Pcap pcap) {
			this.pcap = pcap;
			return this;
		}

		/**
		 * Filter pattern.
		 * @param filter filter pattern.
		 * @return returns BpfProgram Builder.
		 */
		public Builder filter(final String filter) {
			this.filter = filter;
			return this;
		}

		/**
		 * Inet4Address netmask.
		 * @param netmask netmask.
		 * @return returns BpfProgram Builder.
		 */
		public Builder netmask(final int netmask) {
			this.netmask = netmask;
			return this;
		}

		/**
		 * Bpf compile mode.
		 * @param bpfCompileMode bpf compile mode.
		 * @return returns BpfProgram Builder.
		 */
		public Builder bpfCompileMode(final BpfCompileMode bpfCompileMode) {
			this.bpfCompileMode = bpfCompileMode;
			return this;
		}

		/**
		 * Create instance of BpfProgram.
		 * @return returns BpfProgram.
		 */
		private BpfProgram buildBpf() {
			Validate.notIllegalArgument(pcap != null,
					new IllegalArgumentException("Pcap handle should be not null."));
			Validate.notIllegalArgument(!pcap.isClosed(),
					new IllegalArgumentException("Pcap handle is closed."));
			Validate.notIllegalArgument(filter != null && !filter.equals(""),
					new IllegalArgumentException("Filter expression should be not null or empty."));

			BpfProgram bpfProgram = new BpfProgram();
			if (Jxnet.PcapCompile(pcap, bpfProgram, filter, bpfCompileMode.getValue(), netmask) < Jxnet.OK) {
				throw new NativeException(Jxnet.PcapGetErr(pcap));
			}
			if (Jxnet.PcapSetFilter(pcap, bpfProgram) < Jxnet.OK) {
				throw new NativeException(Jxnet.PcapGetErr(pcap));
			}
			return bpfProgram;
		}

		@Override
		public BpfProgram build() {
			return buildBpf();
		}

		@Override
		public BpfProgram build(Void value) {
			throw new UnsupportedOperationException();
		}

	}

	static {
		try {
			Class.forName("com.ardikars.jxnet.Jxnet");
		} catch (ClassNotFoundException e) {
			//
		}
	}

}
