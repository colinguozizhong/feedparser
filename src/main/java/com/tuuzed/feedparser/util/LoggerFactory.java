package com.tuuzed.feedparser.util;


public class LoggerFactory {
    public static Logger getLogger(Class<?> clazz) {
        return new Logger(clazz);
    }
}
