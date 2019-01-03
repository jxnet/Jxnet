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

import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.util.Builder;
import com.ardikars.jxnet.exception.BpfProgramCloseException;
import com.ardikars.jxnet.exception.PcapCloseException;
import com.ardikars.jxnet.exception.PcapDumperCloseException;
import com.ardikars.jxnet.exception.PlatformNotSupportedException;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * Application context for wrap a pcap handle.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.1.5
 * @deprecated please use {@link com.ardikars.jxnet.context.ApplicationContext}.
 */
@Deprecated
public final class ApplicationContext implements Context {

	private final com.ardikars.jxnet.context.Context ctx;

	protected ApplicationContext(com.ardikars.jxnet.context.Context ctx) {
		this.ctx = ctx;
	}

    @Override
    public String getApplicationName() {
        return ctx.getApplicationName();
    }

	@Override
	public String getApplicationDisplayName() {
		return ctx.getApplicationDisplayName();
	}

	@Override
    public String getApplicationVersion() {
        return ctx.getApplicationVersion();
    }

	@Override
	public Context newInstance(Builder<Pcap, Void> builder) {
		com.ardikars.jxnet.context.Context context =  ctx.newInstance(builder);
		return new ApplicationContext(context);
	}

	@Override
	public <T> PcapCode pcapLoop(int cnt, PcapHandler<T> callback, T user) throws PcapCloseException {
		return ctx.pcapLoop(cnt, callback, user);
	}

	@Override
	public <T> PcapCode pcapLoop(final int cnt, final PcapHandler<T> callback, final T user, final Executor executor)
			throws PcapCloseException {
		return ctx.pcapLoop(cnt, callback, user, executor);
	}

	@Override
	public <T> PcapCode pcapLoop(final int cnt, final PcapHandler<T> callback, final T user, final ExecutorService executor)
			throws PcapCloseException {
		return ctx.pcapLoop(cnt, callback, user, executor);
	}

	@Override
	public <T> PcapCode pcapDispatch(int cnt, PcapHandler<T> callback, T user) throws PcapCloseException {
		return ctx.pcapDispatch(cnt, callback, user);
	}

	@Override
	public <T> PcapCode pcapDispatch(final int cnt, final PcapHandler<T> callback, final T user, final Executor executor)
			throws PcapCloseException {
		return ctx.pcapDispatch(cnt, callback, user, executor);
	}

	@Override
	public PcapCode pcapDumpOpen(String fname) throws PcapCloseException {
		return ctx.pcapDumpOpen(fname);
	}

	@Override
	public void pcapDump(PcapPktHdr h, ByteBuffer sp) throws PcapDumperCloseException {
		ctx.pcapDump(h, sp);
	}

	@Override
	public PcapCode pcapCompile(String str, BpfProgram.BpfCompileMode optimize, int netmask) throws PcapCloseException, BpfProgramCloseException {
		return ctx.pcapCompile(str, optimize, netmask);
	}

	@Override
	public PcapCode pcapSetFilter() throws PcapCloseException, BpfProgramCloseException {
		return ctx.pcapSetFilter();
	}

	@Override
	public PcapCode pcapSendPacket(ByteBuffer buf, int size) throws PcapCloseException {
		return ctx.pcapSendPacket(buf, size);
	}

	@Override
	public ByteBuffer pcapNext(PcapPktHdr h) throws PcapCloseException {
		return ctx.pcapNext(h);
	}

	@Override
	public PcapCode pcapNextEx(PcapPktHdr pktHeader, ByteBuffer pktData) throws PcapCloseException {
		return ctx.pcapNextEx(pktHeader, pktData);
	}

	@Override
	public void pcapClose() throws PcapCloseException {
		ctx.pcapClose();
	}

	@Override
	public PcapCode pcapDumpFlush() throws PcapDumperCloseException {
		return ctx.pcapDumpFlush();
	}

	@Override
	public void pcapDumpClose(PcapDumper pcapDumper) throws PcapDumperCloseException {
		ctx.pcapDumpClose(pcapDumper);
	}

	@Override
	public DataLinkType pcapDataLink() throws PcapCloseException {
		return ctx.pcapDataLink();
	}

	@Override
	public PcapCode pcapSetDataLink(DataLinkType dataLinkType) throws PcapCloseException {
		return ctx.pcapSetDataLink(dataLinkType);
	}

	@Override
	public void pcapBreakLoop() throws PcapCloseException {
		ctx.pcapBreakLoop();
	}

	@Override
	public String pcapGetErr() throws PcapCloseException {
		return ctx.pcapGetErr();
	}

	@Override
	public PcapCode pcapIsSwapped() throws PcapCloseException {
		return ctx.pcapIsSwapped();
	}

	@Override
	public int pcapSnapshot() throws PcapCloseException {
		return ctx.pcapSnapshot();
	}

	@Override
	public int pcapMajorVersion() throws PcapCloseException {
		return ctx.pcapMajorVersion();
	}

	@Override
	public int pcapMinorVersion() throws PcapCloseException {
		return ctx.pcapMinorVersion();
	}

	@Override
	public PcapCode pcapSetNonBlock(boolean nonblock, StringBuilder errbuf) throws PcapCloseException {
		return ctx.pcapSetNonBlock(nonblock, errbuf);
	}

	@Override
	public PcapCode pcapGetNonBlock(StringBuilder errbuf) throws PcapCloseException {
		return ctx.pcapGetNonBlock(errbuf);
	}

	@Override
	public long pcapDumpFTell() throws PcapDumperCloseException {
		return ctx.pcapDumpFTell();
	}

	@Override
	public void pcapFreeCode() throws BpfProgramCloseException {
		ctx.pcapFreeCode();
	}

	@Override
	public PcapCode pcapStats(PcapStat pcapStat) throws PcapCloseException {
		return ctx.pcapStats(pcapStat);
	}

	@Override
	public PcapCode pcapCompileNoPcap(int snaplen, DataLinkType dataLinkType, String filter,
									  BpfProgram.BpfCompileMode optimize, Inet4Address mask)
			throws BpfProgramCloseException {
		return ctx.pcapCompileNoPcap(snaplen, dataLinkType, filter, optimize, mask);
	}

	@Override
	public void pcapPError(String prefix) throws PcapCloseException {
		ctx.pcapPError(prefix);
	}

	@Override
	public PcapCode pcapCanSetRfMon() throws PcapCloseException {
		return ctx.pcapCanSetRfMon();
	}

	@Override
	public PcapCode pcapSetDirection(PcapDirection direction) throws PcapCloseException, PlatformNotSupportedException {
		return ctx.pcapSetDirection(direction);
	}

	@Override
	public PcapTimestampPrecision pcapGetTStampPrecision() throws PcapCloseException, PlatformNotSupportedException {
		return ctx.pcapGetTStampPrecision();
	}

	@Override
	public PcapCode pcapListDataLinks(List<DataLinkType> dtlBuffer) throws PcapCloseException, PlatformNotSupportedException {
		return ctx.pcapListDataLinks(dtlBuffer);
	}

	@Override
	public PcapCode pcapListTStampTypes(List<PcapTimestampType> tstampTypesp) throws PcapCloseException, PlatformNotSupportedException {
		return ctx.pcapListTStampTypes(tstampTypesp);
	}

	@Override
	public PcapCode pcapOfflineFilter(BpfProgram fp, PcapPktHdr h, ByteBuffer pkt) {
		return ctx.pcapOfflineFilter(fp, h, pkt);
	}

	@Override
	public PcapCode pcapInject(ByteBuffer buf, int size) throws PcapCloseException, PlatformNotSupportedException {
		return ctx.pcapInject(buf, size);
	}

	@Override
	public void close() throws Exception {
		ctx.close();
	}

}
