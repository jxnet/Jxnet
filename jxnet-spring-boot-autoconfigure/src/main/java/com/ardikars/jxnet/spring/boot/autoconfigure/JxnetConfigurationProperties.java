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

package com.ardikars.jxnet.spring.boot.autoconfigure;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.util.management.Jvm;
import com.ardikars.common.util.management.Jvms;
import com.ardikars.jxnet.BpfProgram;
import com.ardikars.jxnet.Context;
import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.ImmediateMode;
import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapDirection;
import com.ardikars.jxnet.PcapTimestampPrecision;
import com.ardikars.jxnet.PcapTimestampType;
import com.ardikars.jxnet.PromiscuousMode;
import com.ardikars.jxnet.RadioFrequencyMonitorMode;
import javax.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Spring autoconfiguration properties.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.4.0
 */
@ConditionalOnClass({Context.class, Jxnet.class})
@ConfigurationProperties(prefix = "jxnet")
public class JxnetConfigurationProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(JxnetConfigurationProperties.class);

    private String source;

    private Integer snapshot;

    private PromiscuousMode promiscuous;

    private Integer timeout;

    private ImmediateMode immediate;

    private PcapTimestampType timestampType;

    private PcapTimestampPrecision timestampPrecision;

    private RadioFrequencyMonitorMode rfmon;

    private Boolean blocking;

    private PcapDirection direction;

    private Integer datalink;

    private String file;

    private BpfProgram.BpfCompileMode bpfCompileMode;

    private String filter;

    private Pcap.PcapType pcapType;

    private Integer numberOfThread;

    private Jvm jvm;

    /**
     * Initialize field.
     */
    @PostConstruct
    public void initialize() {
        if (pcapType == null) {
            pcapType = Pcap.PcapType.LIVE;
        }
        if (snapshot == null || snapshot <= 0) {
            snapshot = 65535;
        }
        if (timestampType == null) {
            timestampType = PcapTimestampType.HOST;
        }
        if (timestampPrecision == null) {
            timestampPrecision = PcapTimestampPrecision.MICRO;
        }
        if (datalink == null) {
            datalink = (int) DataLinkType.EN10MB.getValue();
        }
        if (promiscuous == null) {
            promiscuous = PromiscuousMode.PROMISCUOUS;
        }
        if (timeout == null || timeout <= 0) {
            timeout = 2000;
        }
        if (immediate == null) {
            immediate = ImmediateMode.IMMEDIATE;
        }
        if (direction == null) {
            direction = PcapDirection.PCAP_D_INOUT;
        }
        if (rfmon == null) {
            rfmon = RadioFrequencyMonitorMode.NON_RFMON;
        }
        if (blocking == null) {
            blocking = false;
        }
        if (file == null) {
            file = null;
        }
        if (bpfCompileMode == null) {
            bpfCompileMode = BpfProgram.BpfCompileMode.OPTIMIZE;
        }
        if (filter == null || filter.isEmpty()) {
            filter = null;
        }

        try {
            jvm = Jvms.getJvm();
            if (numberOfThread == null) {
                if (jvm.hasJvm()) {
                    numberOfThread = jvm.getAvailableProcessors();
                } else {
                    numberOfThread = 0;
                }
            }
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
        LOGGER.debug("Source                       : {}", source);
        LOGGER.debug("Snapshot length              : {}", snapshot);
        LOGGER.debug("Promiscuous                  : {}", promiscuous);
        LOGGER.debug("Timeout                      : {}", timeout);
        LOGGER.debug("Immediate mode               : {}", immediate);
        LOGGER.debug("Timestamp type               : {}", timestampType);
        LOGGER.debug("Timestamp precision          : {}", timestampPrecision);
        LOGGER.debug("Redio frequency monitor mode : {}", rfmon);
        LOGGER.debug("Blocking                     : {}", blocking);
        LOGGER.debug("Direction                    : {}", direction);
        LOGGER.debug("Datalink                     : {}", datalink);
        LOGGER.debug("Pcap file                    : {}", file);
        LOGGER.debug("Bpf compile mode             : {}", bpfCompileMode);
        LOGGER.debug("Filter                       : {}", filter);
        LOGGER.debug("Pcap type                    : {}", pcapType);
        LOGGER.debug("Number of thread             : {}", numberOfThread);
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Integer snapshot) {
        this.snapshot = snapshot;
    }

    public PromiscuousMode getPromiscuous() {
        return promiscuous;
    }

    public void setPromiscuous(PromiscuousMode promiscuous) {
        this.promiscuous = promiscuous;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public ImmediateMode getImmediate() {
        return immediate;
    }

    public void setImmediate(ImmediateMode immediate) {
        this.immediate = immediate;
    }

    public PcapTimestampType getTimestampType() {
        return timestampType;
    }

    public void setTimestampType(PcapTimestampType timestampType) {
        this.timestampType = timestampType;
    }

    public PcapTimestampPrecision getTimestampPrecision() {
        return timestampPrecision;
    }

    public void setTimestampPrecision(PcapTimestampPrecision timestampPrecision) {
        this.timestampPrecision = timestampPrecision;
    }

    public RadioFrequencyMonitorMode getRfmon() {
        return rfmon;
    }

    public void setRfmon(RadioFrequencyMonitorMode rfmon) {
        this.rfmon = rfmon;
    }

    public Boolean getBlocking() {
        return blocking;
    }

    public void setBlocking(Boolean blocking) {
        this.blocking = blocking;
    }

    public PcapDirection getDirection() {
        return direction;
    }

    public void setDirection(PcapDirection direction) {
        this.direction = direction;
    }

    public DataLinkType getDatalink() {
        return DataLinkType.valueOf(datalink.shortValue());
    }

    public void setDatalink(Integer datalink) {
        this.datalink = datalink;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public BpfProgram.BpfCompileMode getBpfCompileMode() {
        return bpfCompileMode;
    }

    public void setBpfCompileMode(BpfProgram.BpfCompileMode bpfCompileMode) {
        this.bpfCompileMode = bpfCompileMode;
    }

    public Pcap.PcapType getPcapType() {
        return pcapType;
    }

    public void setPcapType(Pcap.PcapType pcapType) {
        this.pcapType = pcapType;
    }

    public Integer getNumberOfThread() {
        return numberOfThread;
    }

    public void setNumberOfThread(Integer numberOfThread) {
        this.numberOfThread = numberOfThread;
    }

}
