package com.ardikars.jxnet;

public class ApplicationContext implements Application.Context {

    @Override
    public String getApplicationName() {
        return Application.getInstance().getApplicationName();
    }

    @Override
    public String getApplicationVersion() {
        return Application.getInstance().getApplicationVersion();
    }

    @Override
    public void addProperty(String key, Object value) {
        Application.getInstance().addProperty(key, value);
    }

    @Override
    public Object getProperty(String key) {
        return Application.getInstance().getProperty(key);
    }

    @Override
    public void addLibrary(Library.Loader libraryLoader) {
        Application.getInstance().addLibrary(libraryLoader);
    }

}
