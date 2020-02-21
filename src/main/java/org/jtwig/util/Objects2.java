package org.jtwig.util;

public class Objects2 {
    public static void require(boolean check, String message) {
        if (check) {
            throw new IllegalStateException("Requirement does not meet: " + message);
        }
    }
}
