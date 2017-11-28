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
    public void addLibrary(Library.Loader libraryLoader) {
        Application.getInstance().addLibrary(libraryLoader);
    }

}
