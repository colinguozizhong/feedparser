package com.tuuzed.feedparser.util

import org.junit.Test

class LoggerTest {
    @Test
    fun info() {
        val logger = LoggerFactory.getLogger(javaClass)
        logger.info("info")

    }

    @Test
    fun error() {
        val logger = LoggerFactory.getLogger(javaClass)
        logger.error("error")
    }

}