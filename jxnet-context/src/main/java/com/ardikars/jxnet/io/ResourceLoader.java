package com.ardikars.jxnet.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class ResourceLoader<T> implements Resource<T> {

	private final String path;
	private Class aClass;
	private ClassLoader classLoader;

	public ResourceLoader(String path) {
		this.path = path;
		this.aClass = null;
		this.classLoader = null;
	}

	public ResourceLoader(String path, Class aClass) {
		this.path = path;
		this.aClass = aClass;
	}

	public ResourceLoader(String path, ClassLoader classLoader) {
		this.path = path;
		this.classLoader = classLoader;
	}

	public ResourceLoader(String path, Class aClass, ClassLoader classLoader) {
		this.path = path;
		this.aClass = aClass;
		this.classLoader = classLoader;
	}

	protected URL resolveURL() {
		if (this.aClass != null) {
			return this.aClass.getResource(this.path);
		} else {
			return this.classLoader != null ? this.classLoader.getResource(this.path) : ClassLoader.getSystemResource(this.path);
		}
	}

	@Override
	public InputStream getInputStream() throws IOException {
		InputStream is;
		if (this.aClass != null) {
			is = this.aClass.getResourceAsStream(this.path);
		} else if (this.classLoader != null) {
			is = this.classLoader.getResourceAsStream(this.path);
		} else {
			is = ClassLoader.getSystemResourceAsStream(this.path);
		}

		if (is == null) {
			throw new FileNotFoundException(path + " file cannot be opened because it does not exist");
		} else {
			return is;
		}
	}

	@Override
	public boolean exists() {
		return resolveURL() != null;
	}

}
