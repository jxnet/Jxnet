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

import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.util.Validate;
import com.ardikars.jxnet.exception.NativeException;

/**
 * The Berkeley Packet Filter (BPF) allows a user-space program to attach a filter onto any socket and
 * allow or disallow certain types of data to come through the socket to
 * avoid useless packet copies from kernel to userspace.
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class BpfProgram implements PointerHandler {

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

	private long address;

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
	 * @return returns pointer address.
	 */
	@Override
	public long getAddress() {
		synchronized (this) {
			return this.address;
		}
	}

	/**
	 * Check bpf handle.
	 * @return returns true if closed, false otherwise.
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

		final BpfProgram that = (BpfProgram) o;

		return this.getAddress() == that.getAddress();
	}

	@Override
	public int hashCode() {
		return (int) (this.getAddress() ^ (this.getAddress() >>> 32));
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		BpfProgram bpfProgram = (BpfProgram) super.clone();
		return bpfProgram;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BpfProgram{");
		sb.append("address=").append(this.getAddress());
		sb.append('}');
		return sb.toString();
	}

	/**
	 * Builder class for BpfProgram.
	 */
	public static final class Builder {

		private Pcap pcap;
		private String filter;
		private Inet4Address netmask = Pcap.PCAP_NETMASK_UNKNOWN;
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
		public Builder netmask(final Inet4Address netmask) {
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
		public BpfProgram buildBpf() {
			Validate.nullPointer(pcap);
			Validate.notIllegalArgument(!pcap.isClosed(), new IllegalArgumentException("Pcap handle is closed."));
			Validate.notIllegalArgument(filter != null && !filter.equals(""));

			BpfProgram bpfProgram = new BpfProgram();
			if (Jxnet.PcapCompile(pcap, bpfProgram, filter, bpfCompileMode.getValue(), netmask.toInt()) != Jxnet.OK) {
				throw new NativeException();
			}
			if (Jxnet.PcapSetFilter(pcap, bpfProgram) != Jxnet.OK) {
				throw new NativeException();
			}
			return bpfProgram;
		}

	}

	static {
		try {
			Class.forName("com.ardikars.jxnet.Jxnet");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
