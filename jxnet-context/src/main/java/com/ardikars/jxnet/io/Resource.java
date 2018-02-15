package com.ardikars.jxnet.io;

import java.io.IOException;

public interface Resource<T> extends InputStreamSource {

	T getData() throws IOException;

	boolean exists();

}
