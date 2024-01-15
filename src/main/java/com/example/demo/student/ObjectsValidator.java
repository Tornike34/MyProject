package com.example.demo.student;

import org.jetbrains.annotations.NotNull;

public class ObjectsValidator {
    public static boolean validateNotNull(@NotNull Student student) {
        if (student.getName() == null) {
            return false;
        }
        if (student.getEmail() == null) {
            return false;
        }
        return student.getDob() != null;
    }
}
