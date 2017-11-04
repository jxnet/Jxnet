package com.ardikars.test;

import com.ardikars.jxnet.util.Loaders;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by root on 6/8/17.
 */
public class AddJavaLibraryPathTest {

    @Test
    public void run() {
        String paths = System.getProperty("java.library.path");
        Loaders.addJavaLibarayPath("C:\\Windows\\System32\\Npcap");
        String newPaths = System.getProperty("java.library.path");
        System.out.println(paths);
        System.out.println(newPaths);
        Assert.assertNotEquals(paths, newPaths);
    }

}
