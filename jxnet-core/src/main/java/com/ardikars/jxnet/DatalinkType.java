/**
 * Copyright (C) 2017  Ardika Rommy Sanjaya
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

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public enum DatalinkType {
    /**
     * Ethernet (10Mb, 100Mb, 1000Mb, and up): 1
     */
    EN10MB((short) 1, "Ethernet")
    ;

    private short value;
    private String name;

    private DatalinkType(short value, String name) {
        this.value = value;
        this.name = name;
    }

    public short getValue() {
        return value;
    }

    public String getName() {
        return name;
    }


    public static DatalinkType valueOf(short DatalinkType) {
        for(DatalinkType f : values()) {
            if(f.getValue() == DatalinkType) {
                return f;
            }
        }
        return null;
    }

}
