package com.ardikars.jxnet;

public interface ArpHandler<T> {

    void nextARPEntry(ArpEntry arpEntry, T arg);

}
