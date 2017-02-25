package com.ardikars.test;

import com.ardikars.jxnet.util.Preconditions;

import java.lang.reflect.Field;

public class Test {

    public static void main(String[] args) {
        String test = "BOOM";
        System.out.println(Preconditions.CheckNotNull(test, "MY NM"));

    }



}

