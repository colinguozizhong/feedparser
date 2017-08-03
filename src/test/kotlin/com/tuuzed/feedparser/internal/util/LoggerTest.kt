package com.tuuzed.feedparser.internal.util

import com.tuuzed.feedparser.Logger
import org.junit.Test

class LoggerTest {
    @Test
    fun info() {
        val logger = Logger.getLogger(javaClass)
        logger.info("info")

    }

    @Test
    fun error() {
        val logger = Logger.getLogger(javaClass)
        logger.error("error")
    }

}