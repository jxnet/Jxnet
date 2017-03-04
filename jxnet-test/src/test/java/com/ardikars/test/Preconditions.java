package com.ardikars.test;

import org.junit.*;
import static com.ardikars.jxnet.util.Preconditions.*;
public class Preconditions {

    @org.junit.Test
    public void run() {
        CheckArgument(true, "Check Argument True");
        //CheckArgument(false, "Check Argument False");
        CheckState(true, "Check State True");
        //CheckState(false, "Check State False");
        CheckNotNull("", "Check Not Null Yes");
        //CheckNotNull(null, "Check Not Null No");
    }

}
