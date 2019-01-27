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

import com.ardikars.common.annotation.Incubating;
import com.ardikars.common.util.Unsafes;

/**
 * Native mamory wrapper.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.5.4
 */
@Incubating
public final class Memory implements PointerHandler {

    private final long address;

    private final long size;

    private boolean closed;

    private Memory(long address, long size) {
        this.address = address;
        this.size = size;
    }

    /**
     * Allocate uninitialized native memory.
     * @param size size in bytes.
     * @return returns {@link Memory}.
     */
    public static Memory allocate(long size) {
        long address = Unsafes.getUnsafe().allocateMemory(size);
        return new Memory(address, size);
    }

    /**
     * Allocate native memory.
     * @param size size in bytes.
     * @param zeroing if {@code true} memory will initialize with zero value, otherwise unintialize.
     * @return returns {@link Memory}.
     */
    public static Memory allocate(long size, boolean zeroing) {
        Memory memory = allocate(size);
        if (zeroing) {
            memory.zeroing();
        }
        return memory;
    }

    @Override
    public long getAddress() {
        return address;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    /**
     * Get memory size in bytes.
     * @return returns memory size in bytes.
     */
    public long getSize() {
        return size;
    }

    /**
     * Zeroing full memory bufffer.
     */
    public void zeroing() {
        Unsafes.getUnsafe().setMemory(address, size, (byte) 0);
    }

    /**
     * Reallocate {@link Memory}.
     * @param newSize new memory size.
     * @return returns new {@link Memory} with the same address.
     */
    public Memory reallocate(long newSize) {
        Unsafes.getUnsafe().reallocateMemory(address, newSize);
        return new Memory(address, newSize);
    }

    /**
     * Copy memory to another address.
     * @return returns new {@link Memory}.
     */
    public Memory copy() {
        long newMemoryAddress = Unsafes.getUnsafe().allocateMemory(size);
        Unsafes.getUnsafe().copyMemory(address, newMemoryAddress, size);
        return new Memory(newMemoryAddress, size);
    }

    /**
     * Free memory.
     */
    public void free() {
        Unsafes.getUnsafe().freeMemory(address);
        closed = true;
    }

    @Override
    public String toString() {
        return new StringBuilder("Memory{")
            .append("address=").append(address)
            .append(", size=").append(size)
            .append(", closed=").append(closed)
            .append('}').toString();
    }

}
