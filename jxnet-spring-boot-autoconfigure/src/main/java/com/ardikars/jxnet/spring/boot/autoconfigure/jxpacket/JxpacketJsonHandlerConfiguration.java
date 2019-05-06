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

package com.ardikars.jxnet.spring.boot.autoconfigure.jxpacket;

import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.JXPACKET_JSON_HANDLER_CONFIGURATION_BEAN_NAME;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.memory.Memory;
import com.ardikars.common.tuple.Tuple;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.RawPcapHandler;
import com.ardikars.jxnet.spring.boot.autoconfigure.HandlerConfigurer;
import com.ardikars.jxnet.spring.boot.autoconfigure.json.JxpacketJacksonSerializer;
import com.ardikars.jxpacket.common.Packet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Jxpacket json handler.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @param <T> returns type.
 */
@ConditionalOnClass({Packet.class, Memory.class, ObjectMapper.class, JsonProcessingException.class})
@Configuration(JXPACKET_JSON_HANDLER_CONFIGURATION_BEAN_NAME)
public class JxpacketJsonHandlerConfiguration<T> extends HandlerConfigurer<T, String> implements RawPcapHandler<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JxpacketJsonHandlerConfiguration.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void nextPacket(T user, int capLen, int len, int tvSec, long tvUsec, long memoryAddress) {
        try {
            String json = objectMapper
                    .writeValueAsString(Tuple
                            .of(PcapPktHdr.newInstance(capLen, len, tvSec, tvUsec),
                                    decodeRawBuffer(memoryAddress, len)));
            getHandler().next(user, json);
        } catch (ExecutionException e) {
            LOGGER.warn(e);
        } catch (InterruptedException e) {
            LOGGER.warn(e);
            Thread.currentThread().interrupt();
        } catch (JsonProcessingException e) {
            LOGGER.warn(e);
        }
    }

    /**
     * Object mapper for jxpacket json serializer.
     * @return returns jxpacket {@link ObjectMapper}.
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new SimpleModule()
                        .addSerializer(new JxpacketJacksonSerializer()));
    }

}
