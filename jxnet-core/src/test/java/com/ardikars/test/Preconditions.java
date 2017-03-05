package com.ardikars.test;

import org.junit.*;
import static com.ardikars.jxnet.util.Preconditions.*;
public class Preconditions {

    @org.junit.Test
    public void run() {
        CheckArgument(true, "Check Argument True");
        CheckArgument(true);
        CheckState(true, "Check State True");
        CheckState(true);
        CheckNotNull("", "Check Not Null Yes");
        CheckNotNull("");
    }

}
