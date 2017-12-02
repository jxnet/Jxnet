package com.ardikars.jxnet;

import com.ardikars.jxnet.util.Validate;

import java.io.*;
import java.util.regex.Pattern;

public final class Library {

    public interface Loader {
        void load() throws UnsatisfiedLinkError;
    }

    static void loadLibrary(String path) throws UnsatisfiedLinkError {
        Validate.nullPointer(path);
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("The path has to be absolute (start with '/').");
        }
        String[] parts = Pattern.compile("/").split(path);
        if (parts != null && parts.length > 1) {
            parts = Pattern.compile("\\.").split(parts[parts.length - 1]);
        }
        File temp = null;
        try {
            temp = File.createTempFile(parts[0], "." + parts[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        temp.deleteOnExit();
        byte[] buffer = new byte[1024];
        int readBytes;
        InputStream is = Library.class.getResourceAsStream(path);
        if (is == null) {
            throw  new UnsatisfiedLinkError("Error: " + path + " not found.\n");
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(temp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while ((readBytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, readBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.load(temp.getAbsolutePath());
    };

}
