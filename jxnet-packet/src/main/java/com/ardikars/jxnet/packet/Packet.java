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

package com.ardikars.jxnet.packet;

import java.nio.Buffer;
import jnr.ffi.Pointer;

public interface Packet {

    /**
     * BOOLEAN PacketSetMinToCopy(LPADAPTER AdapterObject,int nbytes);
     * @param nbytes n bytes.
     * @return returns true on success, false otherwise.
     */
    boolean packetSetMinToCopy(int nbytes);

    /**
     * BOOLEAN PacketSetNumWrites(LPADAPTER AdapterObject,int nwrites);
     * @param nwrites n writes.
     * @return returns true on success, false otherwires.
     */
    boolean packetSetNumWrites(int nwrites);

    /**
     * BOOLEAN PacketSetMode(LPADAPTER AdapterObject,int mode);
     * @param mode mode.
     * @return returns true on success, false otherwise.
     */
    boolean packetSetMode(int mode);

    /**
     * BOOLEAN PacketSetReadTimeout(LPADAPTER AdapterObject,int timeout);
     * @param timeout timeout.
     * @return returns true on success, false otherwise.
     */
    boolean packetSetReadTimeout(int timeout);

    /**
     * BOOLEAN PacketSetBpf(LPADAPTER AdapterObject, struct bpf_program* fp);
     * @param AdapterObject pointer to LPADAPTER.
     * @param bpf_program bpf program.
     * @return returns true on success, false otherwise.
     */
    //boolean PacketSetBpf(Pointer AdapterObject, Structures.bpf_program fp);

    /**
     * BOOLEAN PacketSetLoopbackBehavior(LPADAPTER  AdapterObject, UINT LoopbackBehavior);
     * @param loopbackBehavior loopback behavior.
     * @return returns true on succes, false otherwise.
     */
    boolean packetSetLoopbackBehavior(long loopbackBehavior);

    /**
     * INT PacketSetSnapLen(LPADAPTER AdapterObject, int snaplen);
     * @param snaplen snapshot length.
     * @return returns snapshot length.
     */
    int packetSetSnapLen(int snaplen);

    /**
     * BOOLEAN PacketGetStats(LPADAPTER AdapterObject, struct bpf_stat* s);
     * @param s bpf status.
     * @return returns true on success, false otherwise.
     */
    boolean packetGetStats(NativeMappings.Structures.bpf_stat s);

    /**
     * BOOLEAN PacketGetStatsEx(LPADAPTER AdapterObject, struct bpf_stat* s);
     * @param s bpf status.
     * @return returns true on success, false otherwise.
     */
    boolean packetGetStatsEx(NativeMappings.Structures.bpf_stat s);

    /**
     * BOOLEAN PacketSetBuff(LPADAPTER AdapterObject, int dim);
     * @param dim dim.
     * @return returns true on success, false otherwise.
     */
    boolean packetSetBuff(int dim);

    /**
     * BOOLEAN PacketGetNetType(LPADAPTER AdapterObject, NetType* type);
     * @param type type.
     * @return returns true on success, false otherwise.
     */
    boolean packetGetNetType(NativeMappings.Structures.NetType type);

    /**
     * BOOLEAN PacketGetNetType2(PCHAR AdapterName, NetType *type);
     * @param AdapterName adapter name.
     * @param type type.
     * @return returns true on success, false otherwise.
     */
    //boolean PacketGetNetType2(String AdapterName, Structures.NetType type);

    /**
     * BOOLEAN PacketIsLoopbackAdapter(LPADAPTER AdapterObject);
     * @return returns true on success, false otherwise.
     */
    boolean packetIsLoopbackAdapter();

    /**
     * BOOLEAN PacketSendPacket(LPADAPTER AdapterObject, LPPACKET pPacket, BOOLEAN Sync);
     * @param ppacket pointer to LPPACKET.
     * @param sync sync.
     * @return returns true on success, false otherwise.
     */
    boolean packetSendPacket(Pointer ppacket, boolean sync);

    /**
     * INT PacketSendPackets(LPADAPTER AdapterObject, PVOID PacketBuff, ULONG Size, BOOLEAN Sync);
     * @param packetBuff packet buffer.
     * @param size size.
     * @param sync sync.
     * @return returns 1 on success, 0 otherwise.
     */
    int packetSendPackets(Buffer packetBuff, long size, boolean sync);

    /**
     * BOOLEAN PacketReceivePacket(LPADAPTER AdapterObject, LPPACKET lpPacket, BOOLEAN Sync);
     * @param lpPacket pointer to LPPACKET.
     * @param sync sync.
     * @return returns true on success, false otherwise.
     */
    boolean packetReceivePacket(Pointer lpPacket, boolean sync);

    /**
     * BOOLEAN PacketSetHwFilter(LPADAPTER AdapterObject, ULONG Filter);
     * @param filter filter.
     * @return returns true on success, false otherwise.
     */
    boolean packetSetHwFilter(long filter);

    /**
     * BOOLEAN PacketRequest(LPADAPTER AdapterObject,BOOLEAN Set,PPACKET_OID_DATA OidData);
     * @param set set.
     * @param oidData oid data.
     * @return returns true on success, false otherwise.
     */
    boolean packetRequest(boolean set, NativeMappings.Structures.PPACKET_OID_DATA oidData);

    /**
     * HANDLE PacketGetReadEvent(LPADAPTER AdapterObject);
     * @param AdapterObject pointer to LPADAPTER.
     * @return returns handle.
     */
    //HANDLE PacketGetReadEvent(LPADAPTER AdapterObject);

    /**
     * BOOLEAN PacketSetDumpName(LPADAPTER AdapterObject, void* name, int len);
     * @param name name.
     * @param len length.
     * @return returns true on success, false otherwise.
     */
    boolean packetSetDumpName(String name, int len);

    /**
     * BOOLEAN PacketSetDumpLimits(LPADAPTER AdapterObject, UINT maxfilesize, UINT maxnpacks);
     * @param maxfilesize max file size.
     * @param maxnpacks max n packet.
     * @return returns true on success, false otherwise.
     */
    boolean packetSetDumpLimits(long maxfilesize, long maxnpacks);

    /**
     * BOOLEAN PacketIsDumpEnded(LPADAPTER AdapterObject, BOOLEAN sync);
     * @param sync sync.
     * @return returns true on success, false otherwise.
     */
    boolean packetIsDumpEnded(boolean sync);

    /**
     * VOID PacketCloseAdapter(LPADAPTER lpAdapter);
     */
    void packetCloseAdapter();

    /**
     * PAirpcapHandle PacketGetAirPcapHandle(LPADAPTER AdapterObject);
     * @return return pointer to PAirpcapHandle.
     */
    Pointer packetGetAirPcapHandle();

}
