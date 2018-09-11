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

package com.ardikars.jxnet.packet;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import jnr.ffi.annotations.Encoding;

public interface NativeMappings {

    /**
     * PCHAR PacketGetVersion();
     * @return returns packet version.
     */
    String PacketGetVersion();

    /**
     * PCHAR PacketGetDriverVersion();
     * @return returns packet driver version.
     */
    String PacketGetDriverVersion();

    /**
     * BOOLEAN PacketSetMinToCopy(LPADAPTER AdapterObject,int nbytes);
     * @param AdapterObject pointer to LPADAPTER.
     * @param nbytes n bytes.
     * @return returns true on success, false otherwise.
     */
    boolean PacketSetMinToCopy(Pointer AdapterObject, int nbytes);

    /**
     * BOOLEAN PacketSetNumWrites(LPADAPTER AdapterObject,int nwrites);
     * @param AdapterObject pointer to LPADAPTER.
     * @param nwrites n writes.
     * @return returns true on success, false otherwires.
     */
    boolean PacketSetNumWrites(Pointer AdapterObject, int nwrites);

    /**
     * BOOLEAN PacketSetMode(LPADAPTER AdapterObject,int mode);
     * @param AdapterObject pointer to LPADAPTER.
     * @param mode mode.
     * @return returns true on success, false otherwise.
     */
    boolean PacketSetMode(Pointer AdapterObject, int mode);

    /**
     * BOOLEAN PacketSetReadTimeout(LPADAPTER AdapterObject,int timeout);
     * @param AdapterObject pointer to LPADAPTER.
     * @param timeout timeout.
     * @return returns true on success, false otherwise.
     */
    boolean PacketSetReadTimeout(Pointer AdapterObject, int timeout);

    /**
     * BOOLEAN PacketSetBpf(LPADAPTER AdapterObject, struct bpf_program* fp);
     * @param AdapterObject pointer to LPADAPTER.
     * @param bpf_program bpf program.
     * @return returns true on success, false otherwise.
     */
    //boolean PacketSetBpf(Pointer AdapterObject, Structures.bpf_program fp);

    /**
     * BOOLEAN PacketSetLoopbackBehavior(LPADAPTER  AdapterObject, UINT LoopbackBehavior);
     * @param AdapterObject pointer to LPADAPTER.
     * @param LoopbackBehavior loopback behavior.
     * @return returns true on succes, false otherwise.
     */
    boolean PacketSetLoopbackBehavior(Pointer  AdapterObject, long LoopbackBehavior);

    /**
     * INT PacketSetSnapLen(LPADAPTER AdapterObject, int snaplen);
     * @param AdapterObject pointer to LPADAPTER.
     * @param snaplen snapshot length.
     * @return returns snapshot length.
     */
    int PacketSetSnapLen(Pointer AdapterObject, int snaplen);

    /**
     * BOOLEAN PacketGetStats(LPADAPTER AdapterObject, struct bpf_stat* s);
     * @param AdapterObject pointer to LPADAPTER.
     * @param s bpf status.
     * @return returns true on success, false otherwise.
     */
    boolean PacketGetStats(Pointer AdapterObject, Structures.bpf_stat s);

    /**
     * BOOLEAN PacketGetStatsEx(LPADAPTER AdapterObject, struct bpf_stat* s);
     * @param AdapterObject pointer to LPADAPTER.
     * @param s bpf status.
     * @return returns true on success, false otherwise.
     */
    boolean PacketGetStatsEx(Pointer AdapterObject, Structures.bpf_stat s);

    /**
     * BOOLEAN PacketSetBuff(LPADAPTER AdapterObject, int dim);
     * @param AdapterObject pointer to LPADAPTER.
     * @param dim dim.
     * @return returns true on success, false otherwise.
     */
    boolean PacketSetBuff(Pointer AdapterObject, int dim);

    /**
     * BOOLEAN PacketGetNetType(LPADAPTER AdapterObject, NetType* type);
     * @param AdapterObject pointer to LPADAPTER.
     * @param type type.
     * @return returns true on success, false otherwise.
     */
    boolean PacketGetNetType(Pointer AdapterObject, Structures.NetType type);

    /**
     * BOOLEAN PacketGetNetType2(PCHAR AdapterName, NetType *type);
     * @param AdapterName adapter name.
     * @param type type.
     * @return returns true on success, false otherwise.
     */
    //boolean PacketGetNetType2(String AdapterName, Structures.NetType type);

    /**
     * BOOLEAN PacketIsLoopbackAdapter(LPADAPTER AdapterObject);
     * @param AdapterObject pointer to APADAPTER.
     * @return returns true on success, false otherwise.
     */
    boolean PacketIsLoopbackAdapter(Pointer AdapterObject);

    /**
     * int PacketIsMonitorModeSupported(PCHAR AdapterName);
     * @param AdapterName adapter name.
     * @return returns 1 if adapter is supported for monitor mode, 0 otherwise.
     */
    int PacketIsMonitorModeSupported(String AdapterName);

    /**
     * int PacketSetMonitorMode(PCHAR AdapterName, int mode);
     * @param AdapterName adapter name.
     * @param mode 1 to enable monitor mode; 0 to disable monitor mode.
     * @return returns 0 on success, 1 otherwise.
     */
    int PacketSetMonitorMode(String AdapterName, int mode);

    /**
     * int PacketGetMonitorMode(PCHAR AdapterName);
     * @param AdapterName adapter name.
     * @return returns 0 on success, 1 otherswise.
     */
    int PacketGetMonitorMode(String AdapterName);

    /**
     * LPADAPTER PacketOpenAdapter(PCHAR AdapterName);
     * @param AdapterName adapter name.
     * @return returns pointer to LPADAPTER.
     */
    Pointer PacketOpenAdapter(@Encoding("ASCII") String AdapterName);

    /**
     * BOOLEAN PacketRequest(LPADAPTER AdapterObject,BOOLEAN Set,PPACKET_OID_DATA OidData);
     * @param AdapterObject pointer to LPADAPTER.
     * @param Set set.
     * @param OidData oid data.
     * @return returns true on success, false otherwise.
     */
    boolean PacketRequest(Pointer AdapterObject, boolean Set, Structures.PPACKET_OID_DATA OidData);

    /**
     * VOID PacketCloseAdapter(LPADAPTER lpAdapter);
     * @param lpAdapter pointer to LPADAPTER.
     */
    void PacketCloseAdapter(Pointer lpAdapter);

    final class Structures {

        public static class PPACKET_OID_DATA extends Struct {

            public final UnsignedLong Oid = new UnsignedLong();
            public final UnsignedLong Length = new UnsignedLong();
            public final Unsigned8[] Data;

            public PPACKET_OID_DATA(Runtime runtime, int length) {
                super(runtime);
                this.Length.set(length);
                this.Data = array(new Unsigned8[length]);
            }

        }

        public static class bpf_stat extends Struct {

            public final Unsigned32 bs_recv = new Unsigned32();
            public final Unsigned32 bs_drop = new Unsigned32();
            public final Unsigned32 ps_ifdrop = new Unsigned32();
            public final Unsigned32 bs_capt = new Unsigned32();

            public bpf_stat(Runtime runtime) {
                super(runtime);
            }

        }

        public static class NetType extends Struct {

            public final Unsigned32 LinkType = new Unsigned32();
            public final UnsignedLong LinkSpeed = new UnsignedLong();

            public NetType(Runtime runtime) {
                super(runtime);
            }

        }

    }

}
