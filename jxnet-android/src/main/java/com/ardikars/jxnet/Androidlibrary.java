package com.ardikars.jxnet;

public class Androidlibrary implements Library.Loader {

    @Override
    public void load() throws UnsatisfiedLinkError {
        System.loadLibrary("jxnet");
    }

}
