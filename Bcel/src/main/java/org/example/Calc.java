package org.example;

import java.io.IOException;

public class Calc {
    static {
        try {
            Runtime.getRuntime().exec("calc.exe");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
