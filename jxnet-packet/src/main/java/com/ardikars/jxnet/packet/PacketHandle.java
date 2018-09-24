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
import java.util.concurrent.locks.ReentrantReadWriteLock;
import jnr.ffi.Pointer;

public class PacketHandle implements Packet {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    private final NativeMappings mappings = NativeMappings.INSTANCE;

    private final Pointer handle;

    public PacketHandle(String adapterName) {
        this.handle = mappings.PacketOpenAdapter(adapterName);
    }

    @Override
    public boolean packetSetMinToCopy(int nbytes) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketSetMinToCopy(handle, nbytes);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public boolean packetSetNumWrites(int nwrites) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketSetNumWrites(handle, nwrites);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public boolean packetSetMode(int mode) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketSetMode(handle, mode);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public boolean packetSetReadTimeout(int timeout) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketSetReadTimeout(handle, timeout);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public boolean packetSetLoopbackBehavior(long loopbackBehavior) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketSetLoopbackBehavior(handle, loopbackBehavior);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public int packetSetSnapLen(int snaplen) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketSetSnapLen(handle, snaplen);
            } finally {
                lock.readLock().unlock();
            }
        }
        return -1;
    }

    @Override
    public boolean packetGetStats(NativeMappings.Structures.bpf_stat s) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketGetStats(handle, s);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public boolean packetGetStatsEx(NativeMappings.Structures.bpf_stat s) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketGetStatsEx(handle, s);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public boolean packetSetBuff(int dim) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketSetBuff(handle, dim);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public boolean packetGetNetType(NativeMappings.Structures.NetType type) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketGetNetType(handle, type);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public boolean packetIsLoopbackAdapter() {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketIsLoopbackAdapter(handle);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public boolean packetSendPacket(Pointer ppacket, boolean sync) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketSendPacket(handle, ppacket, sync);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public int packetSendPackets(Buffer packetBuff, long size, boolean sync) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketSendPackets(handle, packetBuff, size, sync);
            } finally {
                lock.readLock().unlock();
            }
        }
        return -1;
    }

    @Override
    public boolean packetReceivePacket(Pointer lpPacket, boolean sync) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketReceivePacket(handle, lpPacket, sync);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public boolean packetSetHwFilter(long filter) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketSetHwFilter(handle, filter);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public boolean packetRequest(boolean set, NativeMappings.Structures.PPACKET_OID_DATA oidData) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketRequest(handle, set, oidData);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public boolean packetSetDumpName(String name, int len) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketSetDumpName(handle, name, len);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public boolean packetSetDumpLimits(long maxfilesize, long maxnpacks) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketSetDumpLimits(handle, maxfilesize, maxnpacks);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public boolean packetIsDumpEnded(boolean sync) {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketIsDumpEnded(handle, sync);
            } finally {
                lock.readLock().unlock();
            }
        }
        return false;
    }

    @Override
    public void packetCloseAdapter() {
        if (lock.writeLock().tryLock()) {
            try {
                mappings.PacketCloseAdapter(handle);
            } finally {
                lock.writeLock().unlock();
            }
        }
    }

    @Override
    public Pointer packetGetAirPcapHandle() {
        if (lock.readLock().tryLock()) {
            try {
                return mappings.PacketGetAirPcapHandle(handle);
            } finally {
                lock.readLock().unlock();
            }
        }
        return null;
    }

}
