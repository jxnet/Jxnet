package com.ardikars.jxnet;

public class App implements ApplicationInitializer {

    @Override
    public void initialize(Application.Context context) {
        context.addLibrary(new Library() {
            @Override
            public void load() {
                System.out.println("SHIT");
                throw new UnsatisfiedLinkError();
            }
        });
        context.addLibrary(new Library() {
            @Override
            public void load() {
                System.out.println("DAMN");
            }
        });
        Application.bootstrap("Shark", "0.0.1", context);
    }

}
