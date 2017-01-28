package com.ardikars.jxnet;

public interface ArpHandler<T> {

    int nextArpEntry(ArpEntry arpEntry, T arg);

}
