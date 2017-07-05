package com.tuuzed.feedparser.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloseableUtils {
    // 日志
    private static final Logger logger = LoggerFactory.getLogger(CloseableUtils.class);

    public static void safeClose(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
