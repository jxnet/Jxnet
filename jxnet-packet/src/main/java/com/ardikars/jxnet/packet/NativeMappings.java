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
    boolean PacketSetBpf(Pointer AdapterObject, Structures.bpf_program bpf_program);

    /**
     *
     */

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

        public static class bpf_insn extends Struct {

            public final Unsigned16 code = new Unsigned16();
            public final Unsigned8 jt = new Unsigned8();
            public final Unsigned8 jf = new Unsigned8();
            public final Signed32 k = new Signed32();

            public bpf_insn(Runtime runtime) {
                super(runtime);
            }

        }

        public static class bpf_program extends Struct {

            public final Unsigned32 bf_len = new Unsigned32();
            public final bpf_insn bpf_insn;

            public bpf_program(Runtime runtime) {
                super(runtime);
                this.bpf_insn = new bpf_insn(runtime);
            }

        }

    }

}
