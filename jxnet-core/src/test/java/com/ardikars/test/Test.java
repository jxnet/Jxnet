package com.ardikars.test;

import com.ardikars.jxnet.util.Preconditions;

import java.lang.reflect.Field;


class MYOBJ {
    public String val;
}

public class    Test {

    public static void main(String[] args) {
        MYOBJ obj = new MYOBJ();
        obj.val = "HAHAHA";
        xxx(obj);
        System.out.println(obj.val);
    }


    private static void xxx(MYOBJ obj) {
        obj.val = "HIHI";
    }



}

