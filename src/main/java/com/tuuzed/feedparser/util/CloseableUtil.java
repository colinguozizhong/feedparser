package com.tuuzed.feedparser.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloseableUtil {
    // 日志
    private static final Logger logger = LoggerFactory.getLogger(CloseableUtil.class);

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
