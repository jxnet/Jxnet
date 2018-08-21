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

import com.ardikars.common.util.NamedNumber;

import java.util.HashMap;
import java.util.Map;

/**
 * List of linktype available at @link https://www.tcpdump.org/linktypes.html.
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class DataLinkType extends NamedNumber<Short, DataLinkType> {

    /**
     * Ethernet (10Mb, 100Mb, 1000Mb, and up): 1
     */
    public static final DataLinkType EN10MB = new DataLinkType((short) 1, "Ethernet");

    /**
     * DOCSIS MAC frames: 143
     */
    public static final DataLinkType DOCSIS = new DataLinkType((short) 143, "DOCSIS");

    /**
     * Linux cooked-mode capture (SLL): 113
     */
    public static final DataLinkType LINUX_SLL = new DataLinkType((short) 113, "Linux SLL");

    private static final Map<DataLinkType, Short> registry =
            new HashMap<>();

    public DataLinkType(Short value, String name) {
        super(value, name);
    }

    /**
     * Get datalink type from value.
     * @param value value.
     * @return returns datalink type.
     */
    public static DataLinkType valueOf(short value) {
        for (Map.Entry<DataLinkType, Short> entry : registry.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }
        return new DataLinkType((short) -1, "Unknown");
    }

    public static DataLinkType register(DataLinkType dataLinkType) {
        registry.put(dataLinkType, dataLinkType.getValue());
        return dataLinkType;
    }

    static {
        registry.put(EN10MB, EN10MB.getValue());
        registry.put(DOCSIS, DOCSIS.getValue());
    }

}
