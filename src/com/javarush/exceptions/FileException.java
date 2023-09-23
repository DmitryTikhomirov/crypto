package com.javarush.exceptions;

import java.io.IOException;

public class FileException extends IOException {
    public FileException(String message, String filename) {
        super(message);
        System.out.printf(message + "%s", filename);
    }
}
