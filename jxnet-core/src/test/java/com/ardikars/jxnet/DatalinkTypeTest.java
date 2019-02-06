package com.ardikars.jxnet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatalinkTypeTest {

    @Test
    public void register() {
        DataLinkType.register(DataLinkType.EN10MB);
        DataLinkType.register(DataLinkType.DOCSIS);
        DataLinkType.register(DataLinkType.LINUX_SLL);
        assert DataLinkType.valueOf(DataLinkType.EN10MB.getValue()) == DataLinkType.EN10MB;
        assert DataLinkType.valueOf(DataLinkType.DOCSIS.getValue()) == DataLinkType.DOCSIS;
        assert DataLinkType.fromValue(DataLinkType.LINUX_SLL.getValue()) == DataLinkType.LINUX_SLL;
        assert DataLinkType.fromValue((short) 0xffff).getValue() == -1;
    }

}
