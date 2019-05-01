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

package com.ardikars.jxnet.spring.boot.autoconfigure.json;

import com.ardikars.common.annotation.Incubating;
import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.tuple.Pair;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxpacket.common.Packet;
import com.ardikars.jxpacket.common.UnknownPacket;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Incubating
public class JxpacketJacksonSerializer extends StdSerializer<Pair<PcapPktHdr, Packet>> {

    private static final long serialVersionUID = -5359017455643338407L;

    private static final Logger LOGGER = LoggerFactory.getLogger(JxpacketJacksonSerializer.class);

    public JxpacketJacksonSerializer() {
        super(Pair.class, false);
    }

    @Override
    public void serialize(Pair<PcapPktHdr, Packet> pair, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("header", pair.getLeft());
        gen.writeArrayFieldStart("packet");
        for (Packet packet : pair.getRight()) {
            gen.writeStartObject();
            gen.writeStringField("type", packet.getClass().getSimpleName());
            gen.writeObjectFieldStart("data");
            Packet.Header header = packet.getHeader();
            Field[] fields = header.getClass().getDeclaredFields();
            if (packet instanceof UnknownPacket) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    int fieldModifier = field.getModifiers();
                    if (Modifier.isFinal(fieldModifier)
                            && !Modifier.isStatic(fieldModifier)
                            && !field.getName().equals("builder")
                            && field.getName().equals("buffer")) {
                        gen.writeStringField("unkown", "");
                    }
                }
            } else {
                for (Field field : fields) {
                    field.setAccessible(true);
                    int fieldModifier = field.getModifiers();
                    if (Modifier.isFinal(fieldModifier)
                            && !Modifier.isStatic(fieldModifier)
                            && !field.getName().equals("builder")
                            && !field.getName().equals("buffer")) {
                        try {
                            String value;
                            Object object = field.get(header);
                            if (object instanceof Number) {
                                if (object instanceof Byte) {
                                    byte v = (Byte) object;
                                    value = String.valueOf(v & 0xFF);
                                } else if (object instanceof Short) {
                                    short v = (Short) object;
                                    value = String.valueOf(v & 0xFFFF);
                                } else if (object instanceof Integer) {
                                    int v = (Integer) object;
                                    value = String.valueOf(v & 0xFFFFFFFFL);
                                } else {
                                    value = object.toString();
                                }
                            } else {
                                value = object.toString();
                            }
                            gen.writeStringField(field.getName(), value);
                        } catch (IllegalAccessException e) {
                            LOGGER.error(e);
                        }
                    }
                }
            }
            gen.writeEndObject();
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }

}
