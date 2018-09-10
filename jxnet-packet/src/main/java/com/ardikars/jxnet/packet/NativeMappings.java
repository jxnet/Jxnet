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

            public PPACKET_OID_DATA(Runtime runtime, int bufferSize) {
                super(runtime);
                this.Data = array(new Unsigned8[bufferSize]);
            }

        }

    }

}
