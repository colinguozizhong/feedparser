package com.tuuzed.feedparser.util

import com.tuuzed.feedparser.ext.safeClose


object CloseableUtils {
    fun safeClose(closeable: AutoCloseable?) {
        closeable?.safeClose()
    }
}

