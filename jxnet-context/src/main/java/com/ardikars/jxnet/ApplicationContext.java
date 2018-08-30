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
import com.ardikars.common.util.Builder;
import com.ardikars.common.util.Validate;
import com.ardikars.jxnet.exception.BpfProgramCloseException;
import com.ardikars.jxnet.exception.PcapCloseException;
import com.ardikars.jxnet.exception.PcapDumperCloseException;
import com.ardikars.jxnet.exception.PlatformNotSupportedException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public final class ApplicationContext implements Context {

	private static final Logger LOGGER = Logger.getLogger(ApplicationContext.class.getSimpleName());

	private String applicationName;

	private String applicationVersion;

	private final Pcap pcap;

	private BpfProgram bpfProgram;

	private PcapDumper pcapDumper;

	protected ApplicationContext(Builder<Pcap, Void> builder) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (pcap != null && !pcap.isClosed()) {
					pcapBreakLoop(); // Force the loop in "pcap_read()" or "pcap_read_offline()" to terminate.
					Jxnet.PcapClose(pcap);
				}
				if (bpfProgram != null && !bpfProgram.isClosed()) {
					Jxnet.PcapFreeCode(bpfProgram);
				}
				if (pcapDumper != null && !pcapDumper.isClosed()) {
					Jxnet.PcapDumpClose(pcapDumper);
				}
				LOGGER.info("Application closed gracefully.");
			}
		});
		final Properties properties = new Properties();
		try (InputStream stream = ClassLoader.class.getResourceAsStream("application.properties")) {
			if (stream != null) {
				properties.load(stream);
			}
			this.applicationName = properties.getProperty("jxnet.application.name", "");
			this.applicationVersion = properties.getProperty("jxnet.application.version", "");
		} catch (IOException e) {
			this.applicationName = "";
			this.applicationVersion = "";
			LOGGER.warning(e.getMessage());
		}
		Validate.notIllegalArgument(builder != null, new IllegalArgumentException("Pcap builder should be not null."));
		this.pcap = builder.build();
	}

    @Override
    public String getApplicationName() {
        return applicationName;
    }

    @Override
    public String getApplicationVersion() {
        return applicationVersion;
    }

	@Override
	public Context newInstance(Builder<Pcap, Void> builder) {
		return new ApplicationContext(builder);
	}

	@Override
	public <T> PcapCode pcapLoop(int cnt, PcapHandler<T> callback, T user) throws PcapCloseException {
		int result = Jxnet.PcapLoop(pcap, cnt, callback, user);
		if (result == 0) {
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public <T> PcapCode pcapDispatch(int cnt, PcapHandler<T> callback, T user) throws PcapCloseException {
		int result = Jxnet.PcapDispatch(pcap, cnt, callback, user);
		if (result == 0) {
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public PcapCode pcapDumpOpen(String fname) throws PcapCloseException {
		pcapDumper = Jxnet.PcapDumpOpen(pcap, fname);
		if (pcapDumper == null) {
			return PcapCode.PCAP_ERROR;
		}
		return PcapCode.PCAP_OK;
	}

	@Override
	public void pcapDump(PcapPktHdr h, ByteBuffer sp) throws PcapDumperCloseException {
		if (pcapDumper == null || pcapDumper.isClosed()) {
			throw new PcapDumperCloseException();
		}
		Jxnet.PcapDump(pcapDumper, h, sp);
	}

	@Override
	public PcapCode pcapCompile(String str, BpfProgram.BpfCompileMode optimize, int netmask) throws PcapCloseException, BpfProgramCloseException {
		if (bpfProgram == null) {
			bpfProgram = new BpfProgram();
		}
		Validate.notIllegalArgument(optimize != null, new IllegalArgumentException("Bpf compile mode should be not null."));
		int result = Jxnet.PcapCompile(pcap, bpfProgram, str, optimize.getValue(), netmask);
		if (result == 0) {
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public PcapCode pcapSetFilter() throws PcapCloseException, BpfProgramCloseException {
		if (bpfProgram == null) {
			bpfProgram = new BpfProgram();
		}
		int result = Jxnet.PcapSetFilter(pcap, bpfProgram);
		if (result == 0) {
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public PcapCode pcapSendPacket(ByteBuffer buf, int size) throws PcapCloseException {
		int result = Jxnet.PcapSendPacket(pcap, buf, size);
		if (result == 0) {
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public ByteBuffer pcapNext(PcapPktHdr h) throws PcapCloseException {
		return Jxnet.PcapNext(pcap, h);
	}

	@Override
	public PcapCode pcapNextEx(PcapPktHdr pktHeader, ByteBuffer pktData) throws PcapCloseException {
		int result = Jxnet.PcapNextEx(pcap, pktHeader, pktData);
		if (result == 0) {
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public void pcapClose() throws PcapCloseException {
		if (pcap != null && !pcap.isClosed()) {
			Jxnet.PcapClose(pcap);
		}
	}

	@Override
	public PcapCode pcapDumpFlush() throws PcapDumperCloseException {
		if (pcapDumper == null || pcapDumper.isClosed()) {
			throw new PcapDumperCloseException();
		}
		int result = Jxnet.PcapDumpFlush(pcapDumper);
		if (result == 0) {
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public void pcapDumpClose(PcapDumper pcapDumper) throws PcapDumperCloseException {
		if (pcapDumper != null && !pcapDumper.isClosed()) {
			Jxnet.PcapDumpClose(pcapDumper);
		}
	}

	@Override
	public DataLinkType pcapDataLink() throws PcapCloseException {
		return DataLinkType.valueOf((short) Jxnet.PcapDataLink(pcap));
	}

	@Override
	public PcapCode pcapSetDataLink(DataLinkType dataLinkType) throws PcapCloseException {
		Validate.notIllegalArgument(dataLinkType != null);
		int result = Jxnet.PcapSetDataLink(pcap, dataLinkType.getValue());
		if (result == 0) {
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public void pcapBreakLoop() throws PcapCloseException {
		Jxnet.PcapBreakLoop(pcap);
	}

	@Override
	public String pcapGetErr() throws PcapCloseException {
		return Jxnet.PcapGetErr(pcap);
	}

	@Override
	public PcapCode pcapIsSwapped() throws PcapCloseException {
		return Jxnet.PcapIsSwapped(pcap) == 1 ? PcapCode.PCAP_TRUE : PcapCode.PCAP_FALSE;
	}

	@Override
	public int pcapSnapshot() throws PcapCloseException {
		return Jxnet.PcapSnapshot(pcap);
	}

	@Override
	public int pcapMajorVersion() throws PcapCloseException {
		return Jxnet.PcapMajorVersion(pcap);
	}

	@Override
	public int pcapMinorVersion() throws PcapCloseException {
		return Jxnet.PcapMinorVersion(pcap);
	}

	@Override
	public PcapCode pcapSetNonBlock(boolean nonblock, StringBuilder errbuf) throws PcapCloseException {
		int result = Jxnet.PcapSetNonBlock(pcap, nonblock ? 1 : 0, errbuf);
		if (result == 0) {
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public PcapCode pcapGetNonBlock(StringBuilder errbuf) throws PcapCloseException {
		return Jxnet.PcapGetNonBlock(pcap, errbuf) == 1 ? PcapCode.PCAP_TRUE : PcapCode.PCAP_FALSE;
	}

	@Override
	public long pcapDumpFTell() throws PcapDumperCloseException {
		if (pcapDumper == null || pcapDumper.isClosed()) {
			throw new PcapDumperCloseException();
		}
		return Jxnet.PcapDumpFTell(pcapDumper);
	}

	@Override
	public void pcapFreeCode() throws BpfProgramCloseException {
		if (bpfProgram != null && !bpfProgram.isClosed()) {
			Jxnet.PcapFreeCode(bpfProgram);
		}
	}

	@Override
	public PcapCode pcapStats(PcapStat pcapStat) throws PcapCloseException {
		int result = Jxnet.PcapStats(pcap, pcapStat);
		if (result == 0) {
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public PcapCode pcapCompileNoPcap(int snaplen, DataLinkType dataLinkType, String filter,
									  BpfProgram.BpfCompileMode optimize, Inet4Address mask)
			throws BpfProgramCloseException {
		if (bpfProgram == null) {
			bpfProgram = new BpfProgram();
		}
		Validate.notIllegalArgument(optimize != null, new IllegalArgumentException("Bpf compile mode should be not null."));
		int result = Jxnet.PcapCompileNoPcap(snaplen, dataLinkType.getValue(), bpfProgram, filter, optimize.getValue(), mask.toInt());
		if (result == 0) {
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public void pcapPError(String prefix) throws PcapCloseException {
		Jxnet.PcapPError(pcap, prefix);
	}

	@Override
	public PcapCode pcapCanSetRfMon() throws PcapCloseException {
		int result = Jxnet.PcapCanSetRfMon(pcap);
		switch (result) {
			case 0: return PcapCode.PCAP_FALSE;
			case 1: return PcapCode.PCAP_TRUE;
			case -4: return PcapCode.PCAP_ERROR_ACTIVATED;
			case -5: return PcapCode.PCAP_ERROR_NO_SUCH_DEVICE;
			case -8: return PcapCode.PCAP_ERROR_PERM_DENIED;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public PcapCode pcapSetDirection(PcapDirection direction) throws PcapCloseException, PlatformNotSupportedException {
		Validate.notIllegalArgument(direction != null);
		int result = Jxnet.PcapSetDirection(pcap, direction);
		if (result == 0) {
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public PcapTimestampPrecision pcapGetTStampPrecision() throws PcapCloseException, PlatformNotSupportedException {
		if (Jxnet.PcapGetTStampPrecision(pcap) == 0) {
			return PcapTimestampPrecision.MICRO;
		}
		return PcapTimestampPrecision.NANO;
	}

	@Override
	public PcapCode pcapListDataLinks(List<DataLinkType> dtlBuffer) throws PcapCloseException, PlatformNotSupportedException {
		List<Integer> buffers = new ArrayList<>();
		int result = Jxnet.PcapListDataLinks(pcap, buffers);
		if (result == 0) {
			dtlBuffer.clear(); // clear buffer.
			for (Integer datalink : buffers) {
				dtlBuffer.add(DataLinkType.valueOf(datalink.shortValue()));
			}
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public PcapCode pcapListTStampTypes(List<PcapTimestampType> tstampTypesp) throws PcapCloseException, PlatformNotSupportedException {
		List<Integer> buffers = new ArrayList<>();
		int result = Jxnet.PcapListTStampTypes(pcap, buffers);
		if (result == 0) {
			tstampTypesp.clear(); // clear buffer.
			for (Integer tstampType : buffers) {
				switch (tstampType) {
					case 0:
						tstampTypesp.add(PcapTimestampType.HOST);
						break;
					case 1:
						tstampTypesp.add(PcapTimestampType.HOST_LOWPREC);
						break;
					case 2:
						tstampTypesp.add(PcapTimestampType.HOST_HIPREC);
						break;
					case 3:
						tstampTypesp.add(PcapTimestampType.ADAPTER);
						break;
					case 4:
						tstampTypesp.add(PcapTimestampType.ADAPTER_UNSYNCED);
						break;
					default:
						// do nothing
				}
			}
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public PcapCode pcapOfflineFilter(BpfProgram fp, PcapPktHdr h, ByteBuffer pkt) {
		Validate.notIllegalArgument(fp != null && !fp.isClosed(), new IllegalArgumentException("Bpf should be not null or closed."));
		int result = Jxnet.PcapOfflineFilter(fp, h, pkt);
		if (result == 0) {
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

	@Override
	public PcapCode pcapInject(ByteBuffer buf, int size) throws PcapCloseException, PlatformNotSupportedException {
		int result = Jxnet.PcapInject(pcap, buf, size);
		if (result == 0) {
			return PcapCode.PCAP_OK;
		}
		return PcapCode.PCAP_ERROR;
	}

}
