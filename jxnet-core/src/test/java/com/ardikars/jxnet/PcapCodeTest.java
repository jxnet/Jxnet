package com.ardikars.jxnet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PcapCodeTest {

    @Test
    public void pcapCodeTest() {
        assert PcapCode.PCAP_ERROR == PcapCode.fromStatusCode(-1);
        assert PcapCode.PCAP_ERROR_BREAK == PcapCode.fromStatusCode(-2);
        assert PcapCode.PCAP_ERROR_NOT_ACTIVATED == PcapCode.fromStatusCode(-3);
        assert PcapCode.PCAP_ERROR_ACTIVATED == PcapCode.fromStatusCode(-4);
        assert PcapCode.PCAP_ERROR_NO_SUCH_DEVICE == PcapCode.fromStatusCode(-5);
        assert PcapCode.PCAP_ERROR_RFMON_NOTSUP == PcapCode.fromStatusCode(-6);
        assert PcapCode.PCAP_ERROR_NOT_RFMON == PcapCode.fromStatusCode(-7);
        assert PcapCode.PCAP_ERROR_PERM_DENIED == PcapCode.fromStatusCode(-8);
        assert PcapCode.PCAP_ERROR_IFACE_NOT_UP == PcapCode.fromStatusCode(-9);
        assert PcapCode.PCAP_ERROR_CANSET_TSTAMP_TYPE == PcapCode.fromStatusCode(-10);
        assert PcapCode.PCAP_ERROR_PROMISC_PERM_DENIED == PcapCode.fromStatusCode(-11);
        assert PcapCode.PCAP_ERROR_TSTAMP_PRECISION_NOTSUP == PcapCode.fromStatusCode(-12);
        assert PcapCode.PCAP_OK == PcapCode.fromStatusCode(0);
        assert PcapCode.PCAP_WARNING == PcapCode.fromStatusCode(1);
        assert PcapCode.PCAP_WARNING_PROMISC_NOTSUP == PcapCode.fromStatusCode(2);
        assert PcapCode.PCAP_WARNING_TSTAMP_TYPE_NOTSUP == PcapCode.fromStatusCode(3);
        assert PcapCode.PCAP_FALSE == PcapCode.fromStatusCode(-100);
        assert PcapCode.PCAP_TRUE == PcapCode.fromStatusCode(-101);
        assert null == PcapCode.fromStatusCode(-9999);
    }

    @Test
    public void getterTest() {
        assert PcapCode.PCAP_OK.getValue() == 0;
    }

}
