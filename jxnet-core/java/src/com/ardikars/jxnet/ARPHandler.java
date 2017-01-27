package com.ardikars.jxnet;

public interface ARPHandler<T> {

    void nextARPEntry(ARPEntry arpEntry, T arg);

}
