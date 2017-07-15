package com.tuuzed.feedparser.util;


public class Logger {
    private String name;

    public Logger(Class<?> clazz) {
        name = clazz.getName();
    }

    public void info(String message) {
        info(message, null);
    }

    public void info(String message, Throwable throwable) {
        System.out.printf("%s INFO: %s%n", name, message);
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }

    public void error(String message) {
        error(message, null);
    }

    public void error(String message, Throwable throwable) {
        System.out.printf("%s ERROR: %s%n", name, message);
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }
}
