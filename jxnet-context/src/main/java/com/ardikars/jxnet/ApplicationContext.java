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

import com.ardikars.jxnet.exception.BpfProgramCloseException;
import com.ardikars.jxnet.exception.PcapCloseException;
import com.ardikars.jxnet.exception.PcapDumperCloseException;
import com.ardikars.jxnet.exception.PlatformNotSupportedException;
import com.ardikars.jxnet.exception.PropertyNotFoundException;
import com.ardikars.jxnet.util.Validate;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class ApplicationContext implements Context {

	private final Logger logger = Logger.getLogger(ApplicationContext.class.getName());

	private ApplicationContext() {

	}

    @Override
    public String getApplicationName() {
        return Application.getInstance().getApplicationName();
    }

    @Override
    public String getApplicationVersion() {
        return Application.getInstance().getApplicationVersion();
    }

    @Override
    public Object getProperty(final String key) throws PropertyNotFoundException {
	    Validate.nullPointer(key);
	    Object object = Application.getInstance().getProperty(key);
	    if (object == null) {
	    	throw new PropertyNotFoundException();
	    }
        return Application.getInstance().getProperty(key);
    }

	@Override
	public <T> T getProperty(String name, Class<T> requiredType) throws ClassCastException, PropertyNotFoundException {
		Validate.nullPointer(name);
		Validate.nullPointer(requiredType);
    	Object object = Application.getInstance().getProperty(name);
		if (object == null) {
			throw new PropertyNotFoundException();
		}
		if (object.getClass() != requiredType) {
			throw new ClassCastException(object.getClass().getName() + " can't cast to " + requiredType.getName() + ".");
		}
    	return (T) object;
	}

	@Override
	public void removeProperty(String key) {
    	Validate.nullPointer(key);
		Application.getInstance().getProperties().remove(key);
	}

	@Override
	public Map<String, Object> getProperties() {
    	Map<String, Object> properties = Collections.unmodifiableMap(Application.getInstance().getProperties());
		return properties;
	}

	@Override
    public void addLibrary(final Library.Loader libraryLoader) {
    	Validate.nullPointer(libraryLoader);
        Application.getInstance().addLibrary(libraryLoader);
    }

    private Pcap pcap;

	private BpfProgram bpfProgram;

	private PcapDumper pcapDumper;

	public static ApplicationContext newApplicationContext(Pcap pcap, BpfProgram bpfProgram) {
		Validate.nullPointer(pcap);
		Validate.nullPointer(bpfProgram);
		ApplicationContext applicationContext = new ApplicationContext();
		applicationContext.pcap = pcap;
		applicationContext.bpfProgram = bpfProgram;
		return applicationContext;
	}

	@Override
	public <T> PcapCode PcapLoop(int cnt, PcapHandler<T> callback, T user) throws PcapCloseException {
		int result = Jxnet.PcapLoop(pcap, cnt, callback, user);
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public <T> PcapCode PcapDispatch(int cnt, PcapHandler<T> callback, T user) throws PcapCloseException {
		int result = Jxnet.PcapDispatch(pcap, cnt, callback, user);
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public PcapCode PcapDumpOpen(String fname) throws PcapCloseException {
		pcapDumper = Jxnet.PcapDumpOpen(pcap, fname);
		if (pcapDumper == null) {
			return PcapCode.PCAP_ERROR;
		}
		return PcapCode.PCAP_OK;
	}

	@Override
	public void PcapDump(PcapPktHdr h, ByteBuffer sp) throws PcapDumperCloseException {
		if (pcapDumper == null || pcapDumper.isClosed()) {
			throw new PcapDumperCloseException();
		}
		Jxnet.PcapDump(pcapDumper, h, sp);
	}

	@Override
	public PcapCode PcapCompile(String str, int optimize, int netmask) throws PcapCloseException {
		int result = Jxnet.PcapCompile(pcap, bpfProgram, str, optimize, netmask);
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public PcapCode PcapSetFilter() throws PcapCloseException {
		int result = Jxnet.PcapSetFilter(pcap, bpfProgram);
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public PcapCode PcapSendPacket(ByteBuffer buf, int size) throws PcapCloseException {
		int result = Jxnet.PcapSendPacket(pcap, buf, size);
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public ByteBuffer PcapNext(PcapPktHdr h) throws PcapCloseException {
		return Jxnet.PcapNext(pcap, h);
	}

	@Override
	public PcapCode PcapNextEx(PcapPktHdr pktHeader, ByteBuffer pktData) throws PcapCloseException {
		int result = Jxnet.PcapNextEx(pcap, pktHeader, pktData);
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public void PcapClose() throws PcapCloseException {
		Jxnet.PcapClose(pcap);
	}

	@Override
	public PcapCode PcapDumpFlush() throws PcapDumperCloseException {
		if (pcapDumper == null || pcapDumper.isClosed()) {
			throw new PcapDumperCloseException();
		}
		int result = Jxnet.PcapDumpFlush(pcapDumper);
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public void PcapDumpClose(PcapDumper pcapDumper) throws PcapDumperCloseException {
		if (pcapDumper != null && !pcapDumper.isClosed()) {
			Jxnet.PcapDumpClose(pcapDumper);
		}
	}

	@Override
	public DataLinkType PcapDataLink() throws PcapCloseException {
		return DataLinkType.valueOf((short) Jxnet.PcapDataLink(pcap));
	}

	@Override
	public PcapCode PcapSetDataLink(DataLinkType dataLinkType) throws PcapCloseException {
		Validate.nullPointer(dataLinkType);
		int result = Jxnet.PcapSetDataLink(pcap, dataLinkType.getValue());
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public void PcapBreakLoop() throws PcapCloseException {
		Jxnet.PcapBreakLoop(pcap);
	}

	@Override
	public String PcapGetErr() throws PcapCloseException {
		return Jxnet.PcapGetErr(pcap);
	}

	@Override
	public PcapCode PcapIsSwapped() throws PcapCloseException {
		return Jxnet.PcapIsSwapped(pcap) == 1 ? PcapCode.PCAP_TRUE : PcapCode.PCAP_FALSE;
	}

	@Override
	public int PcapSnapshot() throws PcapCloseException {
		return Jxnet.PcapSnapshot(pcap);
	}

	@Override
	public int PcapMajorVersion() throws PcapCloseException {
		return Jxnet.PcapMajorVersion(pcap);
	}

	@Override
	public int PcapMinorVersion() throws PcapCloseException {
		return Jxnet.PcapMinorVersion(pcap);
	}

	@Override
	public PcapCode PcapSetNonBlock(boolean nonblock, StringBuilder errbuf) throws PcapCloseException {
		int result = Jxnet.PcapSetNonBlock(pcap, (nonblock) ? 1 : 0, errbuf);
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public PcapCode PcapGetNonBlock(StringBuilder errbuf) throws PcapCloseException {
		return Jxnet.PcapGetNonBlock(pcap, errbuf) == 1 ? PcapCode.PCAP_TRUE : PcapCode.PCAP_FALSE;
	}

	@Override
	public long PcapDumpFTell() throws PcapDumperCloseException {
		if (pcapDumper == null || pcapDumper.isClosed()) {
			throw new PcapDumperCloseException();
		}
		return Jxnet.PcapDumpFTell(pcapDumper);
	}

	@Override
	public void PcapFreeCode() throws BpfProgramCloseException {
		Jxnet.PcapFreeCode(bpfProgram);
	}

	@Override
	public PcapCode PcapStats(PcapStat pcapStat) throws PcapCloseException {
		int result = Jxnet.PcapStats(pcap, pcapStat);
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public PcapCode PcapCompileNoPcap(int snaplen, DataLinkType dataLinkType, String filter, boolean optimize, Inet4Address mask) throws BpfProgramCloseException {
		int result = Jxnet.PcapCompileNoPcap(snaplen, dataLinkType.getValue(), bpfProgram, filter, optimize ? 1 : 0, mask.toInt());
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public void PcapPError(String prefix) throws PcapCloseException {
		Jxnet.PcapPError(pcap, prefix);
	}

	@Override
	public PcapCode PcapCanSetRfMon() throws PcapCloseException {
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
	public PcapCode PcapSetDirection(PcapDirection direction) throws PcapCloseException, PlatformNotSupportedException {
		Validate.nullPointer(direction);
		int result = Jxnet.PcapSetDirection(pcap, direction);
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public int PcapGetTStampPrecision() throws PcapCloseException, PlatformNotSupportedException {
		return Jxnet.PcapGetTStampPrecision(pcap);
	}

	@Override
	public PcapCode PcapListDataLinks(List<Integer> dtlBuffer) throws PcapCloseException, PlatformNotSupportedException {
		int result = Jxnet.PcapListDataLinks(pcap, dtlBuffer);
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public PcapCode PcapListTStampTypes(List<Integer> tstampTypesp) throws PcapCloseException, PlatformNotSupportedException {
		int result = Jxnet.PcapListTStampTypes(pcap, tstampTypesp);
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public PcapCode PcapOfflineFilter(BpfProgram fp, PcapPktHdr h, ByteBuffer pkt) {
		int result = Jxnet.PcapOfflineFilter(bpfProgram, h, pkt);
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

	@Override
	public PcapCode PcapInject(ByteBuffer buf, int size) throws PcapCloseException, PlatformNotSupportedException {
		int result = Jxnet.PcapInject(pcap, buf, size);
		switch (result) {
			case 0: return PcapCode.PCAP_OK;
			default: return PcapCode.PCAP_ERROR;
		}
	}

}
