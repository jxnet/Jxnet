package com.ardikars.jxnet.exception;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExceptionTest {

    @Test
    public void message() {
        BpfProgramCloseException bpfProgramCloseException = new BpfProgramCloseException();
        BpfProgramFreeCodeException bpfProgramFreeCodeException = new BpfProgramFreeCodeException();
        DeviceNotFoundException deviceNotFoundException = new DeviceNotFoundException();
        FileCloseException fileCloseException = new FileCloseException();
        NativeException nativeException = new NativeException();
        OperationNotSupportedException operationNotSupportedException = new OperationNotSupportedException();
        PcapCloseException pcapCloseException = new PcapCloseException();
        PcapDumperCloseException pcapDumperCloseException = new PcapDumperCloseException();
        PlatformNotSupportedException platformNotSupportedException = new PlatformNotSupportedException();
        UnknownNetmaskException unknownNetmaskException = new UnknownNetmaskException();
        log(Arrays.asList(
                bpfProgramCloseException,
                bpfProgramFreeCodeException,
                deviceNotFoundException,
                fileCloseException,
                nativeException,
                operationNotSupportedException,
                pcapCloseException,
                pcapDumperCloseException,
                platformNotSupportedException,
                unknownNetmaskException
        ));
    }

    @Test
    public void cause() {
        RuntimeException exception = new RuntimeException();
        BpfProgramCloseException bpfProgramCloseException = new BpfProgramCloseException(exception);
        BpfProgramFreeCodeException bpfProgramFreeCodeException = new BpfProgramFreeCodeException(exception);
        DeviceNotFoundException deviceNotFoundException = new DeviceNotFoundException(exception);
        FileCloseException fileCloseException = new FileCloseException(exception);
        NativeException nativeException = new NativeException(exception);
        OperationNotSupportedException operationNotSupportedException = new OperationNotSupportedException(exception);
        PcapCloseException pcapCloseException = new PcapCloseException(exception);
        PcapDumperCloseException pcapDumperCloseException = new PcapDumperCloseException(exception);
        PlatformNotSupportedException platformNotSupportedException = new PlatformNotSupportedException(exception);
        UnknownNetmaskException unknownNetmaskException = new UnknownNetmaskException(exception);
        log(Arrays.asList(
                bpfProgramCloseException,
                bpfProgramFreeCodeException,
                deviceNotFoundException,
                fileCloseException,
                nativeException,
                operationNotSupportedException,
                pcapCloseException,
                pcapDumperCloseException,
                platformNotSupportedException,
                unknownNetmaskException
        ));
    }

    private void log(List<RuntimeException> throwables) {
        for (Throwable throwable : throwables) {
            assert throwable != null;
        }
    }

}
