package com.ardikars.jxnet.spring.boot.autoconfigure.memory;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.jxpacket.common.Packet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConditionalOnClass(Packet.class)
@ConfigurationProperties(prefix = "jxnet.memory")
public class MemoryConfigurationProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.ardikars.jxnet.spring.boot.autoconfigure.jxpacket.JxpacketConfigurationProperties.class);

    private Boolean checkBounds;

    /**
     * Initialize properties.
     */
    public MemoryConfigurationProperties() {
        if (checkBounds == null) {
            this.checkBounds = false;
        }
        LOGGER.debug("Memory checkBounds: {}", checkBounds);
    }

    public Boolean getCheckBounds() {
        return checkBounds;
    }

    public void setCheckBounds(Boolean checkBounds) {
        this.checkBounds = checkBounds;
    }

}
