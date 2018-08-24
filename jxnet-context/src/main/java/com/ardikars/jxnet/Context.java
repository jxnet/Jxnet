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
import com.ardikars.jxnet.exception.BpfProgramCloseException;
import com.ardikars.jxnet.exception.PcapCloseException;
import com.ardikars.jxnet.exception.PcapDumperCloseException;
import com.ardikars.jxnet.exception.PlatformNotSupportedException;

import java.nio.ByteBuffer;
import java.util.List;

public interface Context {

	String getApplicationName();

	String getApplicationVersion();

	Object getAdditionalInformation();

	/**
	 * Native function mapping
	 */

	/**
	 * Collect a group of packets.
	 * Callback argument already asyncronous.
	 * @param cnt maximum iteration, -1 is infinite iteration.
	 * @param callback callback funtion.
	 * @param user args
	 * @param <T> args type.
	 * @return PcapLoop() returns 0 if cnt is exhausted or if, when reading from a
	 * @throws PcapCloseException pcap close exception.
	 * savefile, no more packets are available. It returns -1 if an error
	 * occurs or -2 if the loop terminated due to a call to PcapBreakLoop()
	 * before any packets were processed.  It does not return when live packet
	 * buffer timeouts occur; instead, it attempts to read more packets.
	 * @since 1.1.4
	 */
	<T> PcapCode pcapLoop(int cnt, PcapHandler<T> callback, T user) throws PcapCloseException;

	/**
	 * Collect a group of packets.
	 * @param cnt maximum iteration, -1 to infinite.
	 * @param callback callback function.
	 * @param user arg.
	 * @param <T> args type.
	 * @return PcapDispatch() returns the number of packets processed on success;
	 * @throws PcapCloseException pcap close exception.
	 * this can be 0 if no packets were read from a live capture (if, for
	 * example, they were discarded because they didn't pass the packet filter,
	 * or if, on platforms that support a packet buffer timeout that
	 * starts before any packets arrive, the timeout expires before any packets
	 * arrive, or if the file descriptor for the capture device is in non-blocking
	 * mode and no packets were available to be read) or if no more
	 * packets are available in a savefile. It returns -1 if an error
	 * occurs or -2 if the loop terminated due to a call to PcapBreakLoop()
	 * before any packets were processed. If your application uses
	 * PcapBreakLoop(), make sure that you explicitly check for -1 and -2,
	 * rather than just checking for a return value less then 0.
	 * @since 1.1.4
	 */
	<T> PcapCode pcapDispatch(int cnt, PcapHandler<T> callback, T user) throws PcapCloseException;

	/**
	 * Open a file to write packets.
	 * @param fname fname specifies the name of the file to open. The file will have the same format
	 *                 as those used by tcpdump(1) and tcpslice(1). The name "-" is a synonym for stdout.
	 * @return pcap code.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	PcapCode pcapDumpOpen(String fname) throws PcapCloseException;

	/**
	 * Save a packet to disk.
	 * @param h pcap packet header.
	 * @param sp packet buffer.
	 * @throws PcapDumperCloseException pcap dumper close exception.
	 * @since 1.1.4
	 */
	void pcapDump(PcapPktHdr h, ByteBuffer sp) throws PcapDumperCloseException;

	/**
	 * Compile a packet filter, converting an high level filtering expression
	 * (see Filtering expression syntax) in a program that can be interpreted
	 * by the kernel-level filtering engine.
	 * @param str filter expression.
	 * @param optimize optimize (0/1).
	 * @param netmask netmask.
	 * @return -1 on error, 0 otherwise.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	PcapCode pcapCompile(String str, int optimize, int netmask) throws PcapCloseException;

	/**
	 * Associate a filter to a capture.
	 * @return -1 on error, 0 otherwise.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	PcapCode pcapSetFilter() throws PcapCloseException;

	/**
	 * Send a raw packet.
	 * @param buf packet buffer.
	 * @param size size of packet buffer.
	 * @return -1 on error, 0 otherwise.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	PcapCode pcapSendPacket(ByteBuffer buf, int size) throws PcapCloseException;

	/**
	 * Return the next available packet.
	 * @param h packet header.
	 * @return PcapNext() returns next available packet.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	ByteBuffer pcapNext(PcapPktHdr h) throws PcapCloseException;

	/**
	 * Read a packet from an interface or from an offline capture.
	 * @param pktHeader packet header.
	 * @param pktData packet buffer.
	 * @return PcapNextEx() returns 1 if the packet was read without problems, 0 if
	 * packets are being read from a live capture and the packet buffer time-
	 * out expired, -1 if an error occurred while reading the packet, and -2
	 * if packets are being read from a savefile and there are no more
	 * packets to read from the savefile.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	PcapCode pcapNextEx(PcapPktHdr pktHeader, ByteBuffer pktData) throws PcapCloseException;

	/**
	 * Close the files associated with pcap and deallocates resources.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	void pcapClose() throws PcapCloseException;

	/**
	 * Flushes the output buffer to the savefile, so that any packets written
	 * with PcapDump() but not yet written to the savefile will be written. -1
	 * is returned on error, 0 on success.
	 * @return -1 on error, 0 otherwise.
	 * @throws PcapDumperCloseException pcap dumper close exception.
	 * @since 1.1.4
	 */
	PcapCode pcapDumpFlush() throws PcapDumperCloseException;

	/**
	 * Closes a savefile.
	 * @param pcapDumper pcap dumper object.
	 * @throws PcapDumperCloseException pcap dumper close exception.
	 * @since 1.1.4
	 */
	void pcapDumpClose(PcapDumper pcapDumper) throws PcapDumperCloseException;

	/**
	 * Return the link layer of an adapter on success.
	 * @return link layer type of an adapter on success and
	 * PCAP_ERROR_NOT_ACTIVATED(-3) if called on a capture handle that has been
	 * created but not activated.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	DataLinkType pcapDataLink() throws PcapCloseException;

	/**
	 * Set the current data link type of the pcap descriptor to the type
	 * specified by dlt. -1 is returned on failure.
	 * @param dataLinkType data link type.
	 * @return -1 on error, 0 otherwise.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	PcapCode pcapSetDataLink(DataLinkType dataLinkType) throws PcapCloseException;

	/**
	 * Set a flag that will force PcapDispatch() or PcapLoop() to return rather than looping.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	void pcapBreakLoop() throws PcapCloseException;

	/**
	 * Return the error text pertaining to the last pcap library error.
	 * @return error text pertaining to the last pcap library error.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	String pcapGetErr() throws PcapCloseException;

	/**
	 * Returns true (1) if the current savefile uses a different byte order than the current system.
	 * @return PcapIsSwapped() returns true (1) or false (0) on success and
	 * PCAP_ERROR_NOT_ACTIVATED(-3) if called on a capture handle that has been
	 * created but not activated.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	PcapCode pcapIsSwapped() throws PcapCloseException;

	/**
	 * Return the dimension of the packet portion (in bytes) that is delivered to the application.
	 * @return PcapSnapshot() returns the snapshot length on success and
	 * PCAP_ERROR_NOT_ACTIVATED(-3) if called on a capture handle that has been created but not activated.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	int pcapSnapshot() throws PcapCloseException;

	/**
	 * Return the major version number of the pcap library used to write the savefile.
	 * @return major version number of the pcap library used to write the savefile.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	int pcapMajorVersion() throws PcapCloseException;

	/**
	 * Return the minor version number of the pcap library used to write the savefile.
	 * @return minor version number of the pcap library used to write the savefile.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	int pcapMinorVersion() throws PcapCloseException;

	/**
	 * Switch between blocking and nonblocking mode.
	 * @param nonblock 1 to set non block.
	 * @param errbuf error buffer.
	 * @return returns the current non-blocking state of the capture descriptor;
	 * it always returns 0 on savefiles. If there is an error, -1 is returned and
	 * errbuf is filled in with an appropriate error message.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	PcapCode pcapSetNonBlock(boolean nonblock, StringBuilder errbuf) throws PcapCloseException;

	/**
	 * Get the "non-blocking" state of an interface.
	 * @param errbuf error buffer.
	 * @return PcapGetNonBlock() returns the current non-blocking state of the
	 * capture  descriptor; it always returns 0 on savefiles.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	PcapCode pcapGetNonBlock(StringBuilder errbuf) throws PcapCloseException;

	/**
	 * Return the file position for a savefile.
	 * @return file position for a savefile.
	 * @throws PcapDumperCloseException pcap dumper close exception.
	 * @since 1.1.4
	 */
	long pcapDumpFTell() throws PcapDumperCloseException;

	/**
	 * Free a filter.
	 * @throws BpfProgramCloseException bpf program close exception.
	 * @since 1.1.4
	 */
	void pcapFreeCode() throws BpfProgramCloseException;

	/**
	 * Return statistics on current capture.
	 * @param pcapStat pcap stat object.
	 * @return PcapStats() returns 0 on success and returns -1 if there is an error
	 * or if pcap handle doesn't support packet statistics.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	PcapCode pcapStats(PcapStat pcapStat) throws PcapCloseException;

	/**
	 * Compile a packet filter without the need of opening an adapter.
	 * This function converts an high level filtering expression (see Filtering expression syntax)
	 * in a program that can be interpreted by the kernel-level filtering engine.
	 * @param snaplen snapshot length.
	 * @param dataLinkType link type.
	 * @param filter str.
	 * @param optimize optiomize.
	 * @param mask netmask.
	 * @return -1 on error.
	 * @throws BpfProgramCloseException bpf program close exception.
	 * @since 1.1.4
	 */
	PcapCode pcapCompileNoPcap(int snaplen, DataLinkType dataLinkType, String filter, boolean optimize, Inet4Address mask)
			throws BpfProgramCloseException;

	/**
	 * Print the text of the last pcap library error on stderr, prefixed by prefix.
	 * @param prefix prefix.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	void pcapPError(String prefix) throws PcapCloseException;

	/**
	 * Checks whether monitor mode could be set on a capture handle when the handle is activated.
	 * @return PcapCanSetRfMon() returns 0 if monitor mode could not be set, 1 if
	 * monitor mode could be set, and a negative value on error. A negative
	 * return value indicates what error condition occurred. The possible
	 * error values are:
	 * PCAP_ERROR_NO_SUCH_DEVICE(-5): The capture source specified when the handle was created doesn' exist.
	 * PCAP_ERROR_PERM_DENIED(-8): The  process  doesn't  have  permission to check whether monitor mode could be supported.
	 * PCAP_ERROR_ACTIVATED(-3): The capture handle has already been activated.
	 * PCAP_ERROR(-1): Generic error.
	 * @throws PcapCloseException pcap close exception.
	 * @since 1.1.4
	 */
	PcapCode pcapCanSetRfMon() throws PcapCloseException;

	/**
	 * used to specify a direction that packets will be
	 * captured. Direction is one of the constants PCAP_D_IN, PCAP_D_OUT or
	 * PCAP_D_INOUT. PCAP_D_IN will only capture packets received by the
	 * device, PCAP_D_OUT will only capture packets sent by the device and
	 * PCAP_D_INOUT will capture packets received by or sent by the device.
	 * PCAP_D_INOUT is the default setting if this function is not called.
	 *
	 * <p>PcapSetdirection() isn't necessarily fully supported on all platforms;
	 * some platforms might return an error for all  values,  and  some  other
	 * platforms might not support PCAP_D_OUT.</p>
	 *
	 * <p>This operation is not supported if a savefile is being read.</p>
	 * @param direction direction.
	 * @return returns  0 on success and -1 on failure (not supported by operating system).
	 * @throws PcapCloseException pcap close exception.
	 * @throws PlatformNotSupportedException platform not supported exception.
	 * @since 1.1.4
	 */
	PcapCode pcapSetDirection(PcapDirection direction) throws PcapCloseException, PlatformNotSupportedException;

	/**
	 * Get the time stamp precision returned in captures.
	 * @return the precision of the time stamp returned in packet captures on the pcap descriptor.
	 * @throws PcapCloseException pcap close exception.
	 * @throws PlatformNotSupportedException platform not supported exception.
	 */
	int pcapGetTStampPrecision() throws PcapCloseException, PlatformNotSupportedException;

	/**
	 * Get list of datalinks.
	 * @param dtlBuffer datalinks.
	 * @return list of datalinks.
	 * @throws PcapCloseException pcap close exception.
	 * @throws PlatformNotSupportedException platform not supported exception.
	 */
	PcapCode pcapListDataLinks(List<Integer> dtlBuffer) throws PcapCloseException, PlatformNotSupportedException;

	/**
	 * Get link of time stamp types.
	 * @param tstampTypesp time stamp types.
	 * @return time stamp types.
	 * @throws PcapCloseException pcap close exception.
	 * @throws PlatformNotSupportedException platform not supported exception.
	 */
	PcapCode pcapListTStampTypes(List<Integer> tstampTypesp) throws PcapCloseException, PlatformNotSupportedException;

	/**
	 * Given a BPF program, a PcapPktHdr structure for a packet, and the raw
	 * data for the packet, check whether the packet passes the filter.
	 * Returns the return value of the filter program, which will be zero if
	 * the packet doesn't pass and non-zero if the packet does pass.
	 * @param fp bpfProgram
	 * @param h pktHdr
	 * @param pkt buffer.
	 * @return 0 on success.
	 */
	PcapCode pcapOfflineFilter(BpfProgram fp, PcapPktHdr h, ByteBuffer pkt);

	/**
	 * Sends a raw packet through the network interface; buf points to the data of the packet,
	 * including the link-layer header, and size is the number of bytes in the packet.
	 * @param buf packet buffer.
	 * @param size packet size.
	 * @return PcapInject returns the number of bytes written on success and -1 on failure.
	 * @throws PcapCloseException pcap close exception.
	 * @throws PlatformNotSupportedException platform not supported exception.
	 */
	PcapCode pcapInject(ByteBuffer buf, int size) throws PcapCloseException, PlatformNotSupportedException;

}
